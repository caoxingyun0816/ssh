package com.cxy.action;

import com.cxy.entity.Student;
import com.cxy.service.LoginService;
import com.opensymphony.xwork2.ModelDriven;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by cxy on 2018/4/28.
 */
public class LoginAction extends BaseAction implements ModelDriven<Student>{

    private Student student = new Student();

    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String login(){
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        String userName = request.getParameter("stuName");
        String password = request.getParameter("password");
        //System.out.println("login");
        //String result = loginService.login(userName,password);
        //resultMap.put("result",result);
        System.out.println(userName);
        System.out.println(password);
        HttpSession session = request.getSession();
        session.setAttribute("result","123456");
        return "login";
    }

    @Override
    public Student getModel(){
        return student;
    }
}
