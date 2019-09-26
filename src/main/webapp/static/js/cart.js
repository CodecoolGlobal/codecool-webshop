function addToCartApi(productId, quantity) {
    console.log(productId);

    let data = {
        "product_id": productId
    };

    api_post("/cart-add", data, function() {
        raiseQuantity(quantity)
    });

}

function removeFromCartApi(productId, quantity) {
    let data = {
        "product_id": productId
    };

    api_post("/cart-remove", data, function() {
        decreaseQuantity(quantity)
    });
}

function raiseQuantity(quantity) {
    quantity.textContent++;
}

function decreaseQuantity(quantity) {
    quantity.textContent--;
}


function addToCart(addButton, quantity) {
    let productId = addButton.dataset.productId;
    addToCartApi(productId, quantity);
}

function removeFromCart(removeButton, quantity) {
    let productId = removeButton.dataset.productId;
    removeFromCartApi(productId, quantity)
}


function addListenersToCartButtons() {
    const quantityModifiers = document.querySelectorAll(".quantity-modifier");

    for (let modifier of quantityModifiers) {
        let removeButton = modifier.querySelector(".remove-from-cart");
        let quantity = modifier.querySelector(".quantity");
        let addButton = modifier.querySelector(".add-to-cart");

        addButton.addEventListener("click", function() {
            addToCart(addButton, quantity)
        });

        removeButton.addEventListener("click", function() {
            removeFromCart(removeButton, quantity);
        })
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