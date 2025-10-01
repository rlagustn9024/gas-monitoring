package com.elim.server.gas_monitoring.domain.enums.sensor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmLevel {

    NORMAL("정상범위"),
    WARNING("이상범위"),
    CRITICAL("위험범위");

    private final String description;
}
