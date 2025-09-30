package com.elim.server.gas_monitoring.exception.exceptions;


import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.GlobalBaseException;

/**
 * 권한 부족 (로그인은 되어있지만 권한 없음)
 * */
public class AuthorizationException extends GlobalBaseException {

    public AuthorizationException() {
        super(ErrorCode.FORBIDDEN);
    }

    public AuthorizationException(String messageKey, Object... args) {
        super(ErrorCode.FORBIDDEN, messageKey, args);
    }
}
