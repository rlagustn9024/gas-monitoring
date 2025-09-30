package com.elim.server.gas_monitoring.domain.enums.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE("사용 가능"),
    DELETED("논리 삭제"),
    INACTIVE("숨김 등");

    private final String description;
}
