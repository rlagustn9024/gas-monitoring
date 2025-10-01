package com.elim.server.gas_monitoring.config;

import com.elim.server.gas_monitoring.common.cache.CacheNames;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {
    
    private static final Duration DEFAULT_TTL = Duration.ofMinutes(60); // TTL 60분
    private static final int DEFAULT_MAX_SIZE = 1000;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheNames.all());
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(DEFAULT_TTL) // TTL 10분
                        .maximumSize(DEFAULT_MAX_SIZE) // 1000개까지 넣을 수 있도록 설정. 1000개 넘어가면 가장 오래된 항목부터 자동 제거
        );
        return cacheManager;
    }
}
