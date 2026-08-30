<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.sneakershop.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
User admin = (User) session.getAttribute("user");

if (admin == null || !"ADMIN".equals(admin.getRole())) {
    response.sendRedirect("login");
    return;
}
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Manage Users</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

<style>

body{
    background:#f8f9fa;
}

.navbar{
    padding:18px 0;
}

.table{
    border-radius:15px;
    overflow:hidden;
}

.card{
    border:none;
    border-radius:18px;
    box-shadow:0 12px 30px rgba(0,0,0,.08);
}

h2{
    font-weight:700;
}

.badge{
    font-size:14px;
    padding:8px 14px;
}

</style>

</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">

<div class="container-fluid px-5">

<a class="navbar-brand fw-bold fs-3">

👑 KIXORA ADMIN

</a>

<div>

<span class="text-white me-4">

Welcome,

<strong><%=admin.getFirstName()%></strong>

</span>

<a href="logout"
class="btn btn-outline-light">

Logout

</a>

</div>

</div>

</nav>


<div class="container mt-5">

<div class="d-flex justify-content-between align-items-center mb-4">

<div>

<h2>

👥 Manage Users

</h2>

<p class="text-muted mb-0">

Registered users of Kixora

</p>

</div>

<a href="AdminDashboard.jsp"
class="btn btn-dark">

← Return to Dashboard

</a>

</div>



<div class="card">

<div class="card-body">

<table class="table table-hover align-middle">

<thead class="table-dark">

<tr>

<th>ID</th>

<th>Username</th>

<th>Name</th>

<th>City</th>

<th>Role</th>

</tr>

</thead>

<tbody>

<c:forEach items="${users}" var="user">

<tr>

<td>

${user.id}

</td>

<td>

${user.username}

</td>

<td>

${user.firstName} ${user.lastName}

</td>

<td>

${user.city}

</td>

<td>

<c:choose>

<c:when test="${user.role=='ADMIN'}">

<span class="badge bg-danger">

ADMIN

</span>

</c:when>

<c:otherwise>

<span class="badge bg-primary">

USER

</span>

</c:otherwise>

</c:choose>

</td>

</tr>

</c:forEach>

</tbody>

</table>

</div>

</div>

</div>

<script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>