<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sneakershop.model.User" %>
<%
    // Protect this page — redirect to login if no user in session
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard — Sneaker Shop</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header -->
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
                <a href="index.jsp" class="bloom-logo">SNEAKER<span>SHOP</span></a>
                <nav class="d-none d-md-flex align-items-center gap-3">
                    <a href="products" class="nav-link-custom">Browse Sneakers</a>
                    <a href="cart.jsp" class="nav-link-custom">Cart</a>
                </nav>
                <div class="d-flex align-items-center gap-2">
                    <span class="text-muted small"><%= user.getFirstName() %></span>
                    <a href="login" class="btn btn-sm btn-outline-danger">Log Out</a>
                </div>
            </div>
        </div>
    </header>

    <!-- Main -->
    <main class="flex-grow-1">
        <div class="container py-5">
            <h1 class="mb-3">Welcome, <%= user.getFirstName() %>! 👋</h1>
            <p class="text-muted">You are logged in as: <strong><%= user.getUsername() %></strong></p>

            <div class="row g-4 mt-3">
                <div class="col-md-4">
                    <div class="card p-4 text-center shadow-sm">
                        <h5 class="mb-3">👟 Browse Sneakers</h5>
                        <p class="text-muted small">View all 10 sneakers in our catalog</p>
                        <a href="<%= response.encodeURL("products") %>" class="btn btn-bloom">Shop Now</a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card p-4 text-center shadow-sm">
                        <h5 class="mb-3">🛒 Your Cart</h5>
                        <p class="text-muted small">Review items ready for checkout</p>
                        <a href="cart.jsp" class="btn btn-outline-secondary">View Cart</a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card p-4 text-center shadow-sm">
                        <h5 class="mb-3">📞 Contact</h5>
                        <p class="text-muted small">Need help? Reach out to us</p>
                        <a href="contact.jsp" class="btn btn-outline-secondary">Contact Us</a>
                    </div>
                </div>
            </div>

            <%-- Session Info Box --%>
            <div class="session-info-box mt-5 p-3 border rounded bg-light">
                <h6>🔍 Session Information</h6>
                <p class="mb-1 small">Session ID: <code><%= session.getId() %></code></p>
                <p class="mb-0 small">Role: <strong><%= user.getRole() %></strong> | Cookie: SNEAKER_USER_SESSION</p>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
            <small>&copy; 2026 Sneaker Shop</small>
        </div>
    </footer>

    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>