<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Sneaker — KIXORA</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header -->
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
                <a href="index.jsp" class="bloom-logo">KIX<span>ORA</span></a>
                <nav class="d-none d-md-flex gap-2">
                    <a href="AdminDashboard.jsp" class="nav-link-custom">← Back to Dashboard</a>
                </nav>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="flex-grow-1">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <h2 class="mb-4">➕ Add New Sneaker</h2>

                    <%-- Success message --%>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <%-- Error message --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="addProduct" method="post" class="card p-4 shadow-sm">
                        <div class="mb-3">
                            <label for="name" class="form-label">Sneaker Name *</label>
                            <input type="text" class="form-control" id="name"
                                   name="name" placeholder="e.g., AirFlex Runner" required>
                        </div>
                        <div class="mb-3">
                            <label for="category" class="form-label">Category *</label>
                            <select class="form-select" id="category" name="category" required>
                                <option value="">-- Choose Category --</option>
                                <option value="Running">Running</option>
                                <option value="Lifestyle">Lifestyle</option>
                                <option value="Retro">Retro</option>
                                <option value="Performance">Performance</option>
                                <option value="Premium">Premium</option>
                                <option value="Casual">Casual</option>
                                <option value="Streetwear">Streetwear</option>
                                <option value="Training">Training</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description"
                                      rows="3" placeholder="Describe the sneaker..."></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price (₹) *</label>
                            <input type="number" class="form-control" id="price"
                                   name="price" step="0.01" min="0"
                                   placeholder="e.g., 89.00" required>
                        </div>
                        <div class="mb-3">
                            <label for="imageUrl" class="form-label">Image URL</label>
                            <input type="url" class="form-control" id="imageUrl"
                                   name="imageUrl" placeholder="https://images.unsplash.com/...">
                        </div>
                        <button type="submit" class="btn btn-bloom w-100">Add Sneaker</button>
                    </form>
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