package com.elim.server.gas_monitoring.dto.response.sensor.alarm;

import com.elim.server.gas_monitoring.domain.enums.sensor.AlarmLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**알람 상태 응답 DTO**")
public class AlarmResultResponseDto {

    @Schema(description = "알람 레벨(정상 범위, 이상 범위, 위험 범위", example = "WARNING")
    private AlarmLevel alarmLevel;

    @Schema(description = "알람 메세지", example = "CO₂ 경고 범위 (1842.0 ppm)")
    private List<String> messages;

    public static AlarmResultResponseDto of(AlarmLevel alarmLevel, List<String> messages) {
        return AlarmResultResponseDto.builder()
                .alarmLevel(alarmLevel)
                .messages(messages)
                .build();
    }

    public static AlarmResultResponseDto normal() {
        return AlarmResultResponseDto.builder()
                .alarmLevel(AlarmLevel.NORMAL)
                .messages(List.of("정상"))
                .build();
    }
}
