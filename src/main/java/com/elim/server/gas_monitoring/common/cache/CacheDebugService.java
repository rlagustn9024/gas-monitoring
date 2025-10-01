package com.elim.server.gas_monitoring.common.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheDebugService {

    private final CacheManager cacheManager;

    public void printAllCacheValues() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            var cache = cacheManager.getCache(cacheName);
            System.out.println("==== Cache: " + cacheName + " ====");
            if (cache instanceof org.springframework.cache.caffeine.CaffeineCache caffeineCache) {
                var nativeCache = caffeineCache.getNativeCache(); // Caffeine Cache 객체
                nativeCache.asMap().forEach((k, v) -> {
                    System.out.println("key=" + k + ", value=" + v);
                });
            }
        });
    }
}
