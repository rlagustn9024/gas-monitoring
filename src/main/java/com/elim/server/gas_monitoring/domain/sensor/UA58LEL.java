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
public class UA58LEL extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ua58lel_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String modelName; // 모델 명

    @Column(nullable = false, length = 50)
    private String serialNumber; // 기기 고유 번호

    // 측정값
    private String LEL; //
    private String temperature; // 온도(c)
    private String humidity; // 습도(%)
    private String gas_id; //
}
