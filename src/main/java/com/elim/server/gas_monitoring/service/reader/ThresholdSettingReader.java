package com.elim.server.gas_monitoring.service.reader;

import com.elim.server.gas_monitoring.repository.ThresholdSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThresholdSettingReader {

    private final ThresholdSettingRepository thresholdSettingRepository;


}
