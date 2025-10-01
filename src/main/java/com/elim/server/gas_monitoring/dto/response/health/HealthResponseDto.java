package com.elim.server.gas_monitoring.dto.response.health;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**UA58KFGU 센서 Health 응답 DTO**")
public class HealthResponseDto {

    @Schema(description = "연결 상태", example = "true")
    private boolean isAlive;

    public static HealthResponseDto of(boolean isAlive) {
        return HealthResponseDto.builder()
                .isAlive(isAlive)
                .build();
    }
}
