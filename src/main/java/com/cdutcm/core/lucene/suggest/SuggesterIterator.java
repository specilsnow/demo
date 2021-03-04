package com.cdutcm.core.lucene.suggest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggesterIterator implements InputIterator {

	/** 集合的迭代器 */
	private final Iterator<Suggester> suggesterIterator;

	/** 遍历的当前的Suggerter */
	private Suggester currentSuggester;

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * 构造方法
	 * 
	 * @param suggesterIterator
	 */
	public SuggesterIterator(Iterator<Suggester> suggesterIterator) {
		this.suggesterIterator = suggesterIterator;
	}

	/*
	 * 迭代下一个
	 * 
	 * @see org.apache.lucene.util.BytesRefIterator#next()
	 */
	@Override
	public BytesRef next() throws IOException {
		if (suggesterIterator.hasNext()) {
			currentSuggester = suggesterIterator.next();
			String term = currentSuggester.getName();
			try {
				return new BytesRef(term.getBytes("UTF8"));
			} catch (UnsupportedEncodingException e) {
				errorlogger.error(e.toString());
			}
		}
		// 如果出错或者遍历完返回空
		return null;
	}

	/*
	 * 是否有payload数据信息
	 * 
	 * @see org.apache.lucene.search.suggest.InputIterator#hasPayloads()
	 */
	@Override
	public boolean hasPayloads() {
		return true;
	}

	/*
	 * payload数据,存其他后期需要取出的各种数据，这里存id
	 * 
	 * @see org.apache.lucene.search.suggest.InputIterator#payload()
	 */
	@Override
	public BytesRef payload() {
		/** 如hasPayloads retrun false 以下代码无用 */
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			dos.writeLong(currentSuggester.getId());
			dos.close();
			return new BytesRef(bos.toByteArray());
		} catch (IOException e) {
			errorlogger.error(e.toString());
		}
		return null;
	}

	/*
	 * 自定义的排序规则
	 * 
	 * @see org.apache.lucene.search.suggest.InputIterator#weight()
	 */
	@Override
	public long weight() {
		// 当前权重为词频
		return currentSuggester.getTimes();
	}

	/*
	 * @see org.apache.lucene.util.BytesRefIterator#getComparator()
	 */
	@Override
	public Comparator<BytesRef> getComparator() {
//		System.out.println("1");
		return null;
	}

}
