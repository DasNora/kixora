<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log In — KIXORA</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header -->
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
                <a href="index.jsp" class="bloom-logo">KIX<span>ORA</span></a>
            </div>
        </div>
    </header>

    <!-- Main -->
    <main class="flex-grow-1 d-flex align-items-center">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-5 col-lg-4">
                    <h2 class="mb-4 text-center">Log In</h2>

                    <%-- Success message from registration --%>
                    <%
                        String success = (String) request.getAttribute("success");
                        if (success != null) {
                    %>
                        <div class="alert alert-success"><%= success %></div>
                    <%
                        }
                    %>

                    <%-- Error message from failed login --%>
                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %>
                        <div class="alert alert-danger"><%= error %></div>
                    <%
                        }
                    %>

                    <form action="login" method="post" class="card p-4 shadow-sm">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username"
                                   name="username" placeholder="Enter username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password"
                                   name="password" placeholder="Enter password" required>
                        </div>
                        <button type="submit" class="btn btn-bloom w-100">Log In</button>
                    </form>

                    <p class="text-center mt-3">
                        Don't have an account? <a href="register">Create one</a>
                    </p>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
            <small>&copy; 2026 KIXORA</small>
        </div>
    </footer>

    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>