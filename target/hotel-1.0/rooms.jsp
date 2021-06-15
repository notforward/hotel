<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tags.tld" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}">
<head>
    <title>Rooms</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="auth.to_menu"/></a>
<div style="margin: 7px">
    <a class="btn-sm btn-dark" href="controller?command=sortby&orderBy=price"><fmt:message key="rooms.order_price"/></a>
    <a class="btn-sm btn-dark" href="controller?command=sortby&orderBy=size"><fmt:message key="rooms.order_size"/></a>
    <a class="btn-sm btn-dark" href="controller?command=sortby&orderBy=class"><fmt:message key="rooms.order_class"/></a>
    <a class="btn-sm btn-dark" href="controller?command=sortby&orderBy=status"><fmt:message key="rooms.order_status"/></a>
</div>
<c:choose>
    <c:when test="${page - 1 > 0}">
        <a href="controller?command=showrooms&page=${page-1}"><fmt:message key="checks.previous"/></a>
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
            <a href="controller?command=showrooms&page=${p}">${p}?</a>
        </c:otherwise>
    </c:choose>
</c:forEach>

|

<c:choose>
    <c:when test="${page + 1 <= pages}">
        <a href="controller?command=showrooms&page=${page+1}"><fmt:message key="checks.next"/></a>
    </c:when>
    <c:otherwise>
        <fmt:message key="checks.next"/>
    </c:otherwise>
</c:choose>

|

<form action="controller" style='display:inline;'>
    <my:command command="showrooms"/>
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
        <div id="rooms">
            <div class="row">
                <c:forEach var="room" items="${rooms}">
                    <div class="card col-12 col-sm-6 col-md-4 col-lg-3" style="margin: 3rem">
                        <img src="${room.photo}" class="card-img-top" alt="room_photo">
                        <div class="card-body">
                            <h5 class="card-title">${room.name}</h5>
                            <p class="card-text">${room.description}</p>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><fmt:message key="rooms.info"/></li>
                                <li class="list-group-item"><fmt:message
                                        key="request_admin.size"/>: ${room.size}</li>
                                <li class="list-group-item"><fmt:message key="checks.status"/>: ${room.status}</li>
                                <li class="list-group-item"><fmt:message key="book.cost"/>: ${room.price}$</li>
                                <li class="list-group-item"><fmt:message
                                        key="request_admin.class"/>: ${room.room_class}</li>
                            </ul>
                            <a href="controller?command=showroom&id=${room.id}"><fmt:message
                                    key="request.learn_more"/></a><br>
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
