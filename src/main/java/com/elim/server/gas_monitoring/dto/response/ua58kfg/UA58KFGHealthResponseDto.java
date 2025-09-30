package com.elim.server.gas_monitoring.dto.response.ua58kfg;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**UA58KFG 센서 Health 응답 DTO**")
public class UA58KFGHealthResponseDto {

    @Schema(description = "연결 상태", example = "true")
    private boolean isAlive;

    public static UA58KFGHealthResponseDto of(boolean isAlive) {
        return UA58KFGHealthResponseDto.builder()
                .isAlive(isAlive)
                .build();
    }
}
