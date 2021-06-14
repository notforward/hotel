<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags.tld" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title>Rooms</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="auth.to_menu"/></a>
<h1><fmt:message key="checks.checks"/></h1>
<hr>
<c:choose>
    <c:when test="${page - 1 > 0}">
        <a href="controller?command=showuserchecks&page=${page-1}"><fmt:message key="checks.previous"/></a>
    </c:when>
    <c:otherwise>
        <fmt:message key="checks.previous"/>
    </c:otherwise>
</c:choose>
|
<c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
    <c:choose>
        <c:when test="${page == p}">${p}</c:when>
        <c:otherwise>
            <a href="controller?command=showuserchecks&page=${p}">${p}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>
|
<c:choose>
    <c:when test="${page + 1 <= pages}">
        <a href="controller?command=showuserchecks&page=${page+1}"><fmt:message key="checks.next"/></a>
    </c:when>
    <c:otherwise>
        <fmt:message key="checks.next"/>
    </c:otherwise>
</c:choose>
|
<form action="controller" style='display:inline;'>
    <my:command command="showusercheck"/>
    <select name="page">
        <c:forEach begin="1" end="${pages}" var="p">
            <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
        </c:forEach>
    </select>
    <input type="submit" value="<fmt:message key="checks.go"/>"/>
</form>

<hr>
<table>
    <div class="container">
        <div id="checks">
            <div class="row">
                <c:forEach var="check" items="${checks}">
                    <div class="card col-12 col-sm-6 col-md-4 col-lg-3" style="margin: 3rem">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="checks.check_id"/> #${check.check_id}</h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><fmt:message key="checks.check_info"/>:</li>
                                <li class="list-group-item"><fmt:message key="book.cost"/>: ${check.price}</li>
                                <li class="list-group-item"><fmt:message
                                        key="checks.status"/>: ${check.check_status}</li>
                                <li class="list-group-item"><fmt:message
                                        key="checks.creation_date"/>: ${check.check_creation}</li>
                            </ul>
                            <a href="controller?command=showusercheck&id=${check.check_id}" class="btn btn-dark">
                                <fmt:message key="checks.details"/></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</table>
<hr>
</body>
</html>
