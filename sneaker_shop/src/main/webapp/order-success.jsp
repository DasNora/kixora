<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Order Successful</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
href="css/style.css">

</head>

<body class="bg-light">

<div class="container py-5">

<div class="row justify-content-center">

<div class="col-md-7">

<div class="card shadow border-0 rounded-4">

<div class="card-body text-center p-5">

<div style="font-size:70px;">
✅
</div>

<h2 class="text-success mt-3">
Payment Successful
</h2>

<p class="text-muted">
Thank you for shopping with
<strong>Kixora</strong>.
Your order has been placed successfully.
</p>

<hr>

<div class="row text-start mt-4">

<div class="col-6">
<strong>Order ID</strong>
</div>

<div class="col-6 text-end">
#${orderId}
</div>

</div>

<div class="row mt-3 text-start">

<div class="col-6">
<strong>Payment Status</strong>
</div>

<div class="col-6 text-end">
<span class="badge bg-success">
${paymentStatus}
</span>
</div>

</div>

<div class="row mt-3 text-start">

<div class="col-6">
<strong>Total Paid</strong>
</div>

<div class="col-6 text-end fw-bold">

₹<fmt:formatNumber
value="${amount}"
minFractionDigits="2"/>

</div>

</div>

<div class="row mt-3 text-start">

<div class="col-6">
<strong>Estimated Delivery</strong>
</div>

<div class="col-6 text-end">
3–5 Business Days
</div>

</div>

<div class="d-grid gap-2 mt-5">

<a href="products"
class="btn btn-bloom btn-lg">

Continue Shopping

</a>

<a href="my-orders"
class="btn btn-outline-dark">

View My Orders

</a>

</div>

</div>

</div>

</div>

</div>

</div>

</body>
</html>