package com.cdutcm.tcms.biz.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author: mxq
 * @date: 2019/12/6 09:33
 * @desc:
 */
@Data
@Builder
public class TreatDataDTO {
    //治疗前温度数组
    private  String[] temperatureListBefore;

    //治疗后温度数组
    private  String[] temperatureListAfter;

    //治疗前电阻数组
    private  String[] resistanceListBefore;

    //治疗后电阻数组
    private  String[] resistanceListAfter;

    public TreatDataDTO() {
    }

    public TreatDataDTO(String[] temperatureListBefore, String[] temperatureListAfter, String[] resistanceListBefore, String[] resistanceListAfter) {
        this.temperatureListBefore = temperatureListBefore;
        this.temperatureListAfter = temperatureListAfter;
        this.resistanceListBefore = resistanceListBefore;
        this.resistanceListAfter = resistanceListAfter;
    }
}
