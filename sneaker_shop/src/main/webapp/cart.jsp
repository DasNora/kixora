<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
           prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart — Kixora</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<script>
setInterval(function () {

    fetch("session-check")
        .then(function(response) {
            return response.text();
        })
        .then(function(status) {

            if (status === "false") {

                alert("Your session has expired. Please log in again.");

                window.location.href = "login";
            }

        })
        .catch(function(error) {
            console.error(error);
        });

}, 30000); // Check every 30 seconds
</script>
<body class="d-flex flex-column min-vh-100">
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
                <div class="d-flex align-items-center gap-3">
                    <a href="index.jsp" class="bloom-logo">KIX<span>ORA</span></a>
                </div>
            </div>
        </div>
    </header>

    <main class="flex-grow-1">
        <div class="container py-5 text-center">
            <h2 class="mb-3">🛒 Your Shopping Cart</h2>
            <p class="text-muted mb-4">Your selected sneakers are securely saved to your Kixora account.</p>
            <c:choose>

<c:when test="${empty cartItems}">

<div class="empty-cart">

    <h3>Your cart is empty</h3>

    <a href="products"
       class="btn btn-bloom mt-3">

        Continue Shopping

    </a>

</div>

</c:when>

<c:otherwise>

<div class="row">

    <div class="col-lg-8">

        <c:forEach var="item" items="${cartItems}">

            <div class="card mb-3 shadow-sm">

                <div class="row g-0 align-items-center">

                    <div class="col-md-2 text-center p-2">

                        <img src="${item.product.imageUrl}"
                             class="img-fluid rounded"
                             style="height:100px;object-fit:cover;">

                    </div>

                    <div class="col-md-4">

                        <div class="card-body">

                            <h5>${item.product.name}</h5>

                            <p class="mb-0">

                                ₹
                                <fmt:formatNumber
                                        value="${item.product.price}"
                                        minFractionDigits="2"/>

                            </p>

                        </div>

                    </div>

                    <div class="col-md-3 text-center">

                        <form action="cart" method="post" class="d-inline">

                            <input type="hidden" name="action" value="decrease">
                            <input type="hidden" name="cartId" value="${item.cartId}">

                            <button class="btn btn-outline-dark btn-sm">
                                −
                            </button>

                        </form>

                        <strong class="mx-2">

                            ${item.quantity}

                        </strong>

                        <form action="cart" method="post" class="d-inline">

                            <input type="hidden" name="action" value="increase">
                            <input type="hidden" name="cartId" value="${item.cartId}">

                            <button class="btn btn-outline-dark btn-sm">
                                +
                            </button>

                        </form>

                    </div>

                    <div class="col-md-3 text-center">

                        <div class="fw-bold mb-2">

                            ₹

                            <fmt:formatNumber
                                    value="${item.product.price * item.quantity}"
                                    minFractionDigits="2"/>

                        </div>

                        <form action="cart" method="post">

                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="cartId" value="${item.cartId}">

                            <button class="btn btn-danger btn-sm">

                                Remove

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </c:forEach>

    </div>

    <div class="col-lg-4">

        <div class="card p-4 shadow-sm">

            <h4>Order Summary</h4>

            <hr>
           
            
             <!--  old code
                Items

                <span class="float-end">

                    ${cartItems.size()}

                </span>
                -->
                <c:set var="totalQuantity" value="0" />
				<c:set var="totalCartValue" value="0.0" />

				<%-- 2. Loop through items to calculate totals --%>
				<c:forEach var="item" items="${cartItems}">
				    <c:set var="totalQuantity" value="${totalQuantity + item.quantity}" />
				    <c:set var="totalCartValue" value="${totalCartValue + (item.product.price * item.quantity)}" />
				</c:forEach>
				
				<%-- 3. Display the updated summary --%>
				<p class="mb-2">
				    Items (Total Quantity)
				    <span class="float-end">
				        ${totalQuantity}
				    </span>
				</p>

				<p class="fw-bold">
				    Total Value
				    <span class="float-end">
				        ₹<fmt:formatNumber value="${totalCartValue}" minFractionDigits="2" maxFractionDigits="2"/>
				    </span>
				</p>

           

            <form action="payment" method="post">

    		<button
        		class="btn btn-bloom w-100">

        			Proceed to Checkout

    		</button>

			</form>

            <a href="products"
               class="btn btn-outline-secondary mt-2">

                Continue Shopping

            </a>

        </div>

    </div>

</div>

</c:otherwise>

</c:choose>
        </div>
    </main>

    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
            <small>&copy; 2026 Kixora. Premium Sneakers for Every Journey.</small>
        </div>
    </footer>
    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script>
        // Inject username for cart namespace isolation (Fix 4)
        <%
            com.sneakershop.model.User cartUser =
                (com.sneakershop.model.User) session.getAttribute("user");
        %>
        var sneakerCART_USER = '<%= (cartUser != null) ? cartUser.getUsername() : "anonymous" %>';
    </script>
</body>
</html>