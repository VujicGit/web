$(document).ready(function () {

    let loginForm = $("#loginForm");
    loginForm.submit(function (event) {
        event.preventDefault();

        loginData = {
            username: $("#loginUsername").val(),
            password: $("#loginPassword").val()
        }

        $.ajax({
            type: "POST",
            url: "rest/login/",
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function (data, textStatus, XMLHttpRequest) {
                createLogoutButton();
                if (window.location.href.includes("apartmentPage.html") != true) {
                    location.href = XMLHttpRequest.responseText;
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                incorrectUserPassMessage(XMLHttpRequest.responseText);
            }


        });

    });


});



function createLogoutButton() {
    let navbarUl = $("#navbarUl");

    navbarUl.empty();
    navbarUl.append('<li class=" nav-item nav-item-custom" id="logoutButtonLi">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" id="logoutButton">Logout</button>' +
        '</li>');

}

function createLoginRegisterButtons() {
    let navbarUl = $("#navbarUl");
    navbarUl.empty();
    navbarUl.append('<li class="nav-item nav-item-custom" id="registerButtonLi">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" data-toggle="modal" data-target="#LoginModal" id="loginButton">Login</button>' +
        '</li>');
    navbarUl.append('<li class=" nav-item nav-item-custom" id="loginButtonLI">' +
        '<button type="button" class="btn btn-primary navbar-btn-custom" data-toggle="modal data-target="#LoginModal" id="loginButton">Login</button>"' +
        '</li>'
    );
}

function incorrectUserPassMessage(message) {
    let loginUsername = $("#loginUsername");
    let loginPassword = $("#loginPassword");
    let errorMessage = $("#loginErrorMessage");

    loginUsername.css("border", "1px solid red");
    loginPassword.css("border", "1px solid red");
    errorMessage.text(message);
    errorMessage.show();
}