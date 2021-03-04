package com.cdutcm.core.lucene.suggest;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ComparatorSuggest implements Comparator {

	@Override
  public int compare(Object obj0, Object obj1) {
		Suggester s0 = (Suggester) obj0;
		Suggester s1 = (Suggester) obj1;

		// 首先比较年龄，如果年龄相同，则比较名字

		int flag = String.valueOf(s0.getTimes()).compareTo(
				String.valueOf(s1.getTimes()));
		if (flag == 0) {
			return s0.getName().compareTo(s1.getName());
		} else {
			return flag;
		}
	}

}