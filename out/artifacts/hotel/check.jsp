<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Check</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark"><fmt:message key="auth.to_menu"/></a>
</div>
<div class="col-md-4" style="margin: 5px">
    <h4><fmt:message key="checks.check_info"/></h4>
    <label class="form-label"><fmt:message key="book.apart_name"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.room.name}"
           aria-label="Disabled input name" disabled readonly style="margin: 7px">
    <label class="form-label"><fmt:message key="check.check_in"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.check.room_in}"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label"><fmt:message key="check.check_out"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.check.room_out}"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label"><fmt:message key="book.payment"/> </label>
    <input class="form-control" type="text" placeholder="Cash / card"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label"><fmt:message key="book.cost"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.check.price}$"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
</div>
<c:choose>
    <c:when test="${sessionScope.check.check_status == 'PAYED'}">
        <div class="alert alert-success col-md-4" role="alert" style="margin: 7px">
            <fmt:message key="check.all_done"/>
        </div>
    </c:when>
    <c:when test="${sessionScope.check.check_status == 'NOT PAYED'}">
        <a class="btn btn-dark" href="controller?command=paycheck" style="margin: 7px"><fmt:message key="check.pay"/></a>
    </c:when>
    <c:when test="${sessionScope.check.check_status == 'TERMINATED'}">
        <div class="alert alert-danger col-md-4" role="alert" style="margin: 7px">
            <fmt:message key="check.terminated"/>
        </div>
    </c:when>
</c:choose>
</body>
</html>
