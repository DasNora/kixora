/**
 * main.js — Handles all interactivity for Sneaker Shop.
 * Includes: cart CRUD, search filter, like button, header scroll, mobile menu.
 * (All .html references fixed to .jsp/servlet paths; currency changed to ₹)
 */

// ===== CART HELPERS (using localStorage — namespaced by user) =====

// Injected by JSP: var sneakerCART_USER = 'username' or 'anonymous'
function getCartKey() {
  var username = (typeof sneakerCART_USER !== 'undefined') ? sneakerCART_USER : 'anonymous';
  return 'sneaker_cart_' + username;
}

function getCart() {
  return JSON.parse(localStorage.getItem(getCartKey()) || '[]');
}

function saveCart(cart) {
  localStorage.setItem(getCartKey(), JSON.stringify(cart));
}

function getCartCount() {
  return getCart().reduce((sum, item) => sum + item.quantity, 0);
}

function updateCartBadge() {
  const badges = document.querySelectorAll('.cart-badge');
  const count = getCartCount();
  badges.forEach(badge => {
    badge.textContent = count > 99 ? '99+' : count;
    badge.style.display = count > 0 ? 'flex' : 'none';
  });
}

function addToCart(productId, productName, productPrice, productImage, quantity = 1) {
  const cart = getCart();
  const existing = cart.find(item => item.id === productId);

  if (existing) {
    existing.quantity += quantity;
  } else {
    cart.push({
      id: productId,
      name: productName,
      price: productPrice,
      image: productImage,
      quantity: quantity
    });
  }
  saveCart(cart);
  updateCartBadge();
}

function removeFromCart(productId) {
  let cart = getCart();
  cart = cart.filter(item => item.id !== productId);
  saveCart(cart);
  updateCartBadge();
}

function updateCartQty(productId, newQty) {
  const cart = getCart();
  const item = cart.find(item => item.id === productId);
  if (item) {
    item.quantity = Math.max(1, newQty);
    saveCart(cart);
    updateCartBadge();
  }
}

// ===== HEADER SCROLL EFFECT =====
function initHeaderScroll() {
  const header = document.querySelector('.bloom-header');
  if (!header) return;
  window.addEventListener('scroll', () => {
    header.classList.toggle('scrolled', window.scrollY > 10);
  });
}

// ===== MOBILE MENU =====
function initMobileMenu() {
	const menuContent = document.getElementById('mobileMenu');
	const toggleBtn = document.getElementById('mobileMenuBtn');

	const closeIcon = toggleBtn ? toggleBtn.querySelector('.menu-open-icon') : null;
	const openIcon = toggleBtn ? toggleBtn.querySelector('.menu-close-icon') : null;
  if (!toggleBtn || !menuContent) return;

  toggleBtn.addEventListener('click', () => {
    const isOpen = menuContent.classList.toggle('show');
    if (closeIcon && openIcon) {
      closeIcon.classList.toggle('d-none', isOpen);
      openIcon.classList.toggle('d-none', !isOpen);
    }
  });

  // Close on link click
  menuContent.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', () => {
      menuContent.classList.remove('show');
    });
  });
}

// ===== PRODUCT CARD RENDERING (shared) =====
function renderProductCard(product) {
  return `
    <div class="product-card">
      <div class="product-card-img-wrap" onclick="location.href='product-detail?id=${product.id}'">
        <img src="${product.image}" alt="${product.name}" loading="lazy"
             onerror="this.style.display='none'">
        <div class="quick-view-overlay">
          <button class="btn btn-bloom btn-sm" onclick="event.stopPropagation(); location.href='product-detail?id=${product.id}'">
            👁 Quick View
          </button>
        </div>
        <button class="like-btn" onclick="toggleLike(this, event)" title="Like">
          ♥
        </button>
      </div>
      <div class="product-card-body">
        <a href="product-detail?id=${product.id}" class="product-name">${product.name}</a>
        <div class="product-price">₹${product.price.toFixed(2)}</div>
        <button class="btn btn-bloom w-100" onclick="handleAddToCartBtn(this, ${product.id}, '${product.name.replace(/'/g, "\\'")}', ${product.price}, '${product.image.replace(/'/g, "\\'")}')">
          🛒 Add to Cart
        </button>
      </div>
    </div>
  `;
}

