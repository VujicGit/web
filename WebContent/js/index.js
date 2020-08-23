$(document).ready(function () {
    $('.input-daterange').datepicker({});

    let form = $("#form");
    let btn = $("#btnSubmit");


    $.ajax({
        type: "GET",
        url: "rest/apartments/",
        success: function (data) {

            for (let product of data) {
                alert(product.location.address.street + ", " + product.location.address.number + ", " + product.location.address.place);
            }

        }



    });
});