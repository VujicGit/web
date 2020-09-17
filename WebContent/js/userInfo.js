checkIfUserLoggedIn();
$(document).ready(function() {
    getLoggedInUser();
    changeUserInfo();
}); 


function getLoggedInUser() {
    $.ajax({
        type: "GET",
        url: "rest/login/user",
        success: function(user) {
        
            $("#username").val(user.username);
            $("#name").val(user.name);
            $("#surname").val(user.surname)
            $("#gender option:selected").val(user.gender.toUpperCase());
        }
    })
}


function changeUserInfo() {
    
    $("form[name=changeUserInfoForm]").submit(function(event){
        event.preventDefault();

        if(checkSignUpForm() !== true) {
            return;
        }
        formData = {
            username: $("#username").val(),
            password: $("#password").val(),
            name: $("#name").val(),
            surname: $("#surname").val(),
            gender: ($("#gender option:selected").text()).toUpperCase()

        };

        $.ajax({
            type: "POST",
            url: "rest/users/changeInfo",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (data, textStatus, XMLHttpRequest) {
            

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                window.location.href = obj.href;
                
            }
        })
    });
}