isAdminLoggedIn();
$(document).ready(function () {



    addAmentity();
    getAllAmenities();
    updateAmenity();

});

function getAllAmenities() {
    $.ajax({
        type: "GET",
        url: "rest/amenities/getAll",
        success: function (amenities) {
            createTable(amenities);
        }
    })
}

function updateAmenity() {
    $("#updateAmenityForm").submit(function (event) {
        event.preventDefault();

        formData = {
            id: $("#amenityId").val(),
            name: $("#updateAmenityName").val()
        }

        $.ajax({
            type: "POST",
            url: "rest/amenities/update",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (data, textStatus, XMLHttpRequest) {
                $("#updateAmenityerror").text("");
                alert("Amentity updated successfully");
                $("#updateAmenityModal").modal('toggle');
                getAllAmenities();

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                $("#updateAmenityerror").val("ddadsdsa");

            }
        });
    })


}



function createTable(amenities) {
    var tableBody = $("#amenitiesTableBody");
    tableBody.empty();
    $.each(amenities, function (index, value) {
        tableBody.append('<tr>' +
            '<th scope="row">' + (index + 1) + '</th>' +
            '<td scope="col">' + value.name + '</td>' +
            '<td scope="col">' +
            '<button type="button" class="btn btn-outline-danger deleteAmentity" value="' + value.id + '">Remove</button>' +
            '</td>' +
            '<td scope="col">' +
            '<button type="button" class="btn btn-outline-secondary changeAmenity" data-toggle="modal" data-target="#updateAmenityModal" value="' + value.id + '">Change</button>' +
            '</td>' +
            '</tr>'
        )
    });

    $("#tabela tr td button.deleteAmentity").click(function () {
        $.ajax({
            type: "POST",
            url: "rest/amenities/remove/" + ($("#tabela tr td button.btn").attr("value")).toString(),
            success: function (amenities) {
                getAllAmenities();
            }
        })
    });

    $("#tabela tr td button.changeAmenity").click(function () {
        $("#amenityId").val($(this).attr("value"));
    });




}

function addAmentity() {
    var addAmentityInput = $("#addAmentityInput");
    $("form[name=addAmentityForm]").submit(function (event) {
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
                getAllAmenities();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                alert(obj.errorMessage);
            }


        });
    });
}