<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a href="index.jsp" class="btn btn-dark">To menu</a>
<c:forEach items="${requests}" var="req">
    <div class="card col-12 col-sm-6 col-md-4 col-lg-3" style="margin: 10px">
        <div class="card-body">
            <h5 class="card-title">Request id: #${req.request_id}</h5>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">User id: ${req.user_id}</li>
            </ul>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="showrequest">
                <input type="hidden" name="request_id" value="${req.request_id}">
                <input type="submit" value="Learn more">
            </form>
        </div>
    </div>
</c:forEach>
</body>
</html>
