<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} — Kixora</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <!-- Chatbot Spinner Animation CSS (from ReadMeAI blueprint) -->
    <style>
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .chat-loading-spinner {
            width: 20px; height: 20px;
            border: 3px solid #f3f3f3;
            border-top: 3px solid #007bff;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            display: inline-block;
            margin-left: 5px;
            vertical-align: middle;
        }
        .chat-loading-bubble {
            background-color: #f1f1f1; color: #555;
            padding: 8px 12px; border-radius: 15px;
            margin-bottom: 8px;
            display: inline-flex; align-items: center;
            gap: 8px; font-size: 13px; max-width: 80%;
        }
        .chat-message-user {
            margin-bottom: 8px; text-align: right;
        }
        .chat-message-ai {
            margin-bottom: 8px; text-align: left;
        }
    </style>
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

    <!-- Header -->
    <header class="bloom-header border-bottom">
        <div class="container">
            <div class="d-flex align-items-center justify-content-between py-3">
                <div class="d-flex align-items-center gap-3">
                    <a href="index.jsp" class="bloom-logo">KIX<span>ORA</span></a>
                    <nav class="d-none d-md-flex gap-2">
                        <a href="products" class="nav-link-custom">← Back to Catalog</a>
                    </nav>
                </div>
                <div class="d-flex align-items-center gap-2">
                    <a href="cart"
   class="btn btn-link text-dark text-decoration-none position-relative">

    🛒 <strong>Cart</strong>

    <span class="cart-badge"
          style="${cartCount == 0 ? 'display:none' : ''}">
        ${cartCount}
    </span>

</a>
                </div>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <main class="flex-grow-1">
        <div class="container py-5">

            <c:if test="${empty product}">
                <div class="alert alert-warning text-center">
                    Product not found. <a href="products">Browse catalog</a>
                </div>
            </c:if>

            <c:if test="${not empty product}">
                <div class="row g-5">
                    <!-- Product Image -->
                    <div class="col-md-6">
                        <img src="${product.imageUrl}"
                             alt="${product.name}"
                             class="img-fluid rounded shadow"
                             style="width:100%; max-height:500px; object-fit:cover;">
                    </div>

                    <!-- Product Info -->
                    <div class="col-md-6">
                        <span class="badge bg-secondary mb-2">${product.category}</span>
                        <h1 class="display-5 fw-bold">${product.name}</h1>
                        <p class="text-muted fs-5">${product.description}</p>
                        <h3 class="text-bloom fw-bold mb-4">${product.formattedPrice}</h3>

                        <!-- Add to Cart Button — calls real addToCart from main.js -->
                        <button class="btn btn-bloom btn-lg mb-2"
        onclick="handleAddToCartBtn(
            this,
            ${product.id},
            '${product.name}',
            ${product.price},
            '${product.imageUrl}'
        )">
    🛒 Add to Cart
</button>

                        <div class="mt-4 p-3 bg-light rounded">
                            <small class="text-muted">
                                ✓ Free shipping &nbsp;|&nbsp; ✓ 30-day returns &nbsp;|&nbsp; ✓ Authentic product
                            </small>
                        </div>
                    </div>
                </div>

                <!-- ===== Ratings Section (from ReadMeAI Blueprint: Feature A + B) ===== -->
                <div class="row mt-5">
                    <div class="col-md-6">
                        <div class="card p-4">
                            <h5>⭐ Product Rating</h5>
                            <div id="rating-box" class="mt-2">
                                Loading rating data...
                            </div>
								
                            <!-- Rating Submission UI -->
                            <div id="submission-area" class="mt-3 pt-3 border-top">
                                <h6>Rate this Sneaker</h6>
                                <select id="user-rating" class="form-select mb-2" style="width:auto;">
                                    <option value="5">⭐⭐⭐⭐⭐ 5 Stars</option>
                                    <option value="4">⭐⭐⭐⭐ 4 Stars</option>
                                    <option value="3">⭐⭐⭐ 3 Stars</option>
                                    <option value="2">⭐⭐ 2 Stars</option>
                                    <option value="1">⭐ 1 Star</option>
                                </select>
                                <textarea id="user-review" class="form-control mb-2" rows="3" placeholder="Write your review (optional)..."></textarea>
                                <button type="button" class="btn btn-bloom btn-sm" onclick="submitRating()">
                                    Submit Review
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-4">

    <div class="col-md-8">

        <div class="card p-4">

            <h4 class="mb-3">

                Customer Reviews

            </h4>

            <div id="reviews-container">

                Loading reviews...

            </div>

        </div>

    </div>

