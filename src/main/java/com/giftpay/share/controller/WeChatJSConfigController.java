package com.giftpay.share.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.giftpay.share.base.BaseController;
import com.giftpay.share.model.JSONResultModel;
import com.giftpay.share.utils.AESUtil;
import com.giftpay.share.utils.WXShareUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/weChatJSConfigC", method = { RequestMethod.GET, RequestMethod.POST })
public class WeChatJSConfigController extends BaseController {

	
	@Value(value = "#{configProperties['appID']}")
	private String appID;

	@Value(value = "#{configProperties['appSecret']}")
	private String appSecret;

	@Value(value = "#{configProperties['key']}")
	private String key;
	
	private static final Logger _LOGGER = Logger.getLogger(WeChatJSConfigController.class);
	/**
	 * 微信分享
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWeChatJSConfig" , method = RequestMethod.POST )
	public String getWeCharJSConfig(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "currUrl" , required = true)String currUrl){
		JSONResultModel<String> jsonResultModel = new JSONResultModel<String>();
		String computeUrl = AESUtil.AESDncode(key,currUrl);
		try {
			WXShareUtil shareUtil = WXShareUtil.getInstance(appID, appSecret);
			String config = shareUtil.genJSSDKConf(computeUrl);
			jsonResultModel.setCode(1).setMsg("获取微信shareconfig成功！").setResultObject(config);
		} catch (Exception e) {
			_LOGGER.error(e.getMessage(), e);
			jsonResultModel.setCode(0).setMsg("获取微信shareconfig失败！");
		}
		String returnStr = net.sf.json.JSONSerializer.toJSON(jsonResultModel).toString();
		return returnStr;
	}
	
}
