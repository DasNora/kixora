<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Kixora Checkout</title>

<link rel="stylesheet"
href="assets/bootstrap/css/bootstrap.min.css">

<link rel="stylesheet"
href="css/style.css">

<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

</head>

<body class="bg-light">

<header class="bloom-header border-bottom">

<div class="container py-3">

<a href="products" class="bloom-logo">

KIX<span>ORA</span>

</a>

</div>

</header>

<div class="container py-5">

<div class="row g-4">

<!-- LEFT SIDE -->

<div class="col-lg-7">

<div class="card shadow-sm border-0 rounded-4">

<div class="card-body p-4">

<h3 class="mb-4">

🛒 Your Order

</h3>

<c:forEach var="item" items="${cartItems}">

<div class="row align-items-center mb-4">

<div class="col-3">

<img src="${item.product.imageUrl}"

class="img-fluid rounded">

</div>

<div class="col-6">

<h5>

${item.product.name}

</h5>

<div class="text-muted">

Quantity :

${item.quantity}

</div>

</div>

<div class="col-3 text-end fw-bold">

₹<fmt:formatNumber

value="${item.product.price * item.quantity}"

minFractionDigits="2"/>

</div>

</div>

<hr>

</c:forEach>

</div>

</div>

</div>

<!-- RIGHT SIDE -->

<div class="col-lg-5">

<div class="card shadow border-0 rounded-4">

<div class="card-body p-4">

<h3 class="mb-4">

Payment Summary

</h3>

<div class="d-flex justify-content-between">

<span>Subtotal</span>

<strong>

₹<fmt:formatNumber

value="${amount}"

minFractionDigits="2"/>

</strong>

</div>

<div class="d-flex justify-content-between mt-3">

<span>Shipping</span>

<span class="text-success">

FREE

</span>

</div>

<div class="d-flex justify-content-between mt-3">

<span>GST</span>

<span>

Included

</span>

</div>

<hr>

<div class="d-flex justify-content-between fs-4">

<strong>Total</strong>

<strong>

₹<fmt:formatNumber

value="${amount}"

minFractionDigits="2"/>

</strong>

</div>

<div class="alert alert-success mt-4">

🔒 Secure payment powered by Razorpay

</div>

<button

id="payBtn"

class="btn btn-bloom btn-lg w-100">

Pay Securely

</button>

</div>

</div>

</div>

</div>

</div>
<script>
var order = ${order};
document.getElementById("payBtn")
.onclick = function(){

    var options = {

        key : "<%=com.sneakershop.util.RazorpayConfig.KEY_ID%>",

        amount : order.amount,

        currency : order.currency,

        name : "Kixora",

        description : "Sneaker Purchase",

        order_id : order.id,

        handler : function(response){

            var form =
                    document.createElement("form");

            form.method="POST";

            form.action="verify-payment";

            function add(name,value){

                var input =
                    document.createElement("input");

                input.type="hidden";

                input.name=name;

                input.value=value;

                form.appendChild(input);

            }

            add("razorpay_payment_id",
                response.razorpay_payment_id);

            add("razorpay_order_id",
                response.razorpay_order_id);

            add("razorpay_signature",
                response.razorpay_signature);

            document.body.appendChild(form);

            form.submit();

        }

    };

    var rzp =
            new Razorpay(options);

    rzp.open();

};

</script>

</body>
</html>