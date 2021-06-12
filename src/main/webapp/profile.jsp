<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right;">
    <a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp">To menu</a><br/>
</div>
<div class="col-md-4" style="margin: 7px">
    <h3>Info:</h3>
    <h4>You logged as ${sessionScope.user.login}</h4>
    <label class="form-label"> Your account id: </label>
    <input class="form-control" type="text" placeholder="${sessionScope.user.id}"
           aria-label="Disabled input id" disabled readonly style="margin-top: 7px">
    <label class="form-label"> User Login: </label>
    <input class="form-control" type="text" placeholder="${sessionScope.user.login}"
           aria-label="Disabled input login" disabled readonly style="margin-top: 7px">
    <label class="form-label"> Your password: </label>
    <input class="form-control" type="text" placeholder="${sessionScope.user.password}"
           aria-label="Disabled input password" disabled readonly style="margin-top: 7px">
    <label class="form-label"> Your email (for commercial purpose): </label>
    <input class="form-control" type="text" placeholder="${sessionScope.user.email}"
           aria-label="Disabled input password" disabled readonly>
    <c:if test="${sessionScope.user.role == '1'}">
        <a class="btn btn-dark" href="controller?command=showuserchecks&page=1" style="margin-top: 7px" aria-disabled="true">All yours checks</a>
        <a class="btn btn-dark" href="controller?command=showuserrequest" style="margin-top: 7px">Show my last request</a>
    </c:if>
</div>
</body>
</html>
