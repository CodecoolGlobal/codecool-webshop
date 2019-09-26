let radioButtons = document.querySelectorAll('.form-check-input');

for (let radioButton of radioButtons) {
    radioButton.addEventListener("click", function () {
        if (radioButton.id === "paypal" && radioButton.checked === true) {
            document.querySelector('#paypal-payment-holder').classList.remove('paypal-payment-holder-hide');
            document.querySelector('#card-payment-holder').classList.add('card-payment-holder-hide');
        } else if (radioButton.id === "card" && radioButton.checked === true) {
            document.querySelector('#paypal-payment-holder').classList.add('paypal-payment-holder-hide');
            document.querySelector('#card-payment-holder').classList.remove('card-payment-holder-hide');
        }
    });
}
