<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Book</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark"><fmt:message key="auth.to_menu"/></a>
</div>
<h1><fmt:message key="book.purchase"/></h1><br>
<div class="col-md-5" style="margin: 5px">
    <label class="form-label"><fmt:message key="book.apart_name"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.room.name}"
           aria-label="Disabled input name" disabled readonly style="margin-top: 7px">
    <label class="form-label"><fmt:message key="book.cost"/> </label>
    <input class="form-control" type="text" placeholder="${sessionScope.room.price}$"
           aria-label="Disabled input price" disabled readonly style="margin-top: 7px">
    <label class="form-label"><fmt:message key="book.payment"/> </label>
    <input class="form-control" type="text" placeholder="Cash"
           aria-label="Disabled input price" disabled readonly style="margin-top: 7px">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="checkdate">
        <label for="arrival"> <fmt:message key="book.arrival"/>
            <input type="date" name="arrival" value="${sessionScope.arrival}"
                   id="arrival" max="2022-01-01" min="${sessionScope.today}" style="margin-top: 7px">
        </label><br>
        <label for="department"> <fmt:message key="book.department"/>
            <input type="date" name="department" value="${sessionScope.departure}"
                   id="department" max="2022-01-01" min="${sessionScope.today}" style="margin-top: 7px">
        </label><br>
        <c:if test="${!sessionScope.dates && sessionScope.dates != null}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="book.not_available"/>
            </div>
        </c:if>
        <input type="submit" class="btn btn-dark" value="<fmt:message key="book.check_dates"/>">
    </form>
    <c:if test="${sessionScope.dates}">
        <form action="controller" method="post" style="margin: 7px">
            <input type="hidden" name="command" value="createcheck">
            <div class="alert alert-success" role="alert">
                <fmt:message key="book.available"/>
            </div>
            <input type="submit" class="btn btn-dark" value="<fmt:message key="book.book"/>" style="margin-top: 7px">
        </form>
    </c:if>
</div>
</body>
</html>
