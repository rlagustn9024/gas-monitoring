package com.elim.server.gas_monitoring.service;

import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import com.elim.server.gas_monitoring.dto.request.threshold.ThresholdSettingUpdateRequestDto;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingInitResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingUpdateResponseDto;
import com.elim.server.gas_monitoring.exception.exceptions.NotFoundException;
import com.elim.server.gas_monitoring.repository.ThresholdSettingRepository;
import com.elim.server.gas_monitoring.service.reader.ThresholdSettingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThresholdSettingService {

    private final ThresholdSettingReader thresholdSettingReader;
    private final ThresholdSettingRepository thresholdSettingRepository;


    /**
     * 초기화
     * <p>
     *     웹 소켓 연결 후, 구독하기 전에 센서 매핑정보 기반으로 Threshold init
     * </p>
     * */
    @Transactional
    public ThresholdSettingInitResponseDto initThresholdSettings(
            String model,
            String port,
            String serialNumber
    ) {
        // 기본값으로 생성
        return thresholdSettingReader.fetchActiveByModelAndPortAndSerialNumber(model, port, serialNumber)
                .map(ThresholdSettingInitResponseDto::from) // 있으면 그대로 반환
                .orElseGet(() -> { // 없으면 새로 생성
                    ThresholdSetting thresholdSetting = ThresholdSetting.of(model, port, serialNumber);
                    thresholdSettingRepository.save(thresholdSetting);
                    return ThresholdSettingInitResponseDto.from(thresholdSetting);
                });
    }


    /**
     * 구독(세션) 종료되면 ThresholdSetting도 같이 논리 삭제 (가장 최신 Active ThresholdSetting 조회해서 삭제함)
     * */
    @Transactional
    public void deleteThresholdSetting(String model, String port, String serialNumber) {
        ThresholdSetting thresholdSetting = getThresholdSetting(model, port, serialNumber);
        thresholdSetting.softDelete();
    }

    private ThresholdSetting getThresholdSetting(String model, String port, String serialNumber) {
        return thresholdSettingReader.fetchActiveByModelAndPortAndSerialNumber(model, port, serialNumber)
                .orElseThrow(() -> new NotFoundException("thresholdSetting.not.found"));
    }


    /**
     * 사용자 요청 기반으로 ThresholdSetting 수정. Audit으로 로그 남김
     * */
    public ThresholdSettingUpdateResponseDto updateThresholdSetting(ThresholdSettingUpdateRequestDto dto) {
        return null;
    }
}
