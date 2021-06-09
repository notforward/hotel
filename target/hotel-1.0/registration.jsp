<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<a href="index.jsp">To Main Menu</a><br/>
<a href="authorization.jsp">Log in</a>
<div style="text-align: center">
    <h3>Welcome! Insert data for registration!</h3>
    <form method="get" action="controller">
        <input type="hidden" name="command" value="register"/>
        <input type="text" name="login" placeholder="Enter login"/><br/>
        <input type="password" name="password" placeholder="Enter password"><br/>
        <input type="text" name="email" placeholder="Enter email"/><br/>
        <input type="submit" name="submit" value="Register"/>
    </form>
</div>
</body>
</html>
