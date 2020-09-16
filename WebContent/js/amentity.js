$(document).ready(function () {

    addAmentity();

});

function addAmentity() {
    var addAmentityInput = $("#addAmentityInput");
    $("form[name=addAmentityForm]").submit(function (event) {
        alert("dadada");
        event.preventDefault();
        formData = {
            name: $("#addAmentityInput").val()
        };
        $.ajax({
            type: "POST",
            url: "rest/amenities/add",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (data, textStatus, XMLHttpRequest) {

                alert("Amentity created succesfull");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                alert(obj.errorMessage);
            }


        });
    });
}