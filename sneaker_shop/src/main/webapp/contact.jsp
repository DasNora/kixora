<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us — Kixora</title>
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
                    <nav class="d-none d-md-flex gap-2">
                        <a href="products" class="nav-link-custom">Shop</a>
                    </nav>
                </div>
            </div>
        </div>
    </header>

    <main class="flex-grow-1 contact-page">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <h2 class="mb-4 text-center contact-heading">📞 Contact Kixora</h2>

					<div class="card p-4 shadow-lg rounded-4">
   						 <div class="mb-3">
       						 📧 <strong>Email:</strong> support@kixora.com
  						 </div>

    					<div class="mb-3">		
        					📱 <strong>Phone:</strong> +91 98765 43210
    					</div>

    					<div class="mb-3">
        					📍 <strong>Address:</strong> 18 Maple Lane, Park Street,Kolkata, West Bengal 700016, India
    					</div>
					</div>
                </div>
            </div>
        </div>
    </main>

    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
           <small>&copy; 2026 Kixora. Premium Sneakers for Every Journey.</small>
        </div>
    </footer>
    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>