package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.request.threshold.ThresholdSettingUpdateRequestDto;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingInitResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingUpdateResponseDto;
import com.elim.server.gas_monitoring.service.ThresholdSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/threshold")
public class ThresholdSettingController {

    private final ThresholdSettingService thresholdSettingService;


    /**
     * 초기화
     * <p>
     *     웹 소켓 연결 후, 구독하기 전에 센서 매핑정보 기반으로 Threshold init
     * </p>
     * */
    @GetMapping("/init")
    public ResponseEntity<CommonResponse<ThresholdSettingInitResponseDto>> initThresholdSettings(
            @RequestParam String port,
            @RequestParam String model,
            @RequestParam String serialNumber
    ) {
        ThresholdSettingInitResponseDto response =
                thresholdSettingService.initThresholdSettings(port, model, serialNumber);
        return ResponseEntity.ok(CommonResponse.success(response));
    }


    /**
     * 구독(세션) 종료되면 ThresholdSetting도 같이 논리 삭제. Audit으로 로그 남김
     * */
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteThresholdSetting(
            @RequestParam String port,
            @RequestParam String model,
            @RequestParam String serialNumber
    ) {
        thresholdSettingService.deleteThresholdSetting(port, model, serialNumber);
        return ResponseEntity.ok(CommonResponse.success());
    }


    /**
     * 사용자 요청 기반으로 ThresholdSetting 수정. Audit으로 로그 남김
     * */
    @PutMapping
    public ResponseEntity<CommonResponse<ThresholdSettingUpdateResponseDto>> updateThresholdSetting(
            @RequestBody ThresholdSettingUpdateRequestDto dto
    ) {
        ThresholdSettingUpdateResponseDto response = thresholdSettingService.updateThresholdSetting(dto);
        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
