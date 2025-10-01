package com.elim.server.gas_monitoring.dto.response.sensor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**전체 COM 포트 응답 DTO**")
public class SensorPortResponseDto {

    @Schema(description = "포트명과 센서 모델명의 매핑 정보", example = "{COM3: UA58-KFG-U}")
    private Map<String, String> sensorMap;

    public static SensorPortResponseDto of(Map<String, String> sensorMap) {
        return SensorPortResponseDto.builder()
                .sensorMap(sensorMap)
                .build();
    }
}
