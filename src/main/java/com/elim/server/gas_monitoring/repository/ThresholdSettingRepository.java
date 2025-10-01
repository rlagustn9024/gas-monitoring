package com.elim.server.gas_monitoring.repository;

import com.elim.server.gas_monitoring.domain.enums.common.Status;
import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThresholdSettingRepository extends JpaRepository<ThresholdSetting, Long> {


    Optional<ThresholdSetting> findTop1ByPortAndModelAndSerialNumberAndStatusOrderByCreatedAtDesc(
            String port,
            String model,
            String serialNumber,
            Status status
    );
}
