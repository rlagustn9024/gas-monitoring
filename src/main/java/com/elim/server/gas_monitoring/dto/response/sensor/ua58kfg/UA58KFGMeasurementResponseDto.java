package com.elim.server.gas_monitoring.dto.response.sensor.ua58kfg;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**UA58KFGU 센서 측정값 응답 DTO**")
public class UA58KFGMeasurementResponseDto {

    @Schema(description = "CO 측정값(ppm)", example = "3")
    private String co;

    @Schema(description = "O2 측정값(%)", example = "20.36")
    private String o2;

    @Schema(description = "H2S 측정값(ppm)", example = "0")
    private String h2s;

    @Schema(description = "CO2 측정값(ppm)", example = "1461")
    private String co2;


    public static UA58KFGMeasurementResponseDto of(String[] parts) {
        return UA58KFGMeasurementResponseDto.builder()
                .co(parts[0])
                .o2(parts[1])
                .h2s(parts[2])
                .co2(parts[3])
                .build();
    }

    @Override
    public String toString() {
        return "UA58KFGMeasurementResponseDto{" +
                "co='" + co + '\'' +
                ", o2='" + o2 + '\'' +
                ", h2s='" + h2s + '\'' +
                ", co2='" + co2 + '\'' +
                '}';
    }
}
