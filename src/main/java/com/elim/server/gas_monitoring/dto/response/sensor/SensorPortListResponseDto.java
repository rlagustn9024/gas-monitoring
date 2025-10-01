package com.elim.server.gas_monitoring.dto.response.sensor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "전체 센서 및 포트 정보 응답 DTO")
public class SensorPortListResponseDto {

    @Schema(description = "개별 포트 및 센서 정보 목록")
    private List<SensorPortResponseDto> sensors;

    public static SensorPortListResponseDto of(List<SensorPortResponseDto> sensors) {
        return SensorPortListResponseDto.builder()
                .sensors(sensors)
                .build();
    }
}
