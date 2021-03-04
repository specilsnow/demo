/**
 * 
 */
package com.cdutcm.core.util;

/**
 * @author Mxq
 *
 * @date 2018年4月12日
 * 
 * 返回前端结果输出工具
 */
public class ResultVOUtil {

	/**
	 * 获取数据成功，返回code 0
	 * @param object
	 * @return
	 */
	 public static ResultVO success(Object object) {
	        ResultVO resultVO = new ResultVO();
	        resultVO.setData(object);
	        resultVO.setCode(0);
	        resultVO.setMsg("成功");
	        return resultVO;
	    }

	    public static ResultVO success() {
	        return success(null);
	    }
		/**
		 * 获取数据失败  返回code 非零随意设置
		 * @param code
		 * @param msg
		 * @return
		 */
	    public static ResultVO error(Integer code, String msg) {
	        ResultVO resultVO = new ResultVO();
	        resultVO.setCode(code);
	        resultVO.setMsg(msg);
	        return resultVO;
	    }

}