</div>

                <!-- ===== Floating AI Chatbot Widget (from ReadMeAI Blueprint: Feature C) ===== -->
                <div id="chat-box" style="position:fixed; bottom:20px; right:20px; width:320px; border:1px solid #ccc; background:#fff; z-index:1000; border-radius:8px; box-shadow:0 4px 12px rgba(0,0,0,0.15);">
                    <div style="background:#007bff; color:#fff; padding:12px 15px; font-weight:bold; border-radius:8px 8px 0 0; cursor:pointer;" onclick="toggleChat()">
                        🤖 AI Assistant <span id="chat-toggle-icon" style="float:right;">▼</span>
                    </div>
                    <div id="chat-body" style="display:block;">
                        <div id="chat-logs" style="height:200px; overflow-y:auto; padding:10px; font-size:13px;">
                            <div style="margin-bottom:8px;"><strong>AI:</strong> Hi! Ask me anything about this sneaker! 👟</div>
                        </div>
                        <div style="padding:10px; display:flex; border-top:1px solid #eee;">
                            <input type="text" id="user-input" style="flex:1; padding:5px 10px; border:1px solid #ddd; border-radius:4px;" placeholder="Ask about this sneaker..." onkeypress="handleKey(event)"/>
                            <button onclick="sendMessage()" style="margin-left:5px; padding:5px 12px; background:#007bff; color:#fff; border:none; border-radius:4px; cursor:pointer;">Send</button>
                        </div>
                    </div>
                </div>
            </c:if>

        </div>
    </main>

    <!-- Footer -->
    <footer class="border-top py-4 mt-auto">
        <div class="container text-center text-muted">
            <small>&copy; 2026 Kixora. Premium Sneakers for Every Journey.</small>
        </div>
    </footer>

   <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>

<%
    com.sneakershop.model.User detailUser =
        (com.sneakershop.model.User) session.getAttribute("user");
%>

<script>
    var sneakerCART_USER =
        '<%= (detailUser != null) ? detailUser.getUsername() : "anonymous" %>';

    var isLoggedIn = ${not empty sessionScope.user};

    console.log("isLoggedIn =", isLoggedIn);
</script>

