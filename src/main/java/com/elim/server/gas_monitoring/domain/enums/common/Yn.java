package com.elim.server.gas_monitoring.domain.enums.common;

import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.exceptions.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;

import java.util.List;

public enum Yn {
    Y, N;

    @JsonCreator
    public static Yn from(String value) {
        if (!StringUtils.hasText(value)) return null; // 빈 문자열이 오면 null 처리
        try {
            return Yn.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(ErrorCode.INVALID, "common.invalid.format", List.of(value));
        }
    }
}
