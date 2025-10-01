package com.elim.server.gas_monitoring.domain.sensor.alarm;

import com.elim.server.gas_monitoring.domain.common.entity.SoftDeletableEntity;
import com.elim.server.gas_monitoring.domain.enums.sensor.AlarmLevel;
import com.elim.server.gas_monitoring.domain.enums.sensor.AlarmStatus;
import com.elim.server.gas_monitoring.infra.SnowflakeId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlarmEvent extends SoftDeletableEntity {

    @Id
    @SnowflakeId
    @Column(name = "alarm_event_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String port; // 어떤 포트에서 발생했는지 (예: COM7)

    @Column(nullable = false, length = 30)
    private String modelName; // 모델 명(예: UA58-KFG-U)

    @Column(nullable = false, length = 30)
    private String serialNumber; // 기기 고유 번호 (예: 25090199)

    @Column(nullable = false, length = 20)
    private String metric; // 측정 대상 (예: CO, O2, H2S, CO2)

    @Column(nullable = false)
    private Double value; // 측정 값

    // 알람 레벨
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlarmLevel alarmLevel;

    // 알람 상태(알람 발생, 알람 해제)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlarmStatus alarmStatus;
}
