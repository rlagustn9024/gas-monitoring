package com.elim.server.gas_monitoring.service.reader;

import com.elim.server.gas_monitoring.domain.enums.common.Status;
import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import com.elim.server.gas_monitoring.repository.ThresholdSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ThresholdSettingReader {

    private final ThresholdSettingRepository thresholdSettingRepository;


    public Optional<ThresholdSetting> fetchActiveByPortAndModelAndSerialNumber(
            String port,
            String model,
            String serialNumber
    ) {
        return thresholdSettingRepository.findTop1ByPortAndModelAndSerialNumberAndStatusOrderByCreatedAtDesc(
                port,
                model,
                serialNumber,
                Status.ACTIVE
        );
    }
}
