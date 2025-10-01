package com.elim.server.gas_monitoring.service.reader;

import com.elim.server.gas_monitoring.domain.enums.common.Status;
import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import com.elim.server.gas_monitoring.repository.ThresholdSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ThresholdSettingReader {

    private final ThresholdSettingRepository thresholdSettingRepository;


    @Cacheable(
            value = "thresholdSettings",
            key = "@cacheKeyGenerator.generateThresholdSettingKey(#model, #port, #serialNumber)"
    )
    public Optional<ThresholdSetting> fetchActiveByModelAndPortAndSerialNumber(
            String model,
            String port,
            String serialNumber
    ) {
        return thresholdSettingRepository.findTop1ByModelAndPortAndSerialNumberAndStatusOrderByCreatedAtDesc(
                model,
                port,
                serialNumber,
                Status.ACTIVE
        );
    }
}
