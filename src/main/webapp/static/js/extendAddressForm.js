const extendAddressFormButton = document.querySelector('#different-shipping-address');
const excludeAddressFormButton = document.querySelector('#same-shipping-address');
const placeHolderDiv = document.querySelector('#extended-address-input-placeholder');


extendAddressFormButton.addEventListener('click', function() {

    let extendedForm = `
                    <h3>Shipping Address:</h3>
    
                    <label for="buyer-shipping-country-input">Country:</label><br>
                    <input id="buyer-shipping-country-input" type="text" name="buyer-shipping-country" required minlength="1"><br>
    
                    <label for="buyer-shipping-city-input">City:</label><br>
                    <input id="buyer-shipping-city-input" type="text" name="buyer-shipping-city" required minlength="1"><br>
    
                    <label for="buyer-shipping-zip-input">Zip code:</label><br>
                    <input id="buyer-shipping-zip-input" type="text" name="buyer-shipping-zip" required minlength="4"><br>
    
                    <label for="buyer-shipping-address-input">Address:</label><br>
                    <input id="buyer-shipping-address-input" type="text" name="buyer-shipping-address" required minlength="1"><br>
        `;

    placeHolderDiv.innerHTML = extendedForm;
    placeHolderDiv.style.display = "inline-block";
});

excludeAddressFormButton.addEventListener('click', function() {
    placeHolderDiv.innerHTML = ``;
    placeHolderDiv.style.display = "none";
});
