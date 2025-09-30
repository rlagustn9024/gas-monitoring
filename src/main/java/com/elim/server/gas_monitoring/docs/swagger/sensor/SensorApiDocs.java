package com.elim.server.gas_monitoring.docs.swagger.sensor;

import com.elim.server.gas_monitoring.dto.common.CommonResponse;
import com.elim.server.gas_monitoring.dto.response.health.HealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58lel.UA58LELMeasurementResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Sensor", description = "센서 관련 API")
public interface SensorApiDocs {

    @Operation(
            summary = "센서 On/Off 상태 확인",
            description = """
            지정된 포트에 연결된 센서가 정상적으로 응답하는지 확인합니다.
            포트명을 입력하면 장치와 연결을 시도하여 On/Off 상태를 반환합니다.
            """
    )
    @GetMapping("/health")
    ResponseEntity<CommonResponse<HealthResponseDto>> checkGasSensorStatus(
            @Parameter(description = "센서가 연결된 포트 이름(예: COM3)", example = "COM3")
            @RequestParam String port
    );

    @Operation(
            summary = "UA58-KFG 가스 센서 측정값 조회",
            description = """
            UA58-KFG 가스 센서로부터 측정값을 읽어옵니다.
            - CH1: CO 농도(ppm)
            - CH2: O₂ 농도(%)
            - CH3: H₂S 농도(ppm)
            - CH4: CH₄ 농도(ppm)
            """
    )
    @GetMapping("/ua58kfg")
    ResponseEntity<CommonResponse<UA58KFGMeasurementResponseDto>> readKfgSensorValues(
            @Parameter(description = "센서가 연결된 포트 이름(예: COM3)", example = "COM3")
            @RequestParam String port
    );

    @Operation(
            summary = "UA58-LEL 가스 센서 측정값 조회",
            description = """
            UA58-LEL 가스 센서로부터 측정값을 읽어옵니다.
            - CH1: LEL
            - CH2: 온도(℃)
            - CH3: 습도(%)
            - CH4: GAS ID
            """
    )
    @GetMapping("/ua58lel")
    ResponseEntity<CommonResponse<UA58LELMeasurementResponseDto>> readLelSensorValues(
            @Parameter(description = "센서가 연결된 포트 이름(예: COM3)", example = "COM3")
            @RequestParam String port
    );
}
