package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.config.SerialPortConfig;
import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGHealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.exceptions.IntegrationException;
import com.elim.server.gas_monitoring.service.UA58KFGSensorService;
import com.fazecast.jSerialComm.SerialPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UA58KFGSensorController {

    private final UA58KFGSensorService ua58KFGSensorService;


    /**
     * [센서 On/OFF 확인]
     * */
    @GetMapping
    public ResponseEntity<CommonResponse<UA58KFGHealthResponseDto>> checkGasSensorStatus(
            @RequestParam String port
    ) {
        UA58KFGHealthResponseDto response = ua58KFGSensorService.checkStatus(port);
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
    @GetMapping("/read")
    public ResponseEntity<CommonResponse<UA58KFGMeasurementResponseDto>> readGasSensorValues(
            @RequestParam String port
    ) {
        UA58KFGMeasurementResponseDto response = ua58KFGSensorService.readValues(port);
        return ResponseEntity.ok(CommonResponse.success(response));
    }
}
