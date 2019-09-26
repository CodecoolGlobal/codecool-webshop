function handleClick(productId) {
    // add product to cart in ajax
    console.log(productId);
}

function handleFormData(buttonForm) {
    let productId = buttonForm.querySelector("input").value;
    let addToCartButton = buttonForm.querySelector(".btn-success");

    addToCartButton.addEventListener("click", function() {
        handleClick(productId);
    })
}


function getButtonForm(product) {
    return product.querySelector(".add-to-cart-form");
}


function addListenersToCartButtons() {
    const products = document.querySelectorAll(".product-card");

    for (let product of products) {
        let buttonForm = getButtonForm(product);
        handleFormData(buttonForm);
    }
}

window.addEventListener("load", addListenersToCartButtons);