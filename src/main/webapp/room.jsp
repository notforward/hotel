<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Room №${room.id}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="text-align: right">
    <a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp">To menu</a>
</div>
<div class="container">
    <div class="row">
        <div class="card" style="width: 30rem; display: inline-block">
            <h4>You chosen: </h4>
            <img src="${room.photo}" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">${room.name}</h5>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">Apartment cost: ${room.price}$</li>
            </ul>
        </div>
        <div class="col-md-6">
            <div style="display: inline-block" class="card">
                <div class="card-body" style="margin: 7px">
                    <h3>Additional information about apartment:</h3>
                    <label class="form-label"> Room size: </label>
                    <input class="form-control" type="text" placeholder="${room.size}"
                           disabled readonly
                           aria-label="Disabled input size">
                    <label class="form-label"> Room class: </label>
                    <input class="form-control" type="text" placeholder="${room.room_class}"
                           aria-label="Disabled input class"
                           disabled readonly>
                    <label class="form-label"> Room description: </label>
                    <input class="form-control" type="text" placeholder="${room.description}"
                           aria-label="Disabled input description"
                           disabled readonly>
                    <label class="form-label"> Room status: </label>
                    <c:if test="${sessionScope.user.role != '2'}">
                        <input class="form-control" type="text" placeholder="${room.status}"
                               aria-label="Disabled input status"
                               disabled readonly
                               value="${room.status}">
                    </c:if>
                    <c:if test="${sessionScope.user.role == '2'}">
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="editroom">
                            <select class="form-select" id="class" name="status" required>
                                <option selected value="" disabled>Choose one</option>
                                <option value="FREE">FREE</option>
                                <option value="BOOKED">BOOKED</option>
                                <option value="IN USE">IN USE</option>
                                <option value="UNAVAILABLE">UNAVAILABLE</option>
                            </select>
                            <input type="submit" value="Edit room info" class="btn-lg btn-dark" style="margin-top: 15px">
                        </form>
                    </c:if>
                    <c:choose>
                        <c:when test="${user == null}">
                            <a class="btn btn-dark" href="authorization.jsp" style="margin-top: 7px">Log In to book a
                                room</a>
                        </c:when>
                        <c:when test="${sessionScope.user.role == '1' && sessionScope.room.status != 'UNAVAILABLE'}">
                            <form action="controller" method="post" style="margin-top: 15px">
                                <input type="hidden" name="command" value="bookroom">
                                <input type="submit" class="btn-lg btn-dark" value="Book">
                            </form>
                        </c:when>
                    </c:choose>
                    <c:if test="${editRoomResult}">
                        <div class="alert alert-success" role="alert">
                            Status changed!
                            <c:remove var="editRoomResult"/>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
