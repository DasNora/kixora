<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account — KIXORA</title>
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
                <div class="col-md-6 col-lg-5">
                    <h2 class="mb-4 text-center">Create an Account</h2>

                    <%-- Error message --%>
                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %>
                        <div class="alert alert-danger"><%= error %></div>
                    <%
                        }
                    %>

                    <form action="register" method="post" class="card p-4 shadow-sm">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username *</label>
                            <input type="text" class="form-control" id="username"
                                   name="username" placeholder="Choose a username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password *</label>
                            <input type="password" class="form-control" id="password"
                                   name="password" placeholder="Choose a password" required>
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <label for="firstName" class="form-label">First Name</label>
                                <input type="text" class="form-control" id="firstName"
                                       name="firstName" placeholder="First name">
                            </div>
                            <div class="col">
                                <label for="lastName" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="lastName"
                                       name="lastName" placeholder="Last name">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address"
                                   name="address" placeholder="Street address">
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <label for="city" class="form-label">City</label>
                                <input type="text" class="form-control" id="city"
                                       name="city" placeholder="City">
                            </div>
                            <div class="col">
                                <label for="pinCode" class="form-label">Pin Code</label>
                                <input type="text" class="form-control" id="pinCode"
                                       name="pinCode" placeholder="Pin code">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-bloom w-100">Create Account</button>
                    </form>

                    <p class="text-center mt-3">
                        Already have an account? <a href="login">Log in</a>
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