package com.giftpay.share.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 17:49 2017/8/14
 * @Modified By:
 */
public class JSONResultModel<T> implements Serializable {
    private static final long serialVersionUID = -792476931794138965L;
    private Integer code;
    private String msg;
    private List<T> resultList;
    private T resultObject;

    public JSONResultModel() {
    }

    public List<T> getResultList() {
        return this.resultList;
    }

    public JSONResultModel<T> setResultList(List<T> resultList) {
        this.resultList = resultList;
        return this;
    }

    public T getResultObject() {
        return this.resultObject;
    }

    public JSONResultModel<T> setResultObject(T resultObject) {
        this.resultObject = resultObject;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public JSONResultModel<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public JSONResultModel<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public static long getSerialversionuid() {
        return -792476931794138965L;
    }
}
