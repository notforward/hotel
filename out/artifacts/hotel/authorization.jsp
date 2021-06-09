<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Authorization</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
</head>
<body>
<div style="margin: 7px">
    <a type="button" class="btn btn-outline-dark" href="index.jsp" style="margin: 7px">To menu</a><br>
    <a type="button" class="btn btn-outline-dark" href="registration.jsp" style="margin: 7px">To registration</a><br>
</div>
<div class="container">
    <div id="auth" class="col-md-4 ">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="login"/>
            <div class="mb-3">
                <label for="InputLogin" class="form-label">User login</label>
                <input name="login" type="text" class="form-control" id="InputLogin" aria-describedby="emailHelp" required>
            </div>
            <div class="mb-3">
                <label for="InputPassword" class="form-label">Password</label>
                <input name="password" type="password" class="form-control" id="InputPassword" required>
            </div>
            <div class="mb-3">
                <input type="submit" class="btn btn-outline-dark" value="Log In"/>
            </div>
        </form>
    </div>
</div>
</body>
</html>
