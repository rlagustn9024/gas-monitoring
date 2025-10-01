package com.elim.server.gas_monitoring.dto.response.sensor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "개별 센서 포트 정보 DTO")
public class SensorPortResponseDto {

    @Schema(description = "포트명", example = "COM3")
    private String portName;

    @Schema(description = "센서 모델명", example = "UA58-KFG-U")
    private String modelName;

    @Schema(description = "센서 시리얼 번호", example = "SN12345678")
    private String serialNumber;

    public static SensorPortResponseDto of(String portName, String modelName, String serialNumber) {
        return SensorPortResponseDto.builder()
                .portName(portName)
                .modelName(modelName)
                .serialNumber(serialNumber)
                .build();
    }
}
