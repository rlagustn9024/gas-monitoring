package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.response.health.HealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.SensorPortListResponseDto;
import com.elim.server.gas_monitoring.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor")
public class SensorController {

    private final SensorService sensorService;


    /**
     * [센서 On/OFF 확인]
     * */
    @GetMapping("/health")
    public ResponseEntity<CommonResponse<HealthResponseDto>> checkGasSensorStatus(
            @RequestParam String port
    ) {
        HealthResponseDto response = sensorService.checkStatus(port);
        return ResponseEntity.ok(CommonResponse.success(response));
    }



    /**
     * [전체 COM 포트 및 센서 모델명 조회 API]
     *
     * <p>현재 시스템에 연결된 모든 USB Serial 포트를 스캔하고, 각 포트에 대해
     * <code>ATCVER</code> 명령을 전송하여 센서 모델명을 조회합니다.</p>
     *
     * <p>응답은 <b>포트명 → 모델명</b> 매핑 형태의 JSON 으로 반환됩니다.</p>
     *
     * <pre>
     * 예시 응답:
     * {
     *   "COM3": "UA58-LEL",
     *   "COM7": "UA58-KFG-U"
     * }
     * </pre>
     *
     * @return 전체 포트와 모델명 매핑 정보
     */
    @GetMapping("/mappings")
    public ResponseEntity<CommonResponse<SensorPortListResponseDto>> getAllMappings() {
        SensorPortListResponseDto response = sensorService.getAllMappings();
        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
