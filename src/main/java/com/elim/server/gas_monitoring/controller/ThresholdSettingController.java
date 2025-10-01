package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.response.sensor.threshold.ThresholdSettingInitResponseDto;
import com.elim.server.gas_monitoring.service.ThresholdSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/threshold")
public class ThresholdSettingController {

    private final ThresholdSettingService thresholdSettingService;


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

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deleteThresholdSetting(
            @RequestParam String port,
            @RequestParam String model,
            @RequestParam String serialNumber
    ) {
        thresholdSettingService.deleteThresholdSetting(port, model, serialNumber);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
