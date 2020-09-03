$(document).ready(function () {
    $('.input-daterange').datepicker({});

    let btn = $("#btnSubmit");

    var apartmentsCol = $("#apartmentsCol");

    $.ajax({
        type: "GET",
        url: "rest/apartments/",
        success: function (apartments) {

            for (let apartment of apartments) {
                apartmentsCol.append('<div class="card card-custom" style="width: 50rem; margin-top: 100px;">' +
                    '<div class="row no-gutters" style="border-radius: 25px;">' +
                    '<div class="col-sm-5" style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"style="background: #868e96;">' +
                    '<img src="proba/1.jpg"style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"class="card-img-top h-100" alt="..."> ' +
                    '</div>' +
                    '<div class="col-sm-7">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title">' + apartment.location.address.place + '</h5>' +
                    '<p class="card-text">' + apartment.price + '</p>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<a href="rest/apartments/' + apartment.id + '"' + 'class="stretched-link"></a>' +
                    '</div>')


            }

        }

    });


    let form = $("form[name=searchForm]");
    form.submit(function (event) {
        event.preventDefault();
        searchData = {
            location: $("input[name=place]").val(),
            checkinDate: new Date($("input[name=startDate]").val()),
            checkoutDate: new Date($("input[name=endDate]").val())
        }
        alert(searchData.checkinDate);
        $.ajax({
            type: "POST",
            url: "rest/apartments/search",
            contentType: "application/json",
            data: JSON.stringify(searchData),
            success: function (apartments) {
                apartmentsCol.empty();
                for (let apartment of apartments) {
                    apartmentsCol.append('<div class="card card-custom" style="width: 50rem; margin-top: 100px;">' +
                        '<div class="row no-gutters" style="border-radius: 25px;">' +
                        '<div class="col-sm-5" style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"style="background: #868e96;">' +
                        '<img src="proba/1.jpg"style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"class="card-img-top h-100" alt="..."> ' +
                        '</div>' +
                        '<div class="col-sm-7">' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + apartment.location.address.place + '</h5>' +
                        '<p class="card-text">' + apartment.price + '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<a href="rest/apartments/' + apartment.id + '"' + 'class="stretched-link"></a>' +
                        '</div>')


                }
            }

        });
    });

});