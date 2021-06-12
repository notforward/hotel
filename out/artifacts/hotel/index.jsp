<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main Menu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div id="index">
        <h1>Welcome to Artem's Hotel!</h1><br/>
        <div style="text-align: right">
            <c:choose>
                <c:when test="${user.isPresent()}">
                    <h5>You logged as : ${user.get().login}</h5>
                    <a class="btn btn-dark" href="profile.jsp">To user profile</a><br>
                    <a class="btn btn-dark" href="controller?command=logout" style="margin-top: 7px">Log out</a>
                </c:when>
                <c:when test="${!user.isPresent()}">
                    <a class="btn btn-dark" href="authorization.jsp">Log In</a><br/>
                    <a class="btn btn-dark" href="registration.jsp" style="margin-top: 7px">Registration</a>
                </c:when>
            </c:choose>
        </div>
        <div style="text-align: center">
            <c:choose>
                <c:when test="${sessionScope.user.get().role == '1'}">
                    <a class="btn btn-dark" href="request.jsp" style="margin-top: 7px">Create request</a><br>
                </c:when>
                <c:when test="${sessionScope.user.get().role == '2'}">
                    <a class="btn btn-dark" href="controller?command=showrequests" style="margin-top: 7px">Requests</a><br>
                </c:when>
            </c:choose>
            <a class="btn btn-dark" href="controller?command=showrooms" style="margin-top: 7px">Rooms</a><br>
            <a class="btn btn-dark" href="features.jsp" style="margin-top: 7px">Features</a><br>
            <a class="btn btn-dark" href="info.jsp" style="margin-top: 7px">About us</a>
        </div>
    </div>
</div>
</body>
</html>