package com.elim.server.gas_monitoring.service;

import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingInitResponseDto;
import com.elim.server.gas_monitoring.exception.exceptions.NotFoundException;
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


    /**
     * 초기화
     * */
    @Transactional
    public ThresholdSettingInitResponseDto initThresholdSettings(
            String port,
            String model,
            String serialNumber
    ) {
        // 기본값으로 생성
        ThresholdSetting thresholdSetting = ThresholdSetting.of(port, model, serialNumber);
        thresholdSettingRepository.save(thresholdSetting); // 저장
        return ThresholdSettingInitResponseDto.from(thresholdSetting);
    }


    /**
     * 가장 최신 ThresholdSetting 객체 1개 삭제
     * */
    @Transactional
    public void deleteThresholdSetting(String port, String model, String serialNumber) {
        ThresholdSetting thresholdSetting = getThresholdSetting(port, model, serialNumber);
        thresholdSetting.softDelete();
    }

    private ThresholdSetting getThresholdSetting(String port, String model, String serialNumber) {
        return thresholdSettingReader.fetchActiveByPortAndModelAndSerialNumber(port, model, serialNumber)
                .orElseThrow(() -> new NotFoundException("thresholdSetting.not.found"));
    }
}
