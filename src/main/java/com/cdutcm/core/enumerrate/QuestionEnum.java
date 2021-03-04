package com.cdutcm.core.enumerrate;

/**
 * 问题枚举
 * 
 * @author FJL
 */
public enum QuestionEnum {
	/** 病位归经 */
	Bwgj(1),
	/** 证型推荐数 */
	Symptommould(2),
	/** 病名推荐数 */
	Disease(3),
	/** 默认处方推荐数 */
	RecipelDefault(4),
	/** 处方推荐总数 */
	RecipelSum(5),
	/** 订制主题 */
	RecipelStyle(6),
	/** 国际化 */
	ChineseOrEnglish(7),
	/** 药房 */
	Pharmacy(8),
	/** 证型病名不完整时是否推荐处方 */
	IsRecommendRecipel(9),
	/** 以填项是否推荐 */
	IsRecommendSelf(10),
	/** 舌像脉象 */
	TongueAndPulse(11),
	/** 是否分割症状 */
	IsSplitSymptom(12),
	/** 处方药品默认剂量 */
	RecipelDosage(13);

	private int value = 0;

	private QuestionEnum(int value) {
		this.value = value;
	}

	public static QuestionEnum valueOf(int value) {
		switch (value) {
		case 1:
			return Bwgj;
		case 2:
			return Symptommould;
		case 3:
			return Disease;
		case 4:
			return RecipelDefault;
		case 5:
			return RecipelSum;
		case 6:
			return RecipelStyle;
		case 7:
			return ChineseOrEnglish;
		case 8:
			return Pharmacy;
		case 9:
			return IsRecommendRecipel;
		case 10:
			return IsRecommendSelf;
		case 11:
			return TongueAndPulse;
		case 12:
			return IsSplitSymptom;
		case 13:
			return RecipelDosage;
		default:
			return null;
		}
	}

	public int value() {
		return this.value;
	}
}
