<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="controller?command=showrequests&page=1">To requests</a>
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
<div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="checkdateadmin">
        <label class="form-label">Choose room:
            <select name="selected_room">
                <c:forEach items="${rooms}" var="room">
                    <option value="${room.id}">${room.name}</option>
                </c:forEach>
            </select>
        </label><br>
        <input type="submit" value="Check dates" class="btn btn-dark">
    </form>
</div>
<div>
    <c:if test="${request.status == 'MANAGER_DECLINED'}">
        <div class="alert alert-success col-md-3" role="alert">
            Declined request!
        </div>
    </c:if>
    <c:if test="${!available && available != null &&
    request.status != 'MANAGER_DECLINED'}">
        <div class="alert alert-danger col-md-3" role="alert">
            Dates not available! Try another room!<br>
            Decline request?<br>
            <a href="controller?command=declinerequest">Decline!</a>
        </div>
    </c:if>
    <c:if test="${request.status == 'MANAGER_ACCEPTED'}">
        <div class="alert alert-success col-md-3" role="alert">
            Request accepted and sent! Well done!
            Room: ${available_room.name}
        </div>
    </c:if>
    <c:if test="${available && available != null &&
    request.status != 'MANAGER_DECLINED'}">
        <c:if test="${request.status != 'MANAGER_ACCEPTED'}">
            <div class="alert alert-success col-md-3" role="alert">
            Success! This dates are free for :<br>
            #${available_room.name}#<br>
            Send conformation to user?<br>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="confirmrequest">
                <input type="submit" value="Send!">
            </form>
        </c:if>
        </div>
    </c:if>


</div>
</body>
</html>
