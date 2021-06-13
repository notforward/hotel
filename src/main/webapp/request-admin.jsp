<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<html lang="${locale}"><head>
    <title>Request</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="controller?command=showrequests&page=1"><fmt:message key="request_admin.requests"/></a>
<div style="margin: 7px">
    <label class="form-label"><fmt:message key="request_admin.id"/>
        <input class="form-control" type="text" placeholder="${request.request_id}"
               aria-label="Disabled input price" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="request_admin.size"/>:
        <input class="form-control" type="text" placeholder="${request.size}"
               aria-label="Disabled input size" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="request_admin.class"/>:
        <input class="form-control" type="text" placeholder="${request.room_class}"
               aria-label="Disabled input class" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="check.check_in"/>:
        <input class="form-control" type="text" placeholder="${request.arrival}"
               aria-label="Disabled input arrival" disabled readonly>
    </label><br>
    <label class="form-label"><fmt:message key="check.check_out"/>
        <input class="form-control" type="text" placeholder="${request.department}"
               aria-label="Disabled input department" disabled readonly>
    </label><br>
</div>
<div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="checkdateadmin">
        <label class="form-label"><fmt:message key="request_admin.choose_room"/>:
            <select name="selected_room">
                <c:forEach items="${rooms}" var="room">
                    <option value="${room.id}">${room.name}</option>
                </c:forEach>
            </select>
        </label><br>
        <input type="submit" value="<fmt:message key="book.check_dates"/>" class="btn btn-dark">
    </form>
</div>
<div>
    <c:if test="${request.status == 'MANAGER_DECLINED'}">
        <div class="alert alert-success col-md-3" role="alert">
            <fmt:message key="request_admin.declined"/>
        </div>
    </c:if>
    <c:if test="${!available && available != null &&
    request.status != 'MANAGER_DECLINED'}">
        <div class="alert alert-danger col-md-3" role="alert">
            <fmt:message key="request_admin.not_available"/><br>
            <fmt:message key="request_admin.decline?"/><br>
            <a href="controller?command=declinerequest"><fmt:message key="request_admin.decline"/></a>
        </div>
    </c:if>
    <c:if test="${request.status == 'MANAGER_ACCEPTED'}">
        <div class="alert alert-success col-md-3" role="alert">
            <fmt:message key="request_admin.accepted"/>
            : ${available_room.name}
        </div>
    </c:if>
    <c:if test="${available && available != null &&
    request.status != 'MANAGER_DECLINED'}">
        <c:if test="${request.status != 'MANAGER_ACCEPTED'}">
            <div class="alert alert-success col-md-3" role="alert">
            <fmt:message key="request_admin.dates_free"/> :<br>
            #${available_room.name}#<br>
            <fmt:message key="request_admin.send_conformation"/><br>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="confirmrequest">
                <input type="submit" value="<fmt:message key="request_admin.send"/>!">
            </form>
        </c:if>
        </div>
    </c:if>
</div>
</body>
</html>
