package com.cdutcm.core.page;

/**
 * @author       zhufj
 * @Description  分页类
 * @Date         2016-9-20
 */
public class Page {
	private int showCount=10; //每页显示记录数
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	public int getTotalPage() {
		if(totalResult%showCount==0) {
      totalPage = totalResult/showCount;
    } else {
      totalPage = totalResult/showCount+1;
    }
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getCurrentPage() {
		if(currentPage<=0) {
      currentPage = 1;
    }
//		if(currentPage>getTotalPage())
//			currentPage = getTotalPage();
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if(totalResult>0){
			sb.append("	<ul class=\"pagination\">\n");
			if(currentPage==1){
//				sb.append("	<li class=\"active\">首页</li>\n");
//				sb.append("	<li class=\"pageinfo\">上页</li>\n");
				sb.append(" <li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li>" );
			}else{	
//				sb.append("	<li><a href=\"#@\" onclick=\"searchNextPage(1)\">首页</a></li>\n");
//				sb.append("	<li><a href=\"#@\" onclick=\"searchNextPage("+(currentPage-1)+",this)\">上页</a></li>\n");
				sb.append(" <li class=\"disabled\"><a href=\"#\" aria-label=\"Previous\" onclick=\"searchNextPage("+(currentPage-1)+",this)\"><span aria-hidden=\"true\">«</span></a></li>" );
			}
			int showTag = 3;	//分页标签显示数量
			int startTag = 1;
			if(currentPage>showTag){
				startTag = currentPage-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(currentPage==i)
//					sb.append("<li class=\"current\"><a href=\"#\">"+i+"</a></li>\n");
        {
//          sb.append("<li class=\"\"><a href=\"#\" style=\"background:red;color:#27a4b0;\">"+i+"</a></li>\n");
			sb.append("<li ><a href=\"#\" class=\"myPage\" id=\"myPage\">"+i+"</a></li>\n");
        } else {
          sb.append("	<li><a href=\"#\" onclick=\"searchNextPage("+i+",this)\">"+i+"</a></li>\n");
        }
			}
			if(currentPage==totalPage){
//				sb.append("	<li class=\"pageinfo\">下页</li>\n");
				sb.append(" <li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li> ");
//				sb.append("	<li class=\"pageinfo\">尾页</li>\n");
			}else{
//				sb.append("	<li><a href=\"#@\" onclick=\"searchNextPage("+(currentPage+1)+",this)\">下页</a></li>\n");
//				sb.append("	<li><a href=\"#@\" onclick=\"searchNextPage("+totalPage+")\">尾页</a></li>\n");
				sb.append(" <li><a href=\"#\" aria-label=\"Next\" onclick=\"searchNextPage("+(currentPage+1)+",this)\"><span aria-hidden=\"true\">»</span></a></li> ");
			}
//			sb.append("	<li class=\"pageinfo\">第"+currentPage+"页</li>\n");
//			sb.append("	<li class=\"pageinfo\">共"+totalPage+"页</li>\n");
			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");
			sb.append("function nextPage(page){");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		document.forms[0].action = url+page;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		document.location = url + page;\n");
			sb.append("	}\n");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		pageStr = sb.toString();
		return pageStr;
	}
	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}
	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0) {
      currentResult = 0;
    }
		return currentResult;
	}
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	public boolean isEntityOrField() {
		return entityOrField;
	}
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

}
