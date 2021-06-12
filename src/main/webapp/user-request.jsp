<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Your request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp">To menu</a><br>
<H4>Your request: </H4>
<div style="margin: 7px">
    <label class="form-label">Request id:
        <input class="form-control" type="text" placeholder="${request.request_id}"
               aria-label="Disabled input price" disabled readonly>
    </label><br>
    <label class="form-label">Size of room:
        <input class="form-control" type="text" placeholder="${request.size}"
               aria-label="Disabled input size" disabled readonly>
    </label><br>
    <label class="form-label">Class :
        <input class="form-control" type="text" placeholder="${request.room_class}"
               aria-label="Disabled input class" disabled readonly>
    </label><br>
    <label class="form-label">Check-in date:
        <input class="form-control" type="text" placeholder="${request.arrival}"
               aria-label="Disabled input arrival" disabled readonly>
    </label><br>
    <label class="form-label">Check-out date:
        <input class="form-control" type="text" placeholder="${request.department}"
               aria-label="Disabled input department" disabled readonly>
    </label><br>
</div>
<c:choose>
    <c:when test="${request.status == 'CREATED'}">
        <div class="alert alert-success col-md-4" role="alert">
            You created request! Wait for response!
        </div>
    </c:when>
    <c:when test="${request.status == 'MANAGER_ACCEPTED'}">
        <div class="alert alert-success col-md-4" role="alert">
            Manager accepted your request! Room that you need:<br>
                ${room.name}<br>
            <a class="btn btn-dark" href="controller?command=createcheck">Book room!</a>
        </div>
    </c:when>
    <c:when test="${request.status == 'MANAGER_DECLINED'}">
        <div class="alert alert-success col-md-4" role="alert">
            Manager declined your request! Try to create another one!
        </div>
    </c:when>
    <c:when test="${request.status == 'USER_ACCEPTED'}">
        <div class="alert alert-success col-md-4" role="alert">
            You successfully accepted this room! We will wait you in our hotel!
        </div>
    </c:when>
</c:choose>

</body>
</html>
