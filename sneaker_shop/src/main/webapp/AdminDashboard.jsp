<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.sneakershop.model.User"%>

<%
User user = (User) session.getAttribute("user");

if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("login");
    return;
}
%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Kixora Admin Dashboard</title>

<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">

<style>

body{
    background:#f8f9fa;
}

/* Navbar */

.navbar{
    padding:18px 0;
}

/* Dashboard Title */

.dashboard-title{
    margin-top:70px;
    margin-bottom:60px;
    text-align:center;
}

.dashboard-title h1{
    font-weight:800;
    font-size:4rem;
}

.dashboard-line{
    width:120px;
    height:4px;
    background:#8b5cf6;
    border-radius:20px;
    margin:18px auto 0;
}

/* Cards */

.card{
    border:none;
    border-radius:18px;
    box-shadow:0 12px 30px rgba(0,0,0,.08);
    transition:.3s;
}

.card:hover{
    transform:translateY(-8px);
    box-shadow:0 20px 45px rgba(0,0,0,.15);
}

.card-body{
    padding:45px 25px;
}

.card-icon{
    font-size:60px;
    margin-bottom:18px;
}

.card h3{
    font-weight:700;
}

.btn-dark{
    border-radius:10px;
    padding:10px 28px;
}

</style>

</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">

<div class="container-fluid px-5">

<a class="navbar-brand fw-bold fs-3" href="#">

👑 KIXORA ADMIN

</a>

<div>

<span class="text-white me-4 fs-5">

Welcome,

<strong><%=user.getFirstName()%></strong>

</span>

<a href="logout"
class="btn btn-outline-light">

Logout

</a>

</div>

</div>

</nav>


<div class="container">

<div class="dashboard-title">

<h1>

Dashboard

</h1>

<div class="dashboard-line"></div>

</div>


<div class="row justify-content-center g-5">

<!-- Products -->

<div class="col-lg-4 col-md-6">

<div class="card">

<div class="card-body text-center">

<div class="card-icon">

👟

</div>

<h3>

Products

</h3>

<p class="text-muted">

Manage all sneakers

</p>

<a href="admin-products"
class="btn btn-dark">

Manage

</a>

</div>

</div>

</div>

<!-- Orders -->

<div class="col-lg-4 col-md-6">

<div class="card">

<div class="card-body text-center">

<div class="card-icon">

📦

</div>

<h3>

Orders

</h3>

<p class="text-muted">

Manage customer orders

</p>

<a href="admin-orders"
class="btn btn-dark">

Manage

</a>

</div>

</div>

</div>

<!-- Users -->

<div class="col-lg-4 col-md-6">

<div class="card">

<div class="card-body text-center">

<div class="card-icon">

👥

</div>

<h3>

Users

</h3>

<p class="text-muted">

View registered users

</p>

<a href="admin-users"
class="btn btn-dark">

Manage

</a>

</div>

</div>

</div>

</div>

</div>

<script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>