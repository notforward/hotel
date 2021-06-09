<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Checks</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right;">
    <a class="btn btn-dark" href="index.jsp">To menu</a><br/>
</div>
<h4>Your checks:</h4>
<c:if test="${sessionScope.checks.size() == 0}">
    <h3>You have not checks yet</h3>
</c:if>
<c:forEach items="${checks}" var="check">
    <div style="margin: 15px">
        <h5>Check id: #${check.check_id}</h5>
        <h5>Dates: ${check.room_in} - ${check.room_out}</h5>
        <h5>Cost: ${check.price}$</h5>
        <h5>Check status: ${check.check_status}</h5>
        <a href="controller?command=showusercheck&id=${check.check_id}" class="btn btn-dark">
            Detailed information and payment</a>
    </div>
</c:forEach>
</body>
</html>