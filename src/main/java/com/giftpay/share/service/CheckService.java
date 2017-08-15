package com.giftpay.share.service;

import com.giftpay.share.model.JSONResultModel;
import com.giftpay.share.utils.AESUtil;
import com.giftpay.share.utils.MD5Util;
import com.sun.javafx.collections.MappingChange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 19:04 2017/8/14
 * @Modified By:
 */
@Service("CheckService")
public class CheckService {

    @Value(value = "#{configProperties['key']}")
    private String key;

    private static final Logger _LOGGER = Logger.getLogger(CheckService.class);

    public JSONResultModel checkWeChatJSConfig(HttpServletRequest request, HttpServletResponse response){
        JSONResultModel jsonResultModel = new JSONResultModel();
        String timeStamp = request.getParameter("timeStamp");
        String currUrl = request.getParameter("currUrl");
        String sign = request.getParameter("sign");

        if(StringUtils.isEmpty(timeStamp)||StringUtils.isEmpty(currUrl)||StringUtils.isEmpty(sign)){
            _LOGGER.error("有参数为空");
            jsonResultModel.setCode(1002).setMsg("有参数为空");
            return jsonResultModel;
        }
        _LOGGER.info("timeStamp为"+timeStamp);
        _LOGGER.info("currUrl为"+currUrl);
        _LOGGER.info("sign为"+sign);

        long computeTimeStamp = Long.parseLong(AESUtil.AESDncode(key,timeStamp));
        Long nowTime = System.currentTimeMillis();
        Long s = (nowTime - computeTimeStamp) / (1000 * 60);
        if(s>=5){
            _LOGGER.error("时间戳时间距离当前时间太久");
            jsonResultModel.setCode(1003).setMsg("时间戳时间距离当前时间太久");
            return jsonResultModel;
        }

        Map<String,String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp",timeStamp);
        sortedMap.put("currUrl",currUrl);
        sortedMap.put("sign",sign);

        StringBuffer stringBuffer = new StringBuffer();
        for(String key:sortedMap.keySet()){
            if(!"sign".equals(key)){
                stringBuffer.append("&"+key+"="+sortedMap.get(key));
            }
        }
        stringBuffer.append("&key="+key);
        stringBuffer.deleteCharAt(0);
        String computeSign = MD5Util.getMD5(stringBuffer.toString());
        if(computeSign.equals(sign)){
            _LOGGER.info("参数匹配正确，拦截器跳转控制层");
            jsonResultModel.setCode(0);
            return jsonResultModel;
        }else{
            _LOGGER.error("sign校验错误");
            jsonResultModel.setCode(1004).setMsg("sign校验错误");
            return jsonResultModel;
        }
    }
}
