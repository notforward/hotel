<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Your request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="auth.to_menu"/></a><br>
<H4><fmt:message key="user-request.your"/> </H4>
<div style="margin: 7px">
    <label class="form-label"><fmt:message key="request_admin.id"/>
        <input class="form-control" type="text" placeholder="${request.request_id}"
               aria-label="Disabled input price" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="request_admin.size"/>:
        <input class="form-control" type="text" placeholder="${request.size}"
               aria-label="Disabled input size" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="request_admin.class"/>
        <input class="form-control" type="text" placeholder="${request.room_class}"
               aria-label="Disabled input class" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="check.check_in"/>
        <input class="form-control" type="text" placeholder="${request.arrival}"
               aria-label="Disabled input arrival" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="check.check_out"/>
        <input class="form-control" type="text" placeholder="${request.department}"
               aria-label="Disabled input department" disabled readonly>
    </label><br>
</div>
<c:choose>
    <c:when test="${request.status == 'CREATED'}">
        <div class="alert alert-success col-md-4" role="alert">
            <fmt:message key="request.created_wait"/>
        </div>
    </c:when>
    <c:when test="${request.status == 'MANAGER_ACCEPTED'}">
        <div class="alert alert-success col-md-4" role="alert">
            <fmt:message key="request.manager_accepted"/><br>
                ${room.name}<br>
            <a class="btn btn-dark" href="controller?command=createcheck"><fmt:message key="book.book"/></a>
        </div>
    </c:when>
    <c:when test="${request.status == 'MANAGER_DECLINED'}">
        <div class="alert alert-success col-md-4" role="alert">
            <fmt:message key="request.manager_declined"/>
        </div>
    </c:when>
    <c:when test="${request.status == 'USER_ACCEPTED'}">
        <div class="alert alert-success col-md-4" role="alert">
            <fmt:message key="request.success"/>
        </div>
    </c:when>
</c:choose>
</body>
</html>
