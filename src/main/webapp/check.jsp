<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Check</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark">To menu</a>
</div>
<div class="col-md-4" style="margin: 5px">
    <h3>Your check:</h3>
    <label class="form-label">Apartment name: </label>
    <input class="form-control" type="text" placeholder="${room.name}"
           aria-label="Disabled input name" disabled readonly style="margin: 7px">
    <label class="form-label">Check-in date: </label>
    <input class="form-control" type="text" placeholder="${sessionScope.check.room_in}"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label">Check-out date: </label>
    <input class="form-control" type="text" placeholder="${check.room_out}"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label">Payment method: </label>
    <input class="form-control" type="text" placeholder="Cash / card"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
    <label class="form-label">Cost: </label>
    <input class="form-control" type="text" placeholder="${check.price}$"
           aria-label="Disabled input price" disabled readonly style="margin: 7px">
</div>
<c:choose>
    <c:when test="${check.check_status == 'PAYED'}">
        <div class="alert alert-success col-md-4" role="alert" style="margin: 7px">
            Congrats! All done!
        </div>
    </c:when>
    <c:when test="${check.check_status == 'NOT PAYED'}">
        <a class="btn btn-dark" href="controller?command=paycheck" style="margin: 7px">Pay!</a>
    </c:when>
    <c:when test="${check.check_status == 'TERMINATED'}">
        <div class="alert alert-danger col-md-4" role="alert" style="margin: 7px">
            Sorry, these check terminated due time.
        </div>
    </c:when>
</c:choose>
</body>
</html>
