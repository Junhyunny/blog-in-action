let backdrop = document.querySelector(".backdrop");
let modal = document.querySelector(".modal");
let modalNoButton = document.querySelector(".modal__action--negative");
let selectPlanButtons = document.querySelectorAll(".plan button");
let toggleButton = document.querySelector(".toggle-button");
let mobileNav = document.querySelector(".mobile-nav");

// console.dir(backdrop.style['background-image']);

// console.dir(backdrop);
for (let i = 0; i < selectPlanButtons.length; i++) {
    selectPlanButtons[i].addEventListener("click", function () {
        // modal.style.display = "block";
        // backdrop.style.display = "block";
        // modal.className = 'open'; // This will actually overwrite the complete class list
        modal.style.display = 'block';
        backdrop.style.display = 'block';
        setTimeout(() => {
            modal.classList.add("open");
            backdrop.classList.add("open");
        }, 200);
    });
}

backdrop.addEventListener("click", function () {
    mobileNav.classList.remove("open");
    mobileNav.classList.remove("open-mobile-nav");
    setTimeout(() => {
        mobileNav.style.display = 'none';
    }, 600);
    closeModal();
});

if (modalNoButton) {
    modalNoButton.addEventListener("click", closeModal);
}

function closeModal() {
    //   backdrop.style.display = "none";
    //   modal.style.display = "none";
    if (modal) {
        modal.classList.remove("open");
        setTimeout(() => {
            modal.style.display = 'none';
        }, 200);
    }
    backdrop.classList.remove("open");
    setTimeout(() => {
        backdrop.style.display = 'none';
    }, 200);
}

toggleButton.addEventListener("click", function () {
    // mobileNav.style.display = 'block';
    // backdrop.style.display = 'block';
    mobileNav.style.display = 'block';
    backdrop.style.display = 'block';
    setTimeout(() => {
        mobileNav.classList.add("open");
        mobileNav.classList.add("open-mobile-nav");
        backdrop.classList.add("open");
    }, 200);
});
