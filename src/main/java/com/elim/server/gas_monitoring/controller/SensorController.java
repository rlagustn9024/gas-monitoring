package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.docs.swagger.sensor.SensorApiDocs;
import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.response.health.HealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58lel.UA58LELMeasurementResponseDto;
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
public class SensorController implements SensorApiDocs {

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
     * [가스 센서 측정값 읽기]
     * <p>
     * 가스 센서로부터 현재 측정값을 읽어옵니다.
     * 장치로 ATCQ 명령을 전송하면 다음과 같은 응답 포맷을 반환합니다:
     * </p>
     *
     * <pre>
     *     ATCQ 0,20.32,0,1769
     * </pre>
     *
     * <p>응답 필드 설명</p>
     * <ul>
     *   <li><b>첫 번째 값 (CH1)</b> : CO 농도 (ppm)</li>
     *   <li><b>두 번째 값 (CH2)</b> : O2 농도(%)</li>
     *   <li><b>세 번째 값 (CH3)</b> : H2S 농도(ppm)</li>
     *   <li><b>네 번째 값 (CH4)</b> : CO2 농도(ppm)</li>
     * </ul>
     * */
    @GetMapping("/ua58kfg")
    public ResponseEntity<CommonResponse<UA58KFGMeasurementResponseDto>> readKfgSensorValues(
            @RequestParam String port
    ) {
        UA58KFGMeasurementResponseDto response = sensorService.readValuesFromKFG(port);
        return ResponseEntity.ok(CommonResponse.success(response));
    }


    /**
     * [가스 센서 측정값 읽기]
     * <p>
     * 가스 센서로부터 현재 측정값을 읽어옵니다.
     * 장치로 ATCQ 명령을 전송하면 다음과 같은 응답 포맷을 반환합니다:
     * </p>
     *
     * <pre>
     *     ATCQ 0.00,27.10.47.73,0
     * </pre>
     *
     * <p>응답 필드 설명</p>
     * <ul>
     *   <li><b>첫 번째 값 (CH1)</b> : LEL </li>
     *   <li><b>두 번째 값 (CH2)</b> : 온도 측정값(℃) </li>
     *   <li><b>세 번째 값 (CH3)</b> : 습도 측정값(%)</li>
     *   <li><b>네 번째 값 (CH4)</b> : GAS ID </li>
     * </ul>
     * */
    @GetMapping("/ua58lel")
    public ResponseEntity<CommonResponse<UA58LELMeasurementResponseDto>> readLelSensorValues(
            @RequestParam String port
    ) {
        UA58LELMeasurementResponseDto response = sensorService.readValuesFromLEL(port);
        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
