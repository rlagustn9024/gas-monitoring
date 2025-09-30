package com.elim.server.gas_monitoring.exception.exceptions;


import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.GlobalBaseException;

public class NotFoundException extends GlobalBaseException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String messageKey) {
        super(ErrorCode.NOT_FOUND, messageKey);
    }

    public NotFoundException(String messageKey, Object... args) {
        super(ErrorCode.NOT_FOUND, messageKey, args); // 메시지 키와 파라미터를 넘김
    }
}
