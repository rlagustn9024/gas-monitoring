package com.elim.server.gas_monitoring.exception;

import lombok.Getter;

@Getter
public abstract class GlobalBaseException extends RuntimeException {

    /**
     * 모든 커스텀 예외의 부모 클래스, RuntimeException을 상속받음
     * 하위 예외 클래스는 이 클래스를 상속해서 코드/메시지/상태코드 통합 처리
     * 에러 코드를 받아와서 super를 통해 쭉 올라가서 message에 저장함
     * */

    private final ErrorCode errorCode;
    private final String messageKey;
    private final Object[] args;

    /**
     * GlobalBaseException은 직접 사용하는 예외가 아니기 때문에 Protected 선언(하위 예외 클래스들이 상속해서 씀)
     * 이렇게 MessageKey를 별도로 주지 않으면 null을 넣고 아래의 생성자를 호출함
     * */
    protected GlobalBaseException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    protected GlobalBaseException(ErrorCode errorCode, String messageKey, Object... args) {
        super(messageKey); // super는 부모 클래스의 생성자 또는 메서드를 호출하는 키워드임
        // 여기서 super로 호출하면 GlobalBaseException의 부모 클래스인 RuntimeException의 생성자를 호출하는 거임
        this.errorCode = errorCode;
        this.messageKey = messageKey;
        this.args = args;
    }
}