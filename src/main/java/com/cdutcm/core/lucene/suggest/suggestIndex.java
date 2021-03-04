package com.cdutcm.core.lucene.suggest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class suggestIndex {

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * 创建索引
	 * 
	 * @param list
	 *            待建立索引的数据集
	 * @return 创建时间
	 * @throws IOException
	 */
	public static double create(List<Suggester> list, String indexPath)
			throws IOException {
		// 耗时
		long time = 0L;
		// 索引创建管理工具
		AnalyzingInfixSuggester AnalyzingInfixSuggester = null;
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
		// Analyzer analyzer=new IKAnalyzer();
		try {
			AnalyzingInfixSuggester = new AnalyzingInfixSuggester(
					Version.LUCENE_47, new File(indexPath), analyzer);
//			System.out.println("开始创建自动补全索引");
			Long begin = System.currentTimeMillis();
			// build索引
			AnalyzingInfixSuggester
					.build(new SuggesterIterator(list.iterator()));
			Long end = System.currentTimeMillis();
			time = end - begin;
//			System.out.println("创建自动补全索引完成！耗时： " + time + "ms");
		} catch (IOException e) {
			errorlogger.error(e.toString());
		} finally {

		}
		return time / 1000.0;
	}
}
