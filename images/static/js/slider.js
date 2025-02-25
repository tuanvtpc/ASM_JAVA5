let slideIndex = 0;
let autoSlideInterval = null;

showSlide(slideIndex);

function changeSlide(n) {
  stopSlideShow();
  showSlide((slideIndex += n));
  startSlideShow();
}

function currentSlide(n) {
  stopSlideShow();
  showSlide((slideIndex = n - 1));
  startSlideShow();
}

function showSlide(n) {
  let sliders = document.getElementsByClassName("slides")[0].children;
  let dots = document.getElementsByClassName("dot");

  for (let i = 0; i < sliders.length; i++) {
    sliders[i].style.display = "none";
  }

  for (let i = 0; i < dots.length; i++) {
    dots[i].classList.remove("active");
  }

  if (n >= sliders.length) {
    slideIndex = 0;
  } else if (n < 0) {
    slideIndex = sliders.length - 1;
  }

  sliders[slideIndex].style.display = "block";
  dots[slideIndex].classList.add("active");
}

function startSlideShow() {
  if (autoSlideInterval == null) {
    autoSlideInterval = setInterval(function () {
      changeSlide(1);
    }, 3000);
  }
}

function stopSlideShow() {
  clearInterval(autoSlideInterval);
  autoSlideInterval = null;
}

startSlideShow();

let currentLogo = 0;
const logoContainers = document.querySelectorAll(".logo-customers");
const totalLogos = logoContainers.length;

// Ẩn tất cả các logo ngoại trừ logo đầu tiên
function initializeLogos() {
  logoContainers.forEach((logo, index) => {
    if (index === currentLogo) {
      logo.style.display = "flex"; // Hiển thị logo đầu tiên
    } else {
      logo.style.display = "none"; // Ẩn các logo khác
    }
  });
}

function changeLogo() {
  // Ẩn logo hiện tại
  if (currentLogo < totalLogos) {
    logoContainers[currentLogo].style.display = "none";
  }

  // Chuyển sang logo tiếp theo
  currentLogo = currentLogo + 1;

  // Nếu đã đến logo cuối cùng, quay lại logo đầu tiên
  if (currentLogo >= totalLogos) {
    currentLogo = 0;
  }

  // Hiển thị logo mới
  logoContainers[currentLogo].style.display = "flex";
}

// Khởi tạo các logo khi trang được tải
initializeLogos();

// Thay đổi logo sau mỗi 3 giây
setInterval(changeLogo, 3000);
