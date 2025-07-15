function validatePasswords() {
    var password = document.getElementById("password").value;
    var rePassword = document.getElementById("rePassword").value;
    document.getElementById("errorMessage").innerText = "Пароли не совпадают"

    if (password !== rePassword) {
        document.getElementById("errorMessage").innerText = "Пароли не совпадают"
        return false
    }
    document.getElementById("errorMessage").innerText = ""
    return true
}