// ===== RENDER ALL PRODUCTS on index.jsp =====
function renderProductGrid(containerSelector, productList) {
  const grid = document.querySelector(containerSelector);
  if (!grid) return;

  if (productList.length === 0) {
    grid.innerHTML = `
      <div class="col-span-full text-center py-5">
        <div style="font-size:3rem">🔍</div>
        <h3>No products found</h3>
        <p class="text-muted">Try adjusting your filters or search terms.</p>
      </div>`;
    return;
  }
  grid.style.display = 'grid';
  grid.innerHTML = productList.map(p => renderProductCard(p)).join('');
}

// ===== SEARCH FILTER =====
function initSearch() {
  const searchInput = document.querySelector('.bloom-search');
  if (!searchInput) return;

  searchInput.addEventListener('input', function () {
    const query = this.value.toLowerCase().trim();
    const filtered = query
      ? PRODUCTS.filter(p => p.name.toLowerCase().includes(query))
      : PRODUCTS;
    renderProductGrid('.product-grid', filtered);
  });
}

// ===== LIKE BUTTON =====
function toggleLike(btn, event) {
  event.stopPropagation();
  btn.classList.toggle('liked');
}

// ===== ADD TO CART BUTTON (with animation) =====
function handleAddToCartBtn(btn, id, name, price, image) {

    if (!isLoggedIn) {
        window.location.href = "login";
        return;
    }

    if (btn.classList.contains("btn-added"))
        return;

    fetch("cart", {

        method: "POST",

        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },

        body:
            "action=add&productId=" + encodeURIComponent(id)

    })

    .then(function(response){

        if(!response.ok){

            throw new Error("Unable to add item.");

        }

        return fetch("cart-count");

    })

    .then(function(response){

        return response.text();

    })

    .then(function(count){

        const badge = document.querySelector(".cart-badge");

        badge.textContent = count;

        badge.style.display =
            count == 0 ? "none" : "inline-block";

        const originalHTML = btn.innerHTML;

        btn.classList.add("btn-added");
        btn.innerHTML = "✅ Added to Cart!";
        btn.disabled = true;

        setTimeout(function(){

            btn.classList.remove("btn-added");
            btn.innerHTML = originalHTML;
            btn.disabled = false;

        },2000);

    })

    .catch(function(error){

        console.error(error);

    });

}
// ===== PRODUCT DETAIL PAGE =====
function initProductDetail() {
  const container = document.getElementById('productDetailContainer');
  if (!container) return;

  const params = new URLSearchParams(window.location.search);
  const id = parseInt(params.get('id'));
  const product = PRODUCTS.find(p => p.id === id);

  if (!product) {
    container.innerHTML = `
      <div class="text-center py-5">
        <div style="font-size:4rem">🔍</div>
        <h2>Product Not Found</h2>
        <p class="text-muted">The product you're looking for doesn't exist.</p>
        <a href="index.jsp" class="btn btn-bloom mt-3">← Back to Shop</a>
      </div>`;
    return;
  }

  document.title = product.name + ' — Sneaker Shop';
  container.innerHTML = `
    <nav class="bloom-breadcrumb mb-3">
      <a href="index.jsp">Home</a> / <span class="text-muted">${product.name}</span>
    </nav>
    <div class="row g-4">
      <div class="col-md-6">
        <div class="detail-img-wrap">
          <img src="${product.image}" alt="${product.name}" class="w-100"
               onerror="this.src='images/no-image.svg'">
        </div>
      </div>
      <div class="col-md-6 d-flex flex-column">
        <h1 class="mb-2">${product.name}</h1>
        <div class="detail-price mb-3">₹${product.price.toFixed(2)}</div>
        <p class="text-muted mb-4">${product.description}</p>
        <hr>
        <h6 class="fw-semibold mb-2">Features</h6>
        <ul class="mb-4">
          <li>Premium materials</li>
          <li>Comfortable fit</li>
          <li>Durable construction</li>
          <li>Modern design</li>
        </ul>
        <div class="mt-auto">
          <button class="btn btn-bloom btn-lg w-100" onclick="
            addToCart(${product.id}, '${product.name.replace(/'/g, "\\'")}', ${product.price}, '${product.image.replace(/'/g, "\\'")}');
            this.classList.add('btn-added'); this.innerHTML='✅ Added to Cart!';
            setTimeout(()=>{ this.classList.remove('btn-added'); this.innerHTML='🛒 Add to Cart'; }, 2000);
          ">🛒 Add to Cart</button>
        </div>
      </div>
    </div>

    <!-- Related Products (show 4 random) -->
    <div class="mt-5 pt-4 border-top">
      <h3 class="mb-3">You May Also Like</h3>
      <div class="related-grid" id="relatedGrid"></div>
    </div>
  `;

  // Related products: show 4 random that are NOT this product
  const others = PRODUCTS.filter(p => p.id !== product.id);
  const shuffled = others.sort(() => 0.5 - Math.random()).slice(0, 4);
  const relatedGrid = document.getElementById('relatedGrid');
  if (relatedGrid) {
    relatedGrid.innerHTML = shuffled.map(p => renderProductCard(p)).join('');
  }
}

