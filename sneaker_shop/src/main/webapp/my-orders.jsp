<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>My Orders</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
href="css/style.css">

</head>

<body class="bg-light">

<div class="container py-5">

<h2 class="mb-4">

📦 My Orders

</h2>

<div class="card shadow border-0">

<div class="card-body">

<table class="table table-hover align-middle">

<thead>

<tr>

<th>Order ID</th>

<th>Date</th>

<th>Total</th>

<th>Payment</th>

<th>Order Status</th>

<th>Action</th>

</tr>

</thead>

<tbody>




<!-- Order Status -->


<tbody>

<c:forEach var="order" items="${orders}">

<tr>

    <!-- Order ID -->
    <td>
        KX-<fmt:formatNumber value="${order.orderId}" pattern="000000"/>
    </td>

    <!-- Date -->
    <td>
        <fmt:formatDate
            value="${order.createdAt}"
            pattern="dd MMM yyyy HH:mm"/>
    </td>

    <!-- Total -->
    <td>
        ₹<fmt:formatNumber
            value="${order.totalAmount}"
            minFractionDigits="2"/>
    </td>

    <!-- Payment -->
    <td>
        <span class="badge bg-success">
            ${order.paymentStatus}
        </span>
    </td>

    <!-- Order Status -->
    <td>

        <c:choose>

            <c:when test="${order.orderStatus == 'Processing'}">
                <span class="badge bg-warning text-dark">
                    📦 Processing
                </span>
            </c:when>

            <c:when test="${order.orderStatus == 'Shipped'}">
                <span class="badge bg-info">
                    🚚 Shipped
                </span>
            </c:when>

            <c:when test="${order.orderStatus == 'Out for Delivery'}">
                <span class="badge bg-primary">
                    🛵 Out for Delivery
                </span>
            </c:when>

            <c:when test="${order.orderStatus == 'Delivered'}">
                <span class="badge bg-success">
                    ✅ Delivered
                </span>
            </c:when>

            <c:otherwise>
                <span class="badge bg-secondary">
                    ${order.orderStatus}
                </span>
            </c:otherwise>

        </c:choose>

    </td>

    <!-- Action -->
    <td>

        <a href="order-details?orderId=${order.orderId}"
           class="btn btn-outline-primary btn-sm">

            View Details

        </a>
        
        <a href="delete-order?id=${order.orderId}" class="btn btn-outline-danger btn-sm" onclick="return confirm('Delete this order from your history?');">

    				🗑 Delete

		</a>

    </td>

</tr>

</c:forEach>

</tbody>

</td>



</tbody>

</table>
<div class="mt-4 d-flex justify-content-between">

    <a href="index.jsp" class="btn btn-dark">
        ← Continue Shopping
    </a>

</div>

</div>

</div>

</div>

</body>

</html>