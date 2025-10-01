package com.elim.server.gas_monitoring.domain.sensor;

import com.elim.server.gas_monitoring.domain.common.entity.SoftDeletableEntity;
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
public class UA58KFGU extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ua58kfgu_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String modelName; // 모델 명

    @Column(nullable = false, length = 50)
    private String serialNumber; // 기기 고유 번호

    // 측정값
    private String CO; // 일산화탄소 농도(ppm)
    private String O2; // 산소 농도(%)
    private String H2S; // 황화수소 농도(ppm)
    private String CO2; // 이산화탄소 농도 (ppm)
}
