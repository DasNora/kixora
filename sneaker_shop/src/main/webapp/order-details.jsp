<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Order Details</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
href="css/style.css">

</head>

<body class="bg-light">

<div class="container py-5">

<div class="card shadow border-0">

<div class="card-body">

<h2>

📦 Order KX-<fmt:formatNumber value="${order.orderId}" pattern="000000"/>

</h2>

<p>

<strong>Payment:</strong>

<span class="badge bg-success">

${order.paymentStatus}

</span>

</p>

<p>

<strong>Order Status:</strong>

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

</c:choose>

</p>

<hr>

<c:forEach var="item" items="${items}">

<div class="row align-items-center mb-4">

<div class="col-2">

<img src="${item.product.imageUrl}"
class="img-fluid rounded">

</div>

<div class="col-6">

<h5>${item.product.name}</h5>

Quantity :
${item.quantity}

</div>

<div class="col-4 text-end">

₹<fmt:formatNumber
value="${item.price}"
minFractionDigits="2"/>

</div>

</div>

<hr>

</c:forEach>

<h4 class="text-end">

Total :

₹<fmt:formatNumber
value="${order.totalAmount}"
minFractionDigits="2"/>

</h4>

<div class="mt-4">

<a href="my-orders"
class="btn btn-dark">

← Back to My Orders

</a>

</div>

</div>

</div>

</div>

</body>

</html>