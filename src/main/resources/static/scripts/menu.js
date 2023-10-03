document.addEventListener("DOMContentLoaded", function() {
    let buttons = document.querySelectorAll(".dish_plus_button_long");
    for (btn of buttons) {
        btn.onclick = plusDish4LongBtn;
    }
});

function plusDish4LongBtn() {
    console.log(`Добавил блюдо с id=${this.closest(".dish_card").getAttribute('id')} в корзину`);

    let buttonsBlock = this.closest(".dish_card_buttons_block");
    buttonsBlock.innerHTML = 
`<button class="dish_minus_button_short"> - </button>
<p class="dish_card_count"> 0 </p>
<button class="dish_plus_button_short"> + </button>
`;

    let btnMinus = buttonsBlock.querySelector(".dish_minus_button_short");
    let btnPlus = buttonsBlock.querySelector(".dish_plus_button_short");
    let dishCount = buttonsBlock.querySelector(".dish_card_count");

    btnMinus.onclick = minusDish4ShortBtn;
    btnPlus.onclick = plusDish4ShortBtn;
    dishCount.textContent = "1";
}

function plusDish4ShortBtn() {
    let dishCard = this.closest(".dish_card");
    console.log(`id=${dishCard.getAttribute('id')} + 1`);
    let dishCount = dishCard.querySelector(".dish_card_count");
    dishCount.textContent =  Number(dishCount.textContent) + 1;
}

function minusDish4ShortBtn() {
    let dishCard = this.closest(".dish_card");
    console.log(`id=${dishCard.getAttribute('id')} - 1`);
    let dishCount = dishCard.querySelector(".dish_card_count");
    dishCount.textContent = Number(dishCount.textContent) - 1;
    if (Number(dishCount.textContent) === 0) {
        let buttonsBlock = dishCard.querySelector(".dish_card_buttons_block");
        buttonsBlock.innerHTML = `<button class="dish_plus_button_long">+ добавить</button>`;
        let btnPlusLong = buttonsBlock.querySelector(".dish_plus_button_long");
        btnPlusLong.onclick = plusDish4LongBtn;
    }
}