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
                location.href = XMLHttpRequest.responseText;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                incorrectUserPassMessage(XMLHttpRequest.responseText);
            }


        });

    });
});

function incorrectUserPassMessage(message) {
    let loginUsername = $("#loginUsername");
    let loginPassword = $("#loginPassword");
    let errorMessage = $("#loginErrorMessage");

    loginUsername.css("border", "1px solid red");
    loginPassword.css("border", "1px solid red");
    errorMessage.text(message);
    errorMessage.show();
}