package com.cdutcm.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:查询结果
 * @ClassName :com.cdutcm.common.util.Result.java
 * @Author :zhufj
 */
public class Result<E> implements Serializable {
	
	private static final long serialVersionUID = -6720797282594648293L;
	
	private Page page;
	private List<E> content;
	private E obj;

	public Result() { }
	public Result(Page page, List<E> content) {
		this.page = page;
		this.content = content;
	}

	public Page getPage() {
		return this.page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<E> getContent() {
		return this.content;
	}
	public void setContent(List<E> content) {
		this.content = content;
	}
	public E getObj() {
		return obj;
	}
	public void setObj(E obj) {
		this.obj = obj;
	}
}
