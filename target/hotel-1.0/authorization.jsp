<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
    <a href="index.jsp">To Main Menu</a><br/>
    <a href="registration.jsp">Registration</a>
<div style="text-align: center">
    <h3>Welcome! Insert data to log in!</h3>
    <form method="get" action="controller">
        <input type="hidden" name="command" value="login"/>
        <input type="text" name="login" placeholder="Enter login"/><br/>
        <input type="password" name="password" placeholder="Enter password"><br/>
        <input type="submit" name="submit" value="Log in"/>
    </form>
</div>
</body>
</html>
