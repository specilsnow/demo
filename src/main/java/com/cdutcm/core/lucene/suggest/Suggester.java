package com.cdutcm.core.lucene.suggest;

import java.io.Serializable;

public class Suggester implements Serializable {
	private static final long serialVersionUID = 1L;
	String name;
	int times;
	long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public String toString() {
		return "Suggester [name=" + name + ", times=" + times + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// 按照你想要的方法去比较，比如我这里比较的是姓名，号码，相等就返回true
		if (!(obj instanceof Suggester)) {
      return false;
    }
		Suggester contactItem = (Suggester) obj;
		return (this.getName().equals(contactItem.getName()) && this.getName()
				.equals(contactItem.getName()));
	}

	public Suggester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Suggester(String name, long id) {
		super();
		this.name = name;
		this.id = id;
	}

}