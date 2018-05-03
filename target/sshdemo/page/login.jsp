<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/28
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <div id="firstDiv" style="text-align: center">
        <form id="login" action="loginAction.htl" method="post">
            <h5 style="background: red">欢迎登陆学生管理系统</h5><br>
        姓名:<input type="text" name = "stuName"><br>
        密码:<input type="password" name = "password"><br>
        <input type="submit" value="登陆">
        </form>
    </div>
</body>
</html>
