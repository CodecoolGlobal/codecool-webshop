const spinner = document.querySelector("#spinner");
spinner.addEventListener("click", function(){
    setTimeout(3000);
    spinner.classList.add("invisible")
});