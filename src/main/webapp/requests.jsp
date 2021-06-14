<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags.tld" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark">To menu</a>
<h1><fmt:message key="requests.new"/></h1>
<hr>
<c:choose>
    <c:when test="${page - 1 > 0}">
        <a href="controller?command=showrequests&page=${page-1}"><fmt:message key="checks.previous"/></a>
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
            <a href="controller?command=showrequests&page=${p}">${p}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>
|
<c:choose>
    <c:when test="${page + 1 <= pages}">
        <a href="controller?command=showrequests&page=${page+1}"><fmt:message key="checks.next"/></a>
    </c:when>
    <c:otherwise>
        <fmt:message key="checks.next"/>
    </c:otherwise>
</c:choose>
|
<form action="controller" style='display:inline;'>
    <my:command command="showrequests"/>
    <select name="page">
        <c:forEach begin="1" end="${pages}" var="p">
            <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Go"/>
</form>

<hr>
<table>
    <div class="container">
        <div id="rooms">
            <div class="row">
                <c:forEach items="${requests}" var="req">
                    <div class="card col-12 col-sm-6 col-md-4 col-lg-3" style="margin: 10px">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="request_admin.id"/>: #${req.request_id}</h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><fmt:message key="request.user_id"/>: ${req.user_id}</li><br>
                                <li class="list-group-item"><fmt:message key="checks.status"/>: ${req.status}</li>
                            </ul>
                            <form action="controller" method="get">
                                <my:command command="showrequest"/>
                                <input type="hidden" name="request_id" value="${req.request_id}">
                                <input type="submit" value="<fmt:message key="request.learn_more"/>">
                            </form>
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
