<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/28
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <div id="firstDiv" style="text-align: center">
        <form id="login" action="loginAction.htl" method="post">
            <h1 style="color:#33fff8">欢迎登陆学生管理系统</h1><br>
        姓名:<input type="text" name = "stuName"><s:fielderror fieldName="stuName.message"/><br>
        密码:<input type="password" name = "password"><s:fielderror fieldName="password.message"/><br>
        手机号：<input type="text" name="telphone">
        <input type="submit" value="登陆">
        </form>
    </div>
</body>
</html>
