package com.elim.server.gas_monitoring.dto.response.ua58lel;

import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**UA58LEL 센서 측정값 응답 DTO**")
public class UA58LELMeasurementResponseDto {

    @Schema(description = "LEL 측정값", example = "0.00")
    private String lel;

    @Schema(description = "온도 측정값(℃)", example = "27.10")
    private String temperature;

    @Schema(description = "습도 측정값(%)", example = "45.73")
    private String humidity;

    @Schema(description = "GAS ID", example = "0")
    private String gasId;

    public static UA58LELMeasurementResponseDto of(String[] parts) {
        return UA58LELMeasurementResponseDto.builder()
                .lel(parts[0])
                .temperature(parts[1])
                .humidity(parts[2])
                .gasId(parts[3])
                .build();
    }

    @Override
    public String toString() {
        return "UA58LELMeasurementResponseDto{" +
                "lel='" + lel + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", gasId='" + gasId + '\'' +
                '}';
    }
}
