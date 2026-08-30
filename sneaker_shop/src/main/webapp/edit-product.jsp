<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.sneakershop.model.Product"%>

<%
Product product = (Product) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Edit Product</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

</head>

<body class="bg-light">

<div class="container py-5">

<div class="card shadow mx-auto" style="max-width:700px;">

<div class="card-header bg-dark text-white">

<h3>Edit Product</h3>

</div>

<div class="card-body">

<form action="edit-product" method="post">

<input type="hidden"
name="id"
value="<%=product.getId()%>">

<div class="mb-3">

<label>Name</label>

<input
type="text"
name="name"
class="form-control"
value="<%=product.getName()%>"
required>

</div>

<div class="mb-3">

<label>Category</label>

<input
type="text"
name="category"
class="form-control"
value="<%=product.getCategory()%>"
required>

</div>

<div class="mb-3">

<label>Description</label>

<textarea
name="description"
class="form-control"
rows="4"
required><%=product.getDescription()%></textarea>

</div>

<div class="mb-3">

<label>Price</label>

<input
type="number"
step="0.01"
name="price"
class="form-control"
value="<%=product.getPrice()%>"
required>

</div>

<div class="mb-3">

<label>Image URL</label>

<input
type="text"
name="imageUrl"
class="form-control"
value="<%=product.getImageUrl()%>"
required>

</div>

<div class="d-flex justify-content-between">

<a href="admin-products"
class="btn btn-secondary">

Cancel

</a>

<button
type="submit"
class="btn btn-success">

Update Product

</button>

</div>

</form>

</div>

</div>

</div>

</body>

</html>