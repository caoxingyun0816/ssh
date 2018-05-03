package com.cxy.service;

import com.cxy.dao.LoginDao;

/**
 * Created by Administrator on 2018/5/3.
 */
public class LoginService {

    private LoginDao loginDao;

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public String login(String username, String password){
        String result = loginDao.login(username,password);
        return result;
    }
}
