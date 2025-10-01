package com.elim.server.gas_monitoring.service;

import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingInitResponseDto;
import com.elim.server.gas_monitoring.repository.ThresholdSettingRepository;
import com.elim.server.gas_monitoring.service.reader.ThresholdSettingReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThresholdSettingService {

    private final ThresholdSettingReader thresholdSettingReader;
    private final ThresholdSettingRepository thresholdSettingRepository;


    @Transactional
    public ThresholdSettingInitResponseDto initThresholdSettings(
            String port,
            String model,
            String serialNumber
    ) {
        ThresholdSetting thresholdSetting = ThresholdSetting.of(port, model, serialNumber);
        thresholdSettingRepository.save(thresholdSetting);
        return ThresholdSettingInitResponseDto.from(thresholdSetting);
    }
}
