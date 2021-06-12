<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-dark">To menu</a>
<div style="text-align: center">
    <h3>Please, fill data</h3>
</div>
<div class="col-md-3" style="margin: 7px">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="createrequest">
        <label for="size">Choose size of a room</label>
        <select class="form-select" id="size" name="size" required>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
        </select>
        <label for="class">Select class of a room</label>
        <select class="form-select" id="class" name="class" required>
            <option value="ECONOM">Econom class</option>
            <option value="PREMIUM">Premium</option>
            <option value="LUXURY">Luxury</option>
        </select>
        <label for="arrival"> Select arrival date:
            <input type="date" name="arrival" value="${arrival}"
                   id="arrival" max="2022-01-01" min="${today}" style="margin-top: 7px" required>
        </label><br>
        <label for="department"> Select department date:
            <input type="date" name="department" value="${departure}"
                   id="department" max="2022-01-01" min="${today}" style="margin-top: 7px" required>
        </label><br>
        <input type="submit" class="btn btn-dark" value="Create request">
    </form>
    <c:if test="${requestDone != null}">
        <div class="alert alert-success" role="alert">
            Request created!
        </div>
    </c:if>
</div>
</body>
</html>
