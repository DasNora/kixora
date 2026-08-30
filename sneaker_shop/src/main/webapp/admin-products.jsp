<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.sneakershop.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
User user = (User)session.getAttribute("user");

if(user==null || !"ADMIN".equals(user.getRole())){
    response.sendRedirect("login");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Products</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

</head>

<body class="bg-light">

<nav class="navbar navbar-dark bg-dark">

<div class="container">

<a class="navbar-brand"
href="AdminDashboard.jsp">

👑 KIXORA ADMIN

</a>

<div>

<a href="AdminDashboard.jsp"
class="btn btn-primary fw-bold me-2">

🏠 Return to Dashboard

</a>

<a href="logout"
class="btn btn-danger btn-sm">

Logout

</a>

</div>

</div>

</nav>

<div class="container py-5">

<div class="d-flex justify-content-between mb-4">

<h2>

👟 Manage Products

</h2>

<a href="addProduct"
class="btn btn-success">

➕ Add Product

</a>

</div>

<table class="table table-bordered table-hover shadow bg-white">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Image</th>

<th>Name</th>

<th>Category</th>

<th>Price</th>

<th width="180">

Actions

</th>

</tr>

</thead>

<tbody>

<c:forEach var="product"
items="${products}">

<tr>

<td>

${product.id}

</td>

<td>

<img src="${product.imageUrl}"

style="width:80px;height:80px;object-fit:cover;">

</td>

<td>

${product.name}

</td>

<td>

${product.category}

</td>

<td>

₹${product.price}

</td>

<td>

<a href="edit-product?id=${product.id}"
class="btn btn-warning btn-sm">

✏ Edit

</a>

<a href="delete-product?id=${product.id}"
class="btn btn-danger btn-sm"
onclick="return confirm('Delete this product?');">

🗑 Delete

</a>

</td>

</tr>

</c:forEach>

</tbody>

</table>

</div>

</body>

</html>