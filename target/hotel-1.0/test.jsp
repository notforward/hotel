<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Authorisation</title>
</head>
<body>
<c:if test="${sessionScope.user.get() != null}">
    <h1>YOUR NAME IS ${sessionScope.user.get().login}</h1>
</c:if>
<h1>Hello, ${sessionScope.user.get().login}!</h1>
</body>
</html>
