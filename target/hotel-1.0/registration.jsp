<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags.tld" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a type="button" class="btn btn-outline-dark" href="index.jsp" style="margin: 7px"><fmt:message key="auth.to_menu"/></a><br>
<a type="button" class="btn btn-outline-dark" href="authorization.jsp" style="margin: 7px"><fmt:message key="index.log_in"/></a>
<div class="container">
    <div id="register" class="col-md-4 ">
        <form action="controller" method="post">
            <my:command command="register"/>
            <div class="mb-3">
                <label for="InputLogin" class="form-label"><fmt:message key="profile.login"/></label>
                <input name="login" type="text" class="form-control" id="InputLogin" aria-describedby="emailHelp" required>
            </div>
            <div class="mb-3">
                <label for="InputEmail" class="form-label"><fmt:message key="registration.email"/></label>
                <input name="email" type="email" class="form-control" id="InputEmail" aria-describedby="emailHelp" required>
                <div id="emailHelp" class="form-text"><fmt:message key="registration.email_description"/></div>
            </div>
            <div class="mb-3">
                <label for="InputPassword" class="form-label"><fmt:message key="profile.password"/></label>
                <input name="password" type="password" class="form-control" id="InputPassword" required>
            </div>
            <div class="mb-3">
                <input type="submit" class="btn btn-outline-dark" value="<fmt:message key="registration.register"/>"/>
            </div>
        </form>
    </div>
</div>
</body>
</html>
