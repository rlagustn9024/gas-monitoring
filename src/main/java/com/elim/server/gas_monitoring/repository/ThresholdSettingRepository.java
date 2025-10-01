package com.elim.server.gas_monitoring.repository;

import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdSettingRepository extends JpaRepository<ThresholdSetting, Long> {


}
