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


}
