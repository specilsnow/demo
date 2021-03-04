package com.cdutcm.tcms.biz.model;

import lombok.Data;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/11/18 16:16
 * @desc:
 */
@Data
public class Recommendation {
    private List<ForRecommend> symptoms;
    private List<ForRecommend> symptommoulds;
    private List<ForRecommend> diseases;
    private List<ForRecommend> recipels;
}
