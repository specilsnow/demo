package com.cdutcm.core.lucene.suggest;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.similarities.DefaultSimilarity;

/**
 * 自定义排序相同的排在前面
 * 
 * @author fw
 * 
 */
public class MyselfSimilarity extends DefaultSimilarity {

	@Override
	public float tf(float freq) {
		return 1.0f;
	}

	@Override
	public float idf(long docFreq, long numDocs) {
		return 1.0f;

	}

	@Override
	public float lengthNorm(FieldInvertState state) {
		final int numTerms;
		if (discountOverlaps) {
      numTerms = state.getLength() - state.getNumOverlap();
    } else {
      numTerms = state.getLength();
    }
		return state.getBoost() * ((float) (1.0 / numTerms));

	}
}
