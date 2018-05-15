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
    private String stuName;
    private String telphone;

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

//    @Override
//    public void validate(){
//
//        if(student.getStuName() == null){
//            this.addFieldError("stuName.message","学生姓名不能为空");
//        }
//        System.out.println(student.getStuName());
//        System.out.println(student.getPassWord());
//        if(student.getPassWord() == null){
//          this.addFieldError("password.message","密码不能为空");
//        }
//    }

    @Override
    public Student getModel(){
        return student;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getTelphone() {
        return telphone;
    }
}
