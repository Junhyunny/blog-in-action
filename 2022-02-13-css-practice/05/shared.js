const body = document.body;
const backdrop = document.querySelector(".backdrop");
const modal = document.querySelector('.modal');
const yesButtonInModal = document.querySelector('a.modal__action');
const noButtonInModal = document.querySelector('.modal__action--negative');
const buttons = document.querySelectorAll('.plan button');

if (buttons && buttons.length > 0) {

    const openModal = () => {
        // backdrop.className = 'open'; // this will actually overwrite the complete class
        body.classList.add('prevent-scroll');
        backdrop.classList.add('open');
        modal.classList.add('open');
    };
    const closeModal = () => {
        body.classList.remove('prevent-scroll');
        backdrop.classList.remove('open');
        modal.classList.remove('open');
    };

    buttons.forEach(button => {
        button.addEventListener('click', openModal);
    });
    yesButtonInModal.addEventListener('click', closeModal);
    noButtonInModal.addEventListener('click', closeModal);
    backdrop.addEventListener('click', closeModal);
}

// 햄버거 메뉴
const toggleButton = document.querySelector('.toggle-button');
const mobileNav = document.querySelector('.mobile-nav');

const openMobileNav = () => {
    body.classList.add('prevent-scroll');
    backdrop.classList.add('open');
    mobileNav.classList.add('open');
};

const closeMobileNav = () => {
    body.classList.remove('prevent-scroll');
    backdrop.classList.remove('open');
    mobileNav.classList.remove('open');
};

toggleButton.addEventListener('click', openMobileNav);
backdrop.addEventListener('click', closeMobileNav);
