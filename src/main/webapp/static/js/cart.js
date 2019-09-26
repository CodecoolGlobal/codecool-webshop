function updatePrices() {

    const products = document.querySelectorAll(".product");
    const totalPriceOfCartElement = document.querySelector("#total-price");

    let newTotalPrice = 0;

    for (let product of products) {

        let productPrice = parseFloat(product.querySelector(".product-price").textContent);

        let productQuantity = parseFloat(product.querySelector(".quantity").textContent);

        let newSubtotal = productPrice * productQuantity;

        let productSubtotalElement = product.querySelector(".product-subtotal");
        productSubtotalElement.textContent = newSubtotal.toFixed(2).toString();

        newTotalPrice += newSubtotal;

    }

    totalPriceOfCartElement.textContent = `${newTotalPrice.toFixed(2).toString()} (USD)`;
    sendTotalPriceToPaymentController(newTotalPrice)
}

function sendTotalPriceToPaymentController(totalPrice) {

    let data = {
        "total_price": totalPrice
    };

    api_post("/payment", data, function() {
        console.log("works!");
    });
}


function addToCartApi(productId, quantity) {

    let data = {
        "product_id": productId
    };

    api_post("/cart-add", data, function() {
        raiseQuantity(quantity);
        updatePrices();
    });

}


function removeFromCartApi(productId, quantity) {
    let data = {
        "product_id": productId
    };

    api_post("/cart-remove", data, function() {
        decreaseQuantity(quantity);
        updatePrices();
    });
}


function removeAllFromCartApi(productId, product, quantity) {
    let data = {
        "product_id": productId
    };

    api_post("/cart-remove-all", data, function() {
        quantity.textContent = 0;
        product.parentNode.removeChild(product);
        updatePrices();
    });
}


function raiseQuantity(quantity) {
    quantity.textContent++;
}


function decreaseQuantity(quantity) {
    if (quantity.textContent > 0) quantity.textContent--;
}


function addToCart(addButton, quantity) {
    let productId = addButton.dataset.productId;
    addToCartApi(productId, quantity);
}


function removeFromCart(removeButton, quantity) {
    let productId = removeButton.dataset.productId;
    removeFromCartApi(productId, quantity)
}


function removeAllFromCart(removeAllButton, product, quantity) {
    let productId = removeAllButton.dataset.productId;
    removeAllFromCartApi(productId, product, quantity)
}


function addListenersToCartButtons() {

    updatePrices();

    const checkoutButton = document.querySelector("#checkout .btn-success");
    checkoutButton.addEventListener("click", updatePrices);

    const products = document.querySelectorAll(".product");

    for (let product of products) {
        let quantityModifier = product.querySelector(".quantity-modifier");
        let currentQuantity = quantityModifier.querySelector(".quantity");

        let removeButton = quantityModifier.querySelector(".remove-from-cart");
        let addButton = quantityModifier.querySelector(".add-to-cart");
        let removeAllButton = quantityModifier.querySelector(".remove-all-from-cart");

        addButton.addEventListener("click", function() {
            addToCart(addButton, currentQuantity)
        });

        removeButton.addEventListener("click", function() {
            removeFromCart(removeButton, currentQuantity);
        });

        removeAllButton.addEventListener("click", function() {
            removeAllFromCart(removeAllButton, product, currentQuantity)
        });
    }
}


window.addEventListener("load", addListenersToCartButtons);


function api_get(url, callback) {
    // it is not called from outside
    // loads data from API, parses it and calls the callback with it

    fetch(url, {
        method: 'GET',
        credentials: 'same-origin'
    })
        .then(response => response.json())  // parse the response as JSON
        .then(json_response => callback(json_response));  // Call the `callback` with the returned object
}


function api_post(url, data, callback) {
    // it is not called from outside
    // sends the data to the API, and calls callback function

    fetch(url, {
        method: 'POST',
        credentials: "same-origin",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(response => response.json())  // parse the response as JSON
        .then(callback);  // Call the `callback` with the returned object
}