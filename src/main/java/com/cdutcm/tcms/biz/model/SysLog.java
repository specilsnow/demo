package com.cdutcm.tcms.biz.model;

import java.util.Date;

import com.cdutcm.core.page.Page;

public class SysLog {
    private Long id;
    
    private Page page;
    
    public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private String name;

    private String ctype;

    private String createor;

    private Date lastupdate;

    private String description;
    
    private Date starttime;
    
    private String params;
    
    private int exceptionlinenumber;
    
    private String ip;
    
    private String returnvalue;
    
    private String exceptionmessage;

    public String getExceptionmessage() {
		return exceptionmessage;
	}

	public void setExceptionmessage(String exceptionmessage) {
		this.exceptionmessage = exceptionmessage;
	}

	public String getReturnvalue() {
		return returnvalue;
	}

	public void setReturnvalue(String returnvalue) {
		this.returnvalue = returnvalue;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getExceptionlinenumber() {
		return exceptionlinenumber;
	}

	public void setExceptionlinenumber(int exceptionlinenumber) {
		this.exceptionlinenumber = exceptionlinenumber;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public String getCreateor() {
        return createor;
    }

    public void setCreateor(String createor) {
        this.createor = createor == null ? null : createor.trim();
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}