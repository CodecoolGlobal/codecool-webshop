function handleClick(productId) {

    let data = {
        "product_id": productId
    };

    api_post("/cart-add", data, changeCartSize);

}

function changeCartSize() {
    let cartSize = document.querySelector("#cart-size");
    cartSize.textContent++;
}

function handleFormData(button) {
    let productId = button.dataset.productId;

    button.addEventListener("click", function () {
        handleClick(productId);
    })
}


function addListenersToCartButtons() {
    const products = document.querySelectorAll(".product-card");

    for (let product of products) {
        let button = product.querySelector(".add-to-cart");
        handleFormData(button);
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