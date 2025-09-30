package com.elim.server.gas_monitoring.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**공통 응답 포맷**")
public class CommonResponse<T> {
    
    private static final String MESSAGE_SUCCESS = "성공";

    @Schema(description = "HTTP 상태 코드", example = "200")
    private int code;

    @Schema(description = "상태 메시지", example = "성공")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .code(200)
                .message(MESSAGE_SUCCESS)
                .data(data)
                .build();
    }

    public static CommonResponse<Void> success() {
        return CommonResponse.<Void>builder()
                .code(200)
                .message(MESSAGE_SUCCESS)
                .build();
    }

    public static CommonResponse<Void> success(String message) {
        return CommonResponse.<Void>builder()
                .code(200)
                .message(message)
                .build();
    }
}