<script src="js/main.js"></script>
    <script>
        // ===== Cart Function — delegates to main.js addToCart with JSP product data =====
       

        // ===== Rating Display (parses server-injected JSON) =====
        <%-- ratingDataJson is set by ProductDetailServlet from Spring Boot --%>
        var ratingData = ${empty ratingDataJson ? "{}" : ratingDataJson};
        var reviews = ${empty reviewsJson ? "[]" : reviewsJson};

        (function() {

            var ratingBox = document.getElementById('rating-box');

            if (ratingData && ratingData.totalReviews > 0) {

                var stars = '';

                for (var i = 0; i < Math.round(ratingData.averageRating); i++) {
                    stars += '⭐';
                }

                ratingBox.innerHTML =
                    '<p class="mb-1">' +
                    stars +
                    ' <strong>' +
                    Number(ratingData.averageRating).toFixed(1) +
                    '</strong> / 5</p>' +
                    '<small class="text-muted">Based on ' +
                    ratingData.totalReviews +
                    ' reviews</small>';

            } else {

                ratingBox.innerHTML =
                    '<p class="text-muted">No reviews yet. Be the first to rate!</p>';
            }

        })();
        var container =
            document.getElementById(
                    "reviews-container");

    if(reviews.length===0){

        container.innerHTML =
            "<p class='text-muted'>No reviews yet.</p>";

    }else{

        var html="";

        reviews.forEach(function(r){

            var stars="";

            for(var i=0;i<r.rating;i++){

                stars+="⭐";

            }

            html +=

            "<div class='border-bottom pb-3 mb-3'>"

            +"<h6>👤 "+r.userName+"</h6>"

            +"<div>"+stars+"</div>"

            +"<p class='mt-2'>"+r.review+"</p>"

            +"<small class='text-muted'>"
            +r.createdAt+
            "</small>"

            +"</div>";

        });

        container.innerHTML = html;

    }
        

        // ===== Rating Submission =====
        function submitRating() {

    var productId =
        new URLSearchParams(window.location.search)
        .get("id") || "${product.id}";

    var rating = document.getElementById("user-rating").value;

    var review = document.getElementById("user-review").value.trim();

    fetch("submit-rating", {

        method: "POST",

        headers: {
            "Content-Type":
            "application/x-www-form-urlencoded"
        },

        body:

            "productId=" + encodeURIComponent(productId)

            + "&rating=" + encodeURIComponent(rating)

            + "&review=" + encodeURIComponent(review)

    })

    .then(function(response){

        return response.json();

    })

    .then(function(data){

        if(data.status==="success"){

            alert("⭐⭐ Thank you for rating!");

            location.reload();

        }else{

            alert("Unable to submit rating.");

        }

    })

    .catch(function(error){

        console.error(error);

    });

}

        // ===== AI Chatbot Functions (from ReadMeAI blueprint) =====
        function handleKey(e) {
            if (e.key === 'Enter') sendMessage();
        }

        function sendMessage() {
            var inputField = document.getElementById('user-input');
            var message = inputField.value.trim();
            if (!message) return;

            var productId = new URLSearchParams(window.location.search).get('id') || "${product.id}";
            var logs = document.getElementById('chat-logs');

            // Append user message
            logs.innerHTML += '<div class="chat-message-user"><strong>You:</strong> ' + message + '</div>';
            inputField.value = '';
            logs.scrollTop = logs.scrollHeight;

            // Show loading spinner
            var loadingId = 'ai-loading-' + Date.now();
            logs.innerHTML += '<div id="' + loadingId + '" class="chat-loading-bubble">'
                + '<span>AI is thinking</span><div class="chat-loading-spinner"></div></div>';
            logs.scrollTop = logs.scrollHeight;

            // Send to ChatRelayServlet bridge
            fetch('chat-gateway', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'msg=' + encodeURIComponent(message) + '&productId=' + encodeURIComponent(productId)
            })
            .then(function(res) { return res.json(); })
            .then(function(data) {
                // Remove spinner
                var spinnerElement = document.getElementById(loadingId);
                if (spinnerElement) { spinnerElement.remove(); }

                // Append AI reply
                logs.innerHTML += '<div class="chat-message-ai"><strong>AI:</strong> ' + data.reply + '</div>';
                logs.scrollTop = logs.scrollHeight;
            })
            .catch(function() {
                var spinnerElement = document.getElementById(loadingId);
                if (spinnerElement) { spinnerElement.remove(); }
                logs.innerHTML += '<div style="color:red; margin-bottom:8px;"><strong>System:</strong> Connection timed out.</div>';
                logs.scrollTop = logs.scrollHeight;
            });
        }

        // Toggle chat widget minimize/maximize
        function toggleChat() {
            var chatBody = document.getElementById('chat-body');
            var icon = document.getElementById('chat-toggle-icon');
            if (chatBody.style.display === 'none') {
                chatBody.style.display = 'block';
                icon.textContent = '▼';
            } else {
                chatBody.style.display = 'none';
                icon.textContent = '▲';
            }
        }
    </script>
</body>
</html>