// ===== CART PAGE =====
function initCartPage() {
  const container = document.getElementById('cartContainer');
  if (!container) return;

  const cart = getCart();

  if (cart.length === 0) {
	container.innerHTML = `
	    <div class="empty-cart">
	        <a href="products" class="btn btn-bloom mt-3">
	            Continue Shopping
	        </a>
	    </div>
	`;
    return;
  }

  const subtotal = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  container.innerHTML = `
    <div class="row g-4">
      <div class="col-lg-8">
        <div class="list-group list-group-flush">
          ${cart.map(item => `
            <div class="list-group-item d-flex align-items-center gap-3 py-3">
              <img src="${item.image}" alt="${item.name}"
                   class="cart-item-img"
                   onerror="this.src='images/no-image.svg'">
              <div class="flex-grow-1">
                <h6 class="mb-1">${item.name}</h6>
                <p class="text-muted small mb-0">₹${item.price.toFixed(2)} each</p>
              </div>
              <div class="quantity-control">
                <button onclick="updateCartQty(${item.id}, ${item.quantity - 1}); initCartPage();">−</button>
                <span>${item.quantity}</span>
                <button onclick="updateCartQty(${item.id}, ${item.quantity + 1}); initCartPage();">+</button>
              </div>
              <div class="fw-bold ms-3">₹${(item.price * item.quantity).toFixed(2)}</div>
              <button class="btn btn-sm text-danger ms-2" onclick="removeFromCart(${item.id}); initCartPage();">✕</button>
            </div>
          `).join('')}
        </div>
      </div>
      <div class="col-lg-4">
        <div class="card p-3 shadow-sm">
          <h5 class="mb-3">Order Summary</h5>
          <div class="d-flex justify-content-between mb-2">
            <span>Subtotal</span>
            <span class="fw-bold">₹${subtotal.toFixed(2)}</span>
          </div>
          <div class="d-flex justify-content-between mb-2">
            <span>Shipping</span>
            <span class="text-muted">Calculated at checkout</span>
          </div>
          <hr>
          <div class="d-flex justify-content-between mb-3">
            <span class="fw-bold">Total</span>
            <span class="fw-bold fs-5">₹${subtotal.toFixed(2)}</span>
          </div>
          <button class="btn btn-bloom w-100" onclick="alert('Checkout feature coming soon!')">Proceed to Checkout</button>
          <a href="products" class="btn btn-bloom-outline w-100 mt-2">Continue Shopping</a>
        </div>
      </div>
    </div>
  `;
}

// ===== CONTACT FORM =====
function initContactForm() {
  const form = document.getElementById('contactForm');
  if (!form) return;

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    const alertBox = document.getElementById('contactAlert');
    alertBox.classList.remove('d-none');
    form.reset();
    setTimeout(() => alertBox.classList.add('d-none'), 5000);
  });
}

// ===== INITIALIZE EVERYTHING ON DOM READY =====
document.addEventListener('DOMContentLoaded', () => {
  initHeaderScroll();
  initMobileMenu();
 // updateCartBadge();
  //initSearch();

  // Identify which page we're on and call the right initializer

  // Home page: render all products
  //if (document.querySelector('.product-grid')) {
   // renderProductGrid('.product-grid', PRODUCTS);
  //}

  // Product detail page
  if (document.getElementById('productDetailContainer')) {
    initProductDetail();
  }

  // Cart page
  if (document.getElementById('cartContainer')) {
    initCartPage();
  }

  // Contact page
  if (document.getElementById('contactForm')) {
    initContactForm();
  }
});