package com.elim.server.gas_monitoring.exception;

import com.elim.server.gas_monitoring.common.util.MessageUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of") // 생성자는 자동으로 private 됨
// access를 package-private로 해서 같은 패키지에서만 호출 가능하도록 했음. 상속관계도 호출 불가 (protected랑 거의 비슷)
public class ErrorResponse<T> {

    @Schema(description = "에러코드", example = "400")
    private final Integer code;

    @Schema(description = "메세지 키", example = "member.not.found")
    private final String messageKey; // messageKey도 클라이언트에게 같이 보내줌

    @Schema(description = "메세지", example = "사용자 정보를 찾을 수 없습니다.")
    private final String message;

    private List<T> errors;

    public static <T> ErrorResponse <T> of(int status, String messageKey, String message) {
        return new ErrorResponse<>(status, messageKey, message, Collections.emptyList());
    }

    public static <T> ErrorResponse <T> of(int status, String messageKey, String message, List<T> errors) {
        return new ErrorResponse<>(status, messageKey, message, errors);
    }

    public static <T> ErrorResponse <T> of(ErrorCode errorCode, MessageUtil messageUtil) {
        return new ErrorResponse<>(errorCode.getStatus().value(), errorCode.getMessageKey(), messageUtil.getMessage(errorCode.getMessageKey()), Collections.emptyList());
    }

    public static <T> ErrorResponse <T> of(ErrorCode errorCode, MessageUtil messageUtil, List<T> errors) {
        return new ErrorResponse<>(errorCode.getStatus().value(), errorCode.getMessageKey(), messageUtil.getMessage(errorCode.getMessageKey()), errors);
    }

    @Getter
    public static class FieldError{
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
/*
* 그냥 @AllArgsConstructor만 붙이면 아래와 같은 생성자가 자동으로 만들어지는 건데
public ErrorResponse(Integer statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
}

(staticName = "of") 옵션을 붙이면 생성자를 public으로 만들지 않고 정적 메서드로 감싸줌 -> 아래와 같은 형태임
public static ErrorResponse of(Integer statusCode, String message) {
    return new ErrorResponse(statusCode, message);
}

* staticName=of로 정적 메서드 방식을 쓰던 안쓰던 어차피 결과는 똑같음
* 근데 왜 굳이 of로 쓰냐면 일단 생성자 방식은 단순히 인스턴스만 생성하는 거고, public으로 열려있는데
* 정적 메서드 방식을 쓰면 static으로 감싸지면서 생성자는 자동으로 private로 됨
* 그리고 이 정적 메서드에 로직을 추가로 넣을 수 있다는 게 장점임. 생성자는 위의 코드와 같은 형태로 밖에 못쓰는데 of를 쓰면
*
* public static ErrorResponse of(ErrorCode errorCode) {
    log.warn("에러 발생: {}", errorCode);
    return new ErrorResponse(errorCode.getStatus().value(), errorCode.getMessage());
}
* 이런 코드도 작성이 가능함 -> 그래서 유지보수 면에서도 좋음
*/
