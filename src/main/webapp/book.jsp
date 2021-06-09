<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a href="index.jsp" class="btn btn-dark">To menu</a>
</div>
<h1>Please, check information below and confirm purchase</h1><br>
<div class="col-md-5" style="margin: 5px">
    <label class="form-label">Apartment name: </label>
    <input class="form-control" type="text" placeholder="${room.name}"
           aria-label="Disabled input name" disabled readonly style="margin-top: 7px">
    <label class="form-label">Apartment cost: </label>
    <input class="form-control" type="text" placeholder="${room.price}$"
           aria-label="Disabled input price" disabled readonly style="margin-top: 7px">
    <label class="form-label">Payment method: </label>
    <input class="form-control" type="text" placeholder="Cash"
           aria-label="Disabled input price" disabled readonly style="margin-top: 7px">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="checkdate">
        <label for="arrival"> Select arrival date:
            <input type="date" name="arrival" value="${arrival}"
                   id="arrival" max="2022-01-01" min="${today}" style="margin-top: 7px">
        </label><br>
        <label for="department"> Select department date:
            <input type="date" name="department" value="${departure}"
                   id="department" max="2022-01-01" min="${today}" style="margin-top: 7px">
        </label><br>
        <c:if test="${!dates && dates != null}">
            <div class="alert alert-danger" role="alert">
                Sorry, this room is not available for selected dates! Try again
            </div>
        </c:if>
        <input type="submit" class="btn btn-dark" value="Check dates">
    </form>
    <c:if test="${sessionScope.dates}">
        <form action="controller" method="post" style="margin: 7px">
            <input type="hidden" name="command" value="createcheck">
            <div class="alert alert-success" role="alert">
                This dates available!
            </div>
            <input type="submit" class="btn btn-dark" value="Book" style="margin-top: 7px">
        </form>
    </c:if>
</div>
</body>
</html>
