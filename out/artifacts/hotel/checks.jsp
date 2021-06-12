<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Rooms</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="${pageContext.request.contextPath}/index.jsp">To menu</a>
<h1>Your checks:</h1>
<h4>Page: ${requestScope.page} of ${requestScope.pages}</h4>
<hr>
<c:choose>
    <c:when test="${page - 1 > 0}">
        <a href="controller?command=showuserchecks&page=${page-1}">Previous</a>
    </c:when>
    <c:otherwise>
        Previous
    </c:otherwise>
</c:choose>


<c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
    <c:choose>
        <c:when test="${page == p}">${p}</c:when>
        <c:otherwise>
            <a href="controller?command=showuserchecks&page=${p}">${p}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>

<c:choose>
    <c:when test="${page + 1 <= pages}">
        <a href="controller?command=showuserchecks&page=${page+1}">Next</a>
    </c:when>
    <c:otherwise>
        Next
    </c:otherwise>
</c:choose>

|

<form action="controller" style='display:inline;'>
    <input name="command" value="showuserchecks" type="hidden"/>
    <select name="page">
        <c:forEach begin="1" end="${pages}" var="p">
            <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Go"/>
</form>

<hr>
<table>
    <div class="container">
        <div id="checks">
            <div class="row">
                <c:forEach var="check" items="${checks}">
                    <div class="card col-12 col-sm-6 col-md-4 col-lg-3" style="margin: 3rem">
                        <div class="card-body">
                            <h5 class="card-title">Check id: #${check.check_id}</h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Check information:</li>
                                <li class="list-group-item">Price: ${check.price}</li>
                                <li class="list-group-item">Status: ${check.check_status}</li>
                                <li class="list-group-item">Creation date: ${check.check_creation}</li>
                            </ul>
                            <a href="controller?command=showusercheck&id=${check.check_id}" class="btn btn-dark">
                                Detailed information and payment</a>
                        </div>

                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</table>
<hr>
</body>
</html>
