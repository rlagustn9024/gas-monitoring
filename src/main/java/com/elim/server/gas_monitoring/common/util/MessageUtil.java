package com.elim.server.gas_monitoring.common.util;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String getMessage(String messageKey) {
        try {
            return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
//            log.warn("[MessageUtil] 메시지 키 '{}' 가 존재하지 않습니다. (locale: {})", messageKey, LocaleContextHolder.getLocale());
            return messageKey; // 또는 "알 수 없는 오류" 같은 기본 메시지
        }
    }

    public String getMessage(String messageKey, Object[] args) {
        try {
            return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
//            log.warn("[MessageUtil] 메시지 키 '{}' 가 존재하지 않습니다. (locale: {})", messageKey, LocaleContextHolder.getLocale());
            return messageKey;
        }
    }

    /**
     * 왠만한 예외는 직접 다 검증과정에서 처리해야 하는데, 정말 예상치 못한 상황일 때 이걸 통해 처리할 수도 있음
     * */
    public String getMessage(Exception e) {
        Throwable root = ExceptionUtils.getRootCause(e) != null ? ExceptionUtils.getRootCause(e) : e;

        // 예외 유형 기반 메시지 매핑
        if (root instanceof SQLIntegrityConstraintViolationException) {
            return getMessage("db.constraint.duplicate"); // properties에 등록된 메시지 키
        }

        if (e instanceof DataIntegrityViolationException) {
            return getMessage("db.integrity.violation");
        }

        if (e instanceof ConstraintViolationException) {
            return getMessage("common.validation.fail");
        }

        if (e instanceof BindException || e instanceof MethodArgumentNotValidException) {
            return getMessage("common.binding.fail");
        }

        if (e instanceof HttpMessageNotReadableException) {
            return getMessage("common.invalid.json");
        }

        return root.getMessage() != null ? root.getMessage() : getMessage("common.unexpected.error");
    }
}
