package com.elim.server.gas_monitoring.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(name = "PageResponseDto", description = "**페이지네이션 응답 포맷 (content + page 정보)**")
public class PageResponseDto <T> {

    @Schema(description = "실제 응답 데이터 목록", example = "[...]")
    private List<T> content;

    @Schema(description = "페이지 정보")
    private PageInfoDto page;

    public static <T> PageResponseDto <T> of(Page<T> page) {
        return PageResponseDto.<T>builder()
                .content(page.getContent())
                .page(PageInfoDto.of(page))
                .build();
    }

    public static <T> PageResponseDto <T> of(Page<T> page, long totalCount) {
        return PageResponseDto.<T>builder()
                .content(page.getContent())
                .page(PageInfoDto.of(page, totalCount))
                .build();
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Schema(name = "PageInfoDto", description = "**페이지네이션 메타 정보**")
    public static class PageInfoDto {

        @Schema(description = "요청한 페이지 크기", example = "15")
        private int size;           // 요청한 페이지 크기

        @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
        private int number;         // 현재 페이지 번호 (0부터 시작)

        @Schema(description = "전체 데이터 수", example = "148")
        private long totalElements; // 전체 데이터 수

        @Schema(description = "전체 페이지 수", example = "10")
        private int totalPages;     // 전체 페이지 수
//    private boolean hasNext;    // 다음 페이지 여부
//    private boolean hasPrevious; // 이전 페이지 여부

        public static PageInfoDto of(Page<?> page) {
            return PageInfoDto.builder()
                    .size(page.getSize())
                    .number(page.getNumber())
                    .totalElements(page.getTotalElements())
                    .totalPages(page.getTotalPages())
//                .hasNext(page.hasNext()) 지금은 이거 필요 없는 거 같아서 제외함
//                .hasPrevious(page.hasPrevious())
                    .build();
        }

        public static PageInfoDto of(Page<?> page, long totalCount) {
            return PageInfoDto.builder()
                    .size(page.getSize())
                    .number(page.getNumber())
                    .totalElements(totalCount) // 캐시에서 가져온 값
                    .totalPages((int) Math.ceil((double) totalCount / page.getSize()))
//                .hasNext(page.hasNext()) 지금은 이거 필요 없는 거 같아서 제외함
//                .hasPrevious(page.hasPrevious())
                    .build();
        }
    }
}
