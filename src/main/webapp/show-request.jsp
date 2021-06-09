<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Show request #${request.request_id}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div class="col-md-4" style="margin: 7px">
    <h4>Please, help user choose right room</h4>
    <div>
        <label class="form-label">Request id: </label>
        <input class="form-control" type="text" placeholder="#${request.request_id}"
               aria-label="Disabled input name" disabled readonly><br>
        <label class="form-label">Check-in date: </label>
        <input class="form-control" type="text" placeholder="${sessionScope.request.arrival}"
               aria-label="Disabled input price" disabled readonly><br>
        <label class="form-label">Check-out date: </label>
        <input class="form-control" type="text" placeholder="${sessionScope.request.department}"
               aria-label="Disabled input price" disabled readonly><br>
        <label class="form-label">Preferred class: </label>
        <input class="form-control" type="text" placeholder="${sessionScope.request.room_class}"
               aria-label="Disabled input price" disabled readonly><br>
        <label class="form-label">Preferred size: </label>
        <input class="form-control" type="text" placeholder="${sessionScope.request.size}"
               aria-label="Disabled input price" disabled readonly><br>
    </div>
    <c:if test="${sessionScope.request.status != 'MANAGER_DECLINED'}">
        <form action="controller" method="post">
            <label for="room">Select room: </label>
            <select class="form-select" id="room" name="room" required>
                <c:forEach items="${rooms}" var="room">
                    <option value="${room}">${room.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="command" value="checkdateadmin" style="margin-top: 7px">
            <input type="submit" value="Check dates" class="btn-lg btn-dark" style="margin-top: 7px">
        </form>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="declinerequest" style="margin-top: 7px">
            <input type="submit" value="Decline request" class="btn-lg btn-dark" style="margin-top: 7px">
        </form>
    </c:if>
    <c:if test="${sessionScope.request.status == 'MANAGER_DECLINED'}">
        <div class="alert alert-success" role="alert">
            Declined successfully!
        </div>
    </c:if>
</div>
</body>
</html>
