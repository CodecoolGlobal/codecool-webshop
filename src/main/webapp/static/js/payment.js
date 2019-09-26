const radioButtons = document.querySelectorAll('.form-check-input');

const paypalHolder = document.querySelector('#paypal-payment-holder');
const cardHolder = document.querySelector('#card-payment-holder');

const paypalInputFields = paypalHolder.querySelectorAll("input");
const cardInputFields = cardHolder.querySelectorAll("input");

for (let radioButton of radioButtons) {
    radioButton.addEventListener("click", function () {
        if (radioButton.id === "paypal" && radioButton.checked === true) {
            for (let input of cardInputFields){
                input.required = false;
            }
            paypalHolder.classList.remove('paypal-payment-holder-hide');
            cardHolder.classList.add('card-payment-holder-hide');
        } else if (radioButton.id === "card" && radioButton.checked === true) {
            for (let input of paypalInputFields){
                input.required = false;
            }
            paypalHolder.classList.add('paypal-payment-holder-hide');
            cardHolder.classList.remove('card-payment-holder-hide');
        }
    });
}

