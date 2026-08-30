<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.sneakershop.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
User user=(User)session.getAttribute("user");

if(user==null || !"ADMIN".equals(user.getRole())){
    response.sendRedirect("login");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Manage Orders</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

</head>

<body class="bg-light">

<div class="container py-5">

<div class="d-flex justify-content-between align-items-center mb-4">

    <h2 class="fw-bold mb-0">
        📦 Manage Orders
    </h2>

    <a href="AdminDashboard.jsp" class="btn btn-dark">
        ← Return to Dashboard
    </a>

</div>

<table class="table table-bordered table-hover bg-white shadow">

<thead class="table-dark">

<tr>

<th>Order</th>

<th>Customer</th>

<th>Date</th>

<th>Total</th>

<th>Payment</th>

<th>Status</th>

<th>Action</th>

</tr>

</thead>

<tbody>

<c:forEach items="${orders}" var="order">

<tr>

<td>

KX-${order.orderId}

</td>

<td>

${order.customerName}

</td>

<td>

${order.createdAt}

</td>

<td>

₹${order.totalAmount}

</td>

<td>

${order.paymentStatus}

</td>

<td>

<form action="update-order-status" method="post">

<input
type="hidden"
name="orderId"
value="${order.orderId}">

<select
name="status"
class="form-select">

<option ${order.orderStatus=='Processing'?'selected':''}>Processing</option>

<option ${order.orderStatus=='Shipped'?'selected':''}>Shipped</option>

<option ${order.orderStatus=='Out for Delivery'?'selected':''}>Out for Delivery</option>

<option ${order.orderStatus=='Delivered'?'selected':''}>Delivered</option>

</select>

</td>

<td>

<button
class="btn btn-success btn-sm">

Update

</button>

</form>

</td>

</tr>

</c:forEach>

</tbody>

</table>

</div>

</body>

</html>