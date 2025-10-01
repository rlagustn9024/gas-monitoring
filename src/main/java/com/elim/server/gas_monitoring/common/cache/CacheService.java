package com.elim.server.gas_monitoring.common.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    /**
     * [트랜잭션 커밋 이후에 캐시 무효화]
     * <p> Key 값을 기반으로 캐시의 일부를 무효화하는게 아니라 해당 이름을 가진 캐시를 통째로 날림. 캐시 키 조합이 많은 경우 이런식으로
     * 그냥 통째로 날리는 방식을 사용함 </p>
     * */
    public void evictAfterCommit(CacheNames... cacheNames) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Arrays.stream(cacheNames)
                        .map(CacheNames::getName)
                        .map(name -> Objects.requireNonNull(cacheManager.getCache(name), "Cache not found: " + name))
                        .forEach(Cache::clear);
            }
        });
    }

    /**
     * key 값을 기반으로 캐시 무효화. 해당 이름을 가진 캐시를 통째로 날리는게 아님
     * */
    public void evictAfterCommit(CacheNames cacheName, Object key) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                String name = cacheName.getName();
                Cache cache = Objects.requireNonNull(cacheManager.getCache(name),
                        "Cache not found: " + cacheName);
                cache.evict(key);
            }
        });
    }

    public void evictAllAfterCommit(CacheNames cacheName, List<?> keys) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                String name = cacheName.getName();
                Cache cache = Objects.requireNonNull(cacheManager.getCache(name),
                        "Cache not found: " + cacheName);
                keys.forEach(cache::evict); // bulk eviction
            }
        });
    }
}
