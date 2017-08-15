package com.giftpay.share.base;

import javax.servlet.http.HttpServletRequest;


public abstract class BaseController {

	private final String CURRENT_USER="currUser";

	/**
	 * 获取变量
	 * @param name
	 * @return
	 */
	protected String getStr(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
	/**
	 * 获取变量
	 * @param name
	 * @return
	 */
	protected Integer getInt(HttpServletRequest request,String name){
		String value=getStr(request,name);
		if(value==null){
			return null;
		}else{
			return Integer.parseInt(value);
		}
	}
	/**
	 * 获取变量
	 * @param name
	 * @return
	 */
	protected Float getFloat(HttpServletRequest request,String name){
		String value=getStr(request,name);
		if(value==null){
			return null;
		}else{
			return Float.parseFloat(value);
		}
	}


	protected String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		return basePath;
	}
}
