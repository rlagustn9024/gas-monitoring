package com.elim.server.gas_monitoring.exception.exceptions;


import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.GlobalBaseException;

/**
 * 인증 실패, 인증 안됨 등 처리
 * */
public class UnauthorizedException extends GlobalBaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String messageKey, Object... args) {
        super(ErrorCode.UNAUTHORIZED, messageKey, args);
    }
}
