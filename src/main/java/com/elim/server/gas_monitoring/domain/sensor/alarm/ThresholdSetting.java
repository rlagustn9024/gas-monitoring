package com.elim.server.gas_monitoring.domain.sensor.alarm;

import com.elim.server.gas_monitoring.domain.common.entity.SoftDeletableEntity;
import com.elim.server.gas_monitoring.infra.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class ThresholdSetting extends SoftDeletableEntity {

    @Id
    @SnowflakeId
    @Column(name = "threshold_setting_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String port; // 포트 (예: COM7)

    @Column(nullable = false, length = 30)
    private String modelName; // 모델 명(예: UA58-KFG-U)

    @Column(nullable = false, length = 30)
    private String serialNumber; // 기기 고유 번호 (예: 25090199)


    // 정상 범위는 설정 안함. 이상범위, 위험범위만 설정하고 나머지는 다 정상으로 처리
    // ========================== CO ==========================
    // 이상범위 (예: 30 이상 ~ 200 이하)
    private Double coWarningMin; // 예: 30
    private Double coWarningMax; // 예: 200
    
    // 위험 범위 (예: 200 초과)
    private Double coCriticalMax; // 예: 200

    // ========================== O2 ==========================
    // 이상범위1 (예: 19.5 이상 ~ 20 이하)
    private Double o2WarningMin1; // 예: 19.5
    private Double o2WarningMin2; // 예: 20

    // 이상범위2 (예: 22 이상 ~ 23.5 이하)
    private Double o2WarningMax1; // 예: 22
    private Double o2WarningMax2; // 예: 23.5
    
    // 위험 범위 (예: 19.5 미만 ~ 23.5 초과)
    private Double o2CriticalMin; // 예: 19.5
    private Double o2CriticalMax; // 예: 23.5


    // ========================== H2S ==========================
    // 이상 범위 (예: 5 이상 ~ 50 이하)
    private Double h2sWarningMin; // 예: 5
    private Double h2sWarningMax; // 예: 50

    // 위험 범위 (예: 50 초과)
    private Double h2sCriticalMax; // 예: 50


    // ========================== CO2 ==========================
    // 이상 범위 (예: 1500 이상 ~ 5000 이하)
    private Double co2WarningMin; // 예: 1500
    private Double co2WarningMax; // 예: 5000

    // 위험 범위 (예: 5000 초과)
    private Double co2CriticalMax; // 예: 5000


    // ========================== LEL ==========================
    // 이상 범위 (예: 10 이상 ~ 25 이하)
    private Double lelWarningMin; // 예: 10
    private Double lelWarningMax; // 예: 25

    // 위험 범위 (예: 25 초과)
    private Double lelCriticalMax; // 예: 25
}
