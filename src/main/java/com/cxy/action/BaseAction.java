package com.cxy.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxy on 2018/5/3.
 */
public class BaseAction extends ActionSupport {

    public Map<String,Object> resultMap = new HashMap<String,Object>();

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public HttpServletResponse getResponse(){
        return ServletActionContext.getResponse();
    }

    public HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }


}
