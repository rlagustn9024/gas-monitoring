package com.elim.server.gas_monitoring.exception.exceptions;


import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.GlobalBaseException;

public class BusinessException extends GlobalBaseException {

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String messageKey, Object... args) {
        super(errorCode, messageKey, args);
    }
}
