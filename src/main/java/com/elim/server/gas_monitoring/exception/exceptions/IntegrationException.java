package com.elim.server.gas_monitoring.exception.exceptions;


import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.GlobalBaseException;

/**
 * 외부 시스템 연동 실패 (SMTP, 외부 API, 외부 DB 등)
 * <p>연동 성공 여부가 중요한게 아니라 연동 과정에서 발생한 모든 비정상 결과는 전부 IntegrationException 으로 처리함</p>
 * */
public class IntegrationException extends GlobalBaseException {

    public IntegrationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IntegrationException(ErrorCode errorCode, String messageKey, Object... args) {
        super(errorCode, messageKey, args);
    }
}
