package com.elim.server.gas_monitoring.common.cache;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheKeyGenerator {

    public String generateThresholdSettingKey(String model, String port, String serialNumber) {
        return String.join("|",
                model,
                port,
                serialNumber
        );
    }
}
