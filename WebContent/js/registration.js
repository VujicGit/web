$(document).ready(function () {

    var registerForm = $("form[name=registerForm]");

    registerForm.submit(function (event) {
        event.preventDefault();
        alert("dadada");
        registerData = {
            username: $("#username").val(),
            password: $("#password").val(),
            name: $("#name").val(),
            surname: $("#surname").val(),
            gender: ($("#gender option:selected").text()).toUpperCase(),
            role: "GUEST"
        }
        $.ajax({
            type: "POST",
            url: "rest/register/",
            contentType: "application/json",
            data: JSON.stringify(registerData),
            complete: function (data) {
                alert(data.responseText);

            }
        });
    });

    $("#btnLogin").click(function () {
        $.ajax({
            type: "GET",
            url: "rest/register/testlogin",
            complete: function (data) {
                alert(data.responseText);

            }
        });
    });
});