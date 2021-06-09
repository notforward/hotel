<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>ERROR</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<a class="btn btn-dark" href="index.jsp">To menu</a>
<div style="text-align: center">
    <h1>ERROR!</h1>
    <h1>${sessionScope.error.message}</h1>
</div>
</body>
</html>
