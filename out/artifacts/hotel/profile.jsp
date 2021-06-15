<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div id="text-align-right">
    <a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="auth.to_menu"/></a><br/>
</div>
<div class="col-md-4 margin">
    <h4><fmt:message key="index.logged_as"/> ${sessionScope.user.login}</h4>
    <label class="form-label"> <fmt:message key="profile.id"/>: </label>
    <input class="form-control margin" type="text" placeholder="${sessionScope.user.id}"
           aria-label="Disabled input id" disabled readonly>
    <label class="form-label"> <fmt:message key="profile.login"/>: </label>
    <input class="form-control margin" type="text" placeholder="${sessionScope.user.login}"
           aria-label="Disabled input login" disabled readonly>
    <label class="form-label"> <fmt:message key="profile.email"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.user.email}"
           aria-label="Disabled input password" disabled readonly>
    <c:if test="${sessionScope.user.role == '1'}">
        <a class="btn btn-dark margin" href="controller?command=showuserchecks&page=1" aria-disabled="true"><fmt:message key="profile.checks"/></a>
        <a class="btn btn-dark margin" href="controller?command=showuserrequest"><fmt:message key="profile.last_request"/></a>
    </c:if>
</div>
</body>
</html>
