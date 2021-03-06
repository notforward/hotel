<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags.tld" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark"><fmt:message key="auth.to_menu"/></a>
<div id="text-align-center">
    <h3><fmt:message key="request.fill_data"/></h3>
</div>
<div class="col-md-3 margin">
    <form action="controller" method="post">
        <my:command command="createrequest"/>
        <label for="size"><fmt:message key="request.choose_size"/></label>
        <select class="form-select margin" id="size" name="size" required>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
        </select>
        <label for="class"><fmt:message key="request.choose_class"/></label>
        <select class="form-select margin" id="class" name="class" required>
            <option value="ECONOM"><fmt:message key="request.econom"/></option>
            <option value="PREMIUM"><fmt:message key="request.luxury"/></option>
            <option value="LUXURY"><fmt:message key="request.premium"/></option>
        </select>
        <label for="arrival"> <fmt:message key="book.arrival"/>
            <input type="date" name="arrival" value="${arrival}"
                   id="arrival" max="2022-01-01" min="${today}" class="margin" required>
        </label><br>
        <label for="department"> <fmt:message key="book.department"/>
            <input type="date" name="department" value="${departure}"
                   id="department" max="2022-01-01" min="${today}" class="margin" required>
        </label><br>
        <input type="submit" class="btn btn-dark margin" value="<fmt:message key="request.create"/>">
    </form>
    <c:if test="${requestDone != null}">
        <div class="alert alert-success margin" role="alert">
            <fmt:message key="request.created"/>
        </div>
    </c:if>
</div>
</body>
</html>
