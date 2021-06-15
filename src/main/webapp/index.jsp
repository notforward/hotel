<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <title>Main menu</title>
    <link href="styles.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a class="btn btn-dark" href="controller?command=selectlocale&locale=en">EN (english)</a>
    <a class="btn btn-dark" href="controller?command=selectlocale&locale=ru">RU (русский)</a>
</div>
<div class="container">
    <div id="index">
        <h1><fmt:message key="index.greetings"/></h1><br/>
        <div id="text-align-right">
            <c:choose>
                <c:when test="${user != null}">
                    <h5><fmt:message key="index.logged_as"/> : ${user.login}</h5>
                    <a class="btn btn-dark" href="controller?command=showprofile"><fmt:message
                            key="index.to_profile"/></a><br>
                    <a class="btn btn-dark" href="controller?command=logout" id="margin-top"><fmt:message
                            key="index.log_out"/></a>
                </c:when>
                <c:when test="${user == null}">
                    <a class="btn btn-dark" href="authorization.jsp"><fmt:message key="index.log_in"/></a><br/>
                    <a class="btn btn-dark" href="registration.jsp" id="margin-top"><fmt:message
                            key="index.registration"/></a>
                </c:when>
            </c:choose>
        </div>
        <div id="text-align-center">
            <c:choose>
                <c:when test="${sessionScope.user.role == '1'}">
                    <a class="btn btn-dark" href="request.jsp" id="margin-top"><fmt:message
                            key="index.create_request"/></a><br>
                </c:when>
                <c:when test="${sessionScope.user.role == '2'}">
                    <a class="btn btn-dark" href="controller?command=showrequests&page=1"
                       id="margin-top"><fmt:message key="index.requests"/></a><br>
                </c:when>
            </c:choose>
            <a class="btn btn-dark margin" href="controller?command=showrooms&page=1&orderBy=price"
               id="margin-top"><fmt:message key="index.rooms"/></a><br>
            <a class="btn btn-dark margin" href="info.jsp"><fmt:message key="index.about_us"/></a>
        </div>
    </div>
</div>
</body>
</html>