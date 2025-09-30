package com.elim.server.gas_monitoring.exception;

import com.elim.server.gas_monitoring.common.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 규모 있는 기업들은 운영환경에서 이런식으로 message만 사용자에게 보내는 것이 아니라 전체 스택트레이스를 로그로 반드시 남기거나
 * Slack, Sentry로 전송한다고 함. 그리고 여기서 조금 더 보완한다면 code 필드를 따로 만들어서 E001 이런식으로 같이 보내주는 방식도 같이
 * 사용한다고 함. 나중에 이런 부분 추가적으로 보완해도 좋을 듯
 * */

@Slf4j
@RestControllerAdvice // 이 어노테이션을 통해 에러를 JSON 형태로 리턴해줌, 스프링의 모든 예외를 한곳에서 처리
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;
    

    /**
     * 전역 예외 처리 핸들러, 개발자가 직접 터트린 예외들을 여기서 처리함
     * GlobalBaseException을 상속한 모든 예외를 잡아서 일관된 JSON 형식으로 반환
     * */
    @ExceptionHandler(GlobalBaseException.class)
    public ResponseEntity<ErrorResponse> handleGlobalBaseException(GlobalBaseException e) {

        String messageKey = (e.getMessageKey() != null)
                ? e.getMessageKey()
                : e.getErrorCode().getMessageKey();

        String message = messageUtil.getMessage(messageKey, e.getArgs());

        return ResponseEntity.status(e.getErrorCode().getStatus()) // Http 상태코드로 적용됨
                .body(ErrorResponse.of(e.getErrorCode().getStatus().value(), messageKey, message));
                // 상태코드를 Integer로 반환하기 위해 errorCode.getStatus().value() 사용. 이거 안쓰면 아마 enum으로 갈듯?
    }


    /**
     * @Valid, @Validated 어노테이션에서 한 검증에서 발생한 예외도 내 방식대로 돌려주고 싶어서 이 핸들러 추가함
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {

        // BindingResult에 담긴 FieldError를 가져와서 ErrorResponse.FieldError 객체로 변환해서 넣었음
        List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    String defaultMessage = error.getDefaultMessage();

                    // 메시지 코드인지 아닌지 확인
                    String resolvedMessage = isResolvableMessage(defaultMessage)
                            ? messageUtil.getMessage(defaultMessage)
                            : defaultMessage;

                    return new ErrorResponse.FieldError(error.getField(), resolvedMessage);
                })
                .collect(Collectors.toList());

        String message = messageUtil.getMessage("common.invalid.input");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "common.invalid.input", message, fieldErrors));
    }

    private boolean isResolvableMessage(String messageCode) {
        try {
            String resolved = messageUtil.getMessage(messageCode);
            // message와 resolved가 같으면 messageSource에 정의 안 된 것일 수 있음
            return !resolved.equals(messageCode);
        } catch (NoSuchMessageException ex) {
            return false;
        }
    }


    /**
     * [JSON 파싱 실패 처리]
     *
     * <p>바인딩에서 오류가 나면 컨트롤러에 진입하기도 전에 예외가 발생하기 때문에 JSON 파싱 단계에서 발생하는 오류를 여기서 일괄적으로 처리해줌.</p>
     * <p>여기는 JSON 바인딩이 실패한 경우이기 때문에 특정 필드에 대한 예외 메세지가 아니라 그냥 JSON 바인딩이 실패했다고 알려주면 됨.</p>
     * <p>근데 SSR 방식(@ModelAttribute)에서는 BindingResult를 같이 써서 어떤 필드에서 어떻게 바인딩이 실패했는지까지 구체적으로 알려주기도 함</p>
     * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException e) {
        Throwable root = ExceptionUtils.getRootCause(e); // 예외의 실제 원인을 추출

        // 필드 값의 형식이 올바르지 않은 경우 (예: 날짜에 "2024-13-01" 입력)
        if (root instanceof InvalidFormatException formatEx) {
            String fieldName = extractFieldName(formatEx.getPath());
            String messageKey = "common.invalid.format";
            String message = messageUtil.getMessage(messageKey, new Object[]{fieldName});
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.of(400, messageKey, message));
        }

        // 그 외 JSON 파싱 실패 (ex. JSON 구조가 아예 깨졌을 때)
        String message = messageUtil.getMessage("common.invalid.json");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "common.invalid.json", message));
    }

    private String extractFieldName(List<JsonMappingException.Reference> path) {
        return path.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("."));
    }


    /**
     * @RequestParam 값이 누락된 경우
     * */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {

        String parameterName = e.getParameterName();
        String message = messageUtil.getMessage("request.param.missing", new Object[]{parameterName});

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "request.param.missing", message));
    }


    /**
     * 잘못된 쿼리 파라미터 타입 (예: Long id에 "aa" 들어온 경우 등). 여기서 경로 변수 타입 오류도 처리함
     * */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {

        String parameterName = e.getName();
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "적절한 타입";

        String message = messageUtil.getMessage("request.param.type.invalid", new Object[]{parameterName, requiredType});

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "request.param.type.invalid", message));
    }


    /**
     * <p>yml 파일에서 스프링 MultipartFile 업로드 최대 크기, 개별 최대 크기를 설정할 수 있는데, 여기서 막혀버리면 사용자한테 그냥 400번대
     * 상태코드만 보내주고 끝임. 그래서 커스텀 예외 처리를 위해 이렇게 핸들러 따로 만들어줬음.</p>
     * <p>이거 구현할 때 SizeLimitExceededException 등의 클래스 제대로 import해야됨. 같은 이름인데 다른 패키지에 들어가 있으면 if문에
     * 못들어가서 예외처리 제대로 안됨</p>
     * */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        String message = null;
        if (rootCause instanceof FileSizeLimitExceededException) {
            message = messageUtil.getMessage("file.upload.size.exceeded");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.of(400, "file.upload.size.exceeded", message));
        } else if (rootCause instanceof SizeLimitExceededException) {
            message = messageUtil.getMessage("file.upload.total.size.exceeded");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.of(400, "file.upload.total.size.exceeded", message));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "", message));
    }


    /**
     * Multipart 형식으로 데이터가 와야 하는데 해당 포맷이 아닌 경우
     * */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException e) {
        String message = messageUtil.getMessage("file.multipart.required");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "file.multipart.required", message));
    }


    /**
     * Multipart 형식으로 요청이 들어왔는데 파싱에 실패함. 즉, Multipart 요청 자체는 왔는데 특정 필드가 빠진 경우 이 핸들러에 걸림
     * */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        String message = messageUtil.getMessage("request.part.missing", new Object[] {e.getRequestPartName()});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400, "request.part.missing", message));
    }


    /**
     * 클라이언트가 잘못된 HTTP 메서드(Get, Post 등)을 사용해서 요청했을 때 발생하는 예외 처리
     * */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String message = messageUtil.getMessage("common.method.not.allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.of(405, "common.method.not.allowed", message));
    }
    
    
    /**
     * 클라이언트가 존재하지 않는 API 주소로 요청을 보낸 경우
     * */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException e) {
        String message = messageUtil.getMessage("common.not.found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(404, "common.not.found", message));
    }


    /**
     * 스프링 시큐리티에서 처리하는 예외(접근 권한 없음)
     * */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        String message = messageUtil.getMessage("common.forbidden");
        return ResponseEntity.status(HttpStatus.FORBIDDEN) // 인증은 되었지만 권한이 없거나 부족한 경우에 Forbidden 사용
                .body(ErrorResponse.of(403, "common.forbidden", message));
    }
    


    /**
     * 나머지 예외 처리
     * */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception e) {

        // 스프링 예외 메시지를 사용자에게 그대로 전달
        String message = e.getMessage() != null ? e.getMessage() : messageUtil.getMessage("common.unexpected.error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(500, "common.unexpected.error", message));
    }
}
