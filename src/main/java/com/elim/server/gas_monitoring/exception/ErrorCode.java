package com.elim.server.gas_monitoring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 각 예외에 해당하는 에러코드, 메세지, Http 상태코드를 여기서 정의함
    // 클라이언트 응답, 로깅, 에러 추적 시 통일된 구조 제공

    // private 생성자 호출
    INVALID("E001", "common.invalid", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("E002", "common.unauthorized", HttpStatus.UNAUTHORIZED), // 인증조차 안된 경우에 사용
    LOGIN_FAILED("E003", "member.login.failed", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("E004", "common.forbidden", HttpStatus.FORBIDDEN), // 인증은 되었지만 권한이 없거나 부족한 경우에 Forbidden 사용
    NOT_FOUND("E006", "common.not.found", HttpStatus.NOT_FOUND),
    CONFLICT("E009", "common.conflict.exception", HttpStatus.CONFLICT), // Conflict는 충돌
    EXPIRED("E005", "common.expired", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED("E007", "file.upload", HttpStatus.BAD_REQUEST),
    OBJECT_DELETE_FAILED("E007", "object.delete.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_SEND_FAILED("E008", "email.send.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    BULK_VALIDATION("E010", "common.batch.validation.failed", HttpStatus.BAD_REQUEST),
    RELATION_INVALID("E011", "common.relation.invalid", HttpStatus.BAD_REQUEST),

    // 센서 관련
    PORT_OPEN_FAILED("E012", "sensor.port.open.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SENSOR_READ_FAILED("E013", "sensor.read.failed", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code; // 이건 지금은 안쓰임
    private final String messageKey;
    private final HttpStatus status;

    ErrorCode(String code, String messageKey, HttpStatus status) { // Enum 생성자
        // 자바에서 Enum 클래스의 생성자는 반드시 private임. 따로 설정 안해도 자동으로 private
        // 생성자 호출은 오직 enum 내부에서만 허용되며, 외부에서 new로 인스턴스 생성 불가
        this.code = code;
        this.messageKey = messageKey;
        this.status = status;
    }
}
