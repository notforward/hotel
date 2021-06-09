<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<c:if test="${sessionScope.requests.size() == 0}">
    <h4>There's no requests! Great job!</h4>
</c:if>
<c:if test="${sessionScope.requests.size() != 0 }">
    <c:forEach items="${requests}" var="request">
        <div class="card" style="width: 15rem; display: inline-block">
            <h4 class="card-title">Request id: #${request.request_id}</h4>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <a href="controller?command=showrequest?id=${request.request_id}">Review request</a></li>
            </ul>
        </div>
    </c:forEach>
</c:if>
</body>
</html>
