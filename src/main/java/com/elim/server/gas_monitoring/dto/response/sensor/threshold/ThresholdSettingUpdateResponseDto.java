package com.elim.server.gas_monitoring.dto.response.sensor.threshold;

import com.elim.server.gas_monitoring.domain.sensor.threshold.ThresholdSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "**임계값 수정 응답 DTO**")
public class ThresholdSettingUpdateResponseDto {

    @Schema(description = "모델 명", example = "UA58-KFG-U")
    private String model; // 모델 명(예: UA58-KFG-U)

    @Schema(description = "포트 번호", example = "COM7")
    private String port; // 포트 (예: COM7)

    @Schema(description = "기기 고유 번호", example = "25090199")
    private String serialNumber; // 기기 고유 번호 (예: 25090199)


    // ========================== CO ==========================
    @Schema(description = "CO 이상범위 최소값", example = "30")
    private Double coWarningMin;

    @Schema(description = "CO 이상범위 최대값", example = "200")
    private Double coWarningMax;

    @Schema(description = "CO 위험범위 최대값(초과)", example = "200")
    private Double coCriticalMax;


    // ========================== O2 ==========================
    @Schema(description = "O2 이상범위1 최소값", example = "19.5")
    private Double o2WarningMin1;

    @Schema(description = "O2 이상범위1 최대값", example = "20")
    private Double o2WarningMin2;

    @Schema(description = "O2 이상범위2 최소값", example = "22")
    private Double o2WarningMax1;

    @Schema(description = "O2 이상범위2 최대값", example = "23.5")
    private Double o2WarningMax2;

    @Schema(description = "O2 위험범위 최소값(미만)", example = "19.5")
    private Double o2CriticalMin;

    @Schema(description = "O2 위험범위 최대값(초과)", example = "23.5")
    private Double o2CriticalMax;


    // ========================== H2S ==========================
    @Schema(description = "H2S 이상범위 최소값", example = "5")
    private Double h2sWarningMin;

    @Schema(description = "H2S 이상범위 최대값", example = "50")
    private Double h2sWarningMax;

    @Schema(description = "H2S 위험범위 최대값(초과)", example = "50")
    private Double h2sCriticalMax;


    // ========================== CO2 ==========================
    @Schema(description = "CO2 이상범위 최소값", example = "1500")
    private Double co2WarningMin;

    @Schema(description = "CO2 이상범위 최대값", example = "5000")
    private Double co2WarningMax;

    @Schema(description = "CO2 위험범위 최대값(초과)", example = "5000")
    private Double co2CriticalMax;


    // ========================== LEL ==========================
    @Schema(description = "LEL 이상범위 최소값", example = "10")
    private Double lelWarningMin;

    @Schema(description = "LEL 이상범위 최대값", example = "25")
    private Double lelWarningMax;

    @Schema(description = "LEL 위험범위 최대값(초과)", example = "25")
    private Double lelCriticalMax;


    public static ThresholdSettingUpdateResponseDto of(ThresholdSetting thresholdSetting) {
        return ThresholdSettingUpdateResponseDto.builder()
                .model(thresholdSetting.getModel())
                .port(thresholdSetting.getPort())
                .serialNumber(thresholdSetting.getSerialNumber())

                .coWarningMin(thresholdSetting.getCoWarningMin())
                .coWarningMax(thresholdSetting.getCoWarningMax())
                .coCriticalMax(thresholdSetting.getCoCriticalMax())

                .o2WarningMin1(thresholdSetting.getO2WarningMin1())
                .o2WarningMin2(thresholdSetting.getO2WarningMin2())
                .o2WarningMax1(thresholdSetting.getO2WarningMax1())
                .o2WarningMax2(thresholdSetting.getO2WarningMax2())
                .o2CriticalMin(thresholdSetting.getO2CriticalMin())
                .o2CriticalMax(thresholdSetting.getO2CriticalMax())

                .h2sWarningMin(thresholdSetting.getH2sWarningMin())
                .h2sWarningMax(thresholdSetting.getH2sWarningMax())
                .h2sCriticalMax(thresholdSetting.getH2sCriticalMax())

                .co2WarningMin(thresholdSetting.getCo2WarningMin())
                .co2WarningMax(thresholdSetting.getCo2WarningMax())
                .co2CriticalMax(thresholdSetting.getCo2CriticalMax())

                .lelWarningMin(thresholdSetting.getLelWarningMin())
                .lelWarningMax(thresholdSetting.getLelWarningMax())
                .lelCriticalMax(thresholdSetting.getLelCriticalMax())
                .build();
    }
}
