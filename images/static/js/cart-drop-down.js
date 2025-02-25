// Thêm sản phẩm vào giỏ hàng
function addToCart(productName, price, image) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let product = cart.find(item => item.name === productName);

  if (product) {
      product.quantity += 1; // Nếu sản phẩm đã có trong giỏ, tăng số lượng
  } else {
      cart.push({ name: productName, price: price, quantity: 1, image: image }); // Nếu sản phẩm chưa có, thêm sản phẩm mới
  }

  localStorage.setItem("cart", JSON.stringify(cart)); // Lưu giỏ hàng vào Local Storage
  updateCart(); // Cập nhật giỏ hàng hiển thị
  updateCartCount(); // Cập nhật số lượng hiển thị trên biểu tượng giỏ hàng
}

// Cập nhật giỏ hàng hiển thị trong dropdown
function updateCart() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let cartItems = document.getElementById("cart-items");
  let cartTotal = document.getElementById("cart-total");
  cartItems.innerHTML = "";
  let total = 0;

  cart.forEach(item => {
      total += item.price * item.quantity;
      cartItems.innerHTML += `
          <div class="cart-item">
              <img src="${item.image}" alt="${item.name}">
              <div class="item-info">
                  <p class="product-name">${item.name}</p>
                  <span class="product-count">x${item.quantity}</span>
                  <p class="item-price">${item.price.toLocaleString("vi-VN")} VND</p>
              </div>
              <div class="square-del">
                  <button onclick="removeFromCart('${item.name}')">X</button>
              </div>
          </div>
      `;
  });

  cartTotal.innerHTML = `<strong>Tổng cộng:</strong> ${total.toLocaleString("vi-VN")} VND`;
}

// Xóa sản phẩm khỏi giỏ hàng
function removeFromCart(productName) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart = cart.filter(item => item.name !== productName);

  localStorage.setItem("cart", JSON.stringify(cart));
  updateCart(); // Cập nhật lại giỏ hàng hiển thị
  updateCartCount(); // Cập nhật lại số lượng hiển thị trên biểu tượng giỏ hàng
}

// Hiển thị hoặc ẩn giỏ hàng dropdown
function toggleCartDropdown() {
  let cartDropdown = document.getElementById("cart-dropdown");
  cartDropdown.style.display =
      cartDropdown.style.display === "none" || cartDropdown.style.display === "" ? "block" : "none";
}

// Cập nhật số lượng hiển thị trên biểu tượng giỏ hàng
function updateCartCount() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let totalQuantity = cart.reduce((total, item) => total + item.quantity, 0);
  document.querySelector(".mini-cart-count").innerText = totalQuantity;
}

// Khi trang tải xong, cập nhật giỏ hàng và số lượng hiển thị
document.addEventListener("DOMContentLoaded", function () {
    updateCart();
    updateCartCount();
    
    const cartTable = document.getElementById("cart-table");
    if (cartTable) {
        cartTable.addEventListener("click", function (event) {
            if (event.target.classList.contains("increase-qty")) {
                updateQuantity(event.target, 1);
            } else if (event.target.classList.contains("decrease-qty")) {
                updateQuantity(event.target, -1);
            }
        });
    }
  
    function updateQuantity(button, delta) {
        const qtyInput = button.closest(".quantity-controls").querySelector(".qty-input");
        let qty = parseInt(qtyInput.value);
        qty += delta;
        if (qty < 1) qty = 1;
        qtyInput.value = qty;
  
        const row = button.closest("tr");
        const productName = row.querySelector(".product-name").innerText;
  
        // Cập nhật số lượng sản phẩm trong Local Storage
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        let product = cart.find(item => item.name === productName);
        if (product) {
            product.quantity = qty;
            localStorage.setItem("cart", JSON.stringify(cart));
        }
  
        updateTotals(row, qty);
    }
  
    function updateTotals(row, qty) {
        const unitPrice = parseFloat(row.querySelector("td:nth-child(4)").innerText.replace(/[^\d.]/g, ""));
        const productTotal = row.querySelector(".product-total");
        productTotal.innerText = (unitPrice * qty).toLocaleString("vi-VN") + " VND";
  
        updateCartTotal();
    }
  
    function updateCartTotal() {
        const productTotals = document.querySelectorAll(".product-total");
        let subTotal = 0;
  
        productTotals.forEach(function (productTotal) {
            subTotal += parseFloat(productTotal.innerText.replace(/[^\d.]/g, ""));
        });
  
        document.getElementById("sub-total").innerText = subTotal.toLocaleString("vi-VN") + " VND";
        document.getElementById("total").innerText = subTotal.toLocaleString("vi-VN") + " VND";
    }
  });

  
