<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kixora | Where Style Meets Motion</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- Header -->
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
               <a href="index.jsp" class="bloom-logo">KIX<span>ORA</span></a>
                <nav class="d-none d-md-flex align-items-center gap-3">
                    <div class="dropdown">

    <a class="nav-link-custom dropdown-toggle"
       href="#"
       data-bs-toggle="dropdown"
       aria-expanded="false">
        Browse
    </a>

    <ul class="dropdown-menu">

        <li><a class="dropdown-item" href="products">👟 All</a></li>
<li><a class="dropdown-item" href="products?category=Running">🏃 Running</a></li>
<li><a class="dropdown-item" href="products?category=Lifestyle">🌆 Lifestyle</a></li>
<li><a class="dropdown-item" href="products?category=Retro">✨ Retro</a></li>
<li><a class="dropdown-item" href="products?category=Performance">⚡ Performance</a></li>
<li><a class="dropdown-item" href="products?category=Premium">💎 Premium</a></li>
<li><a class="dropdown-item" href="products?category=Casual">😎 Casual</a></li>
<li><a class="dropdown-item" href="products?category=Streetwear">🛹 Streetwear</a></li>
<li><a class="dropdown-item" href="products?category=Training">🏋 Training</a></li>
    </ul>

</div>
                   
                    <a href="contact.jsp" class="nav-link-custom">Contact</a>
                </nav>
               <div class="d-flex align-items-center">

    <!-- Cart -->
    <a href="cart"
       class="btn btn-link text-dark text-decoration-none position-relative">

        🛒 <strong>Cart</strong>

        <span class="cart-badge"
              style="${cartCount == 0 ? 'display:none' : ''}">
            ${cartCount}
        </span>

    </a>

    <c:if test="${not empty sessionScope.user}">

        <div class="vr mx-3"></div>

        <!-- My Orders -->
        <a href="my-orders"
           class="btn btn-link text-dark text-decoration-none">

            📦 <strong>My Orders</strong>

        </a>

    </c:if>

    <div class="vr mx-3"></div>

    <c:choose>

        <c:when test="${empty sessionScope.user}">

            <a href="login"
               class="btn btn-sm btn-outline-secondary me-2">

                Sign In

            </a>

            <a href="register"
               class="btn btn-sm btn-bloom">

                Sign Up

            </a>

        </c:when>

        <c:otherwise>

            <span class="fw-semibold me-3">

                👋 Welcome, ${sessionScope.user.firstName}

            </span>

            <a href="logout"
               class="btn btn-sm btn-outline-danger">

                Logout

            </a>

        </c:otherwise>

    </c:choose>

</div>
                </div>
            </div>
        
    </header>

    <!-- Main Content -->
    <main class="flex-grow-1">
        <div class="container py-4">

            <h2 class="mb-1">👟 Our Sneaker Collection</h2>
            <p class="text-muted mb-4">Showing <strong>${fn:length(productList)}</strong> sneakers from the catalog</p>

            <%-- Error message (e.g., product not found) --%>
            <c:if test="${not empty error}">
                <div class="alert alert-warning">${error}</div>
            </c:if>

            <!-- Product Grid -->
            <div class="row g-4">
                <c:forEach var="product" items="${productList}">
                    <div class="col-sm-6 col-lg-4 col-xl-3">
                        <div class="card h-100 shadow-sm">
                            <!-- Sneaker Image -->
                            <img src="${product.imageUrl}"
                                 class="card-img-top"
                                 alt="${product.name}"
                                 style="height:220px; object-fit:cover;"
                                 loading="lazy">

                            <div class="card-body d-flex flex-column">
                                <!-- Category Badge -->
                                <span class="badge bg-secondary mb-2 align-self-start">
                                    ${product.category}
                                </span>

                                <!-- Sneaker Name -->
                                <h5 class="card-title">${product.name}</h5>
                                <c:if test="${product.totalReviews > 0}">

    									<div class="mb-2">
												⭐<fmt:formatNumber value="${product.averageRating}" minFractionDigits="1" maxFractionDigits="1"/>
         										<!--  ⭐${product.averageRating}-->
												<small class="text-muted">(${product.totalReviews} Reviews) </small>
										</div>

								</c:if>

                                <!-- Price -->
                                <p class="card-text fw-bold text-bloom mb-2">
                                    ${product.formattedPrice}
                                </p>

                                <!-- View Details Button — routes through ProductDetailServlet for ratings -->
                               <div class="mt-auto d-grid gap-2">

    <a href="product-detail?id=${product.id}"
       class="btn btn-outline-secondary btn-sm">
        View Details
    </a>

    <button class="btn btn-bloom btn-sm"
        onclick="handleAddToCartBtn(
            this,
            ${product.id},
            '${product.name}',
            ${product.price},
            '${product.imageUrl}'
        )">
        🛒 Add to Cart
    </button>

</div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <%-- Show message if no products exist --%>
            <c:if test="${empty productList}">
                <div class="text-center py-5">
                    <p class="text-muted">No sneakers in the catalog yet.</p>
                    <a href="index.jsp" class="btn btn-outline-secondary">Go Home</a>
                </div>
            </c:if>

        </div>
    </main>

    <!-- Footer -->
    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
            <small>&copy; 2026 Kixora. Premium Sneakers for Every Journey.</small>
        </div>
    </footer>

    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script>
        // Inject username for cart namespace isolation
        <%
            com.sneakershop.model.User productsUser =
                (com.sneakershop.model.User) session.getAttribute("user");
        %>
        var sneakerCART_USER = '<%= (productsUser != null) ? productsUser.getUsername() : "anonymous" %>';
    </script>
    <script>
    var isLoggedIn = ${not empty sessionScope.user};
</script>
    <script src="js/main.js"></script>
</body>
</html>