package com.cdutcm.core.lucene.suggest;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggestSerach {

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * 自动补全查询索引
	 * 
	 * @param region
	 *            查询条件
	 * @param indexPath
	 *            索引位置
	 * @return 查询结果集
	 */

	public static List<Suggester> lookup(String region, String indexPath,
			int index) {
		// 返回的结果集
		List<Suggester> reList = new ArrayList<Suggester>();
		// 索引文件
		File indexFile = new File(indexPath);
		// 索引创建管理工具
		AnalyzingInfixSuggester AnalyzingInfixSuggester = null;
		// 查询结果集
		List<LookupResult> results = null;
		boolean flag = false;
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
		try {
			AnalyzingInfixSuggester = new AnalyzingInfixSuggester(
					Version.LUCENE_47, indexFile, analyzer);
			if (region.matches(".*[a-zA-z].*")) {
				flag = true;
			}
			if (region.length() >= 3) {
				flag = true;
			}
			/*
			 * 查询结果 region- 查询的关键词 TOPS- 返回的最多数量 allTermsRequired -
			 * should或者must关系 doHighlight - 高亮
			 */
			results = AnalyzingInfixSuggester.lookup(region, index, flag, true);
		} catch (IOException e) {
			errorlogger.error(e.toString());
		} finally {
			try {
				AnalyzingInfixSuggester.close();
			} catch (IOException e) {
				errorlogger.error(e.toString());
			}
		}
		// System.out.println("输入词：" + region);

		// suggestIndex.splitWord(region, true);
		for (LookupResult result : results) {
			String str = (String) result.highlightKey;
			long id = 0;
			try {
				// 获取payload部分id信息 —— id
				BytesRef bytesRef = result.payload;
				DataInputStream dis = new DataInputStream(
						new ByteArrayInputStream(bytesRef.bytes));
				id = dis.readLong();
				// System.out.println(id);
				dis.close();
			} catch (Exception e) {
				errorlogger.error(e.toString());
			}
			reList.add(new Suggester(str, id));
		}
		/*
		 * 剔除搜索关键词自身
		 */
		for (int i = 0; i < reList.size(); i++) {
			Suggester sug = reList.get(i);
			// 剔除高亮标签后进行比较
			if (sug.getName().replaceAll("<[^>]*>", "")
					.replaceAll("[a-zA-Z]", "").equals(region)) {
				reList.remove(sug);
				break;
			}
		}
		// ComparatorSuggest comparator=new ComparatorSuggest();
		// Collections.sort(reList, comparator);
		return reList;
	}

}