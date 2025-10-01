package com.elim.server.gas_monitoring.domain.enums.sensor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmStatus {

    // 위험 범위일 때만 알람 발생
    TRIGGERED("알람 발생"),
    INACTIVE("알람 해제");
    
    private final String description;
}
