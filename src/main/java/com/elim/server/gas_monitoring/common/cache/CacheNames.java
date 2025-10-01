package com.elim.server.gas_monitoring.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CacheNames {

    // ThresholdSetting 캐시
    THRESHOLD_SETTINGS("thresholdSettings");

    private final String name;

    // 전체 이름 배열로 반환
    public static String[] all() {
        return Arrays.stream(values())
                .map(CacheNames::getName)
                .toArray(String[]::new);
    }
}
