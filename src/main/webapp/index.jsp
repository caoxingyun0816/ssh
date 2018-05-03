<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
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
