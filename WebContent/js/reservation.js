
$(document).ready(function () {
	var apartmentsCol = $("#apartmentsCol");

    $.ajax({
        type: "GET",
        url: "rest/reservations/",
        success: function (reservations) {

            for (let reservation of reservations) {
                apartmentsCol.append('<div class="card card-custom" style="width: 50rem; margin-top: 100px;">' +
                    '<div class="row no-gutters" style="border-radius: 25px;">' +
                    '<div class="col-sm-5" style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"style="background: #868e96;">' +
                    '<img src="proba/1.jpg"style="border-top-left-radius: 25px; border-bottom-left-radius: 25px;"class="card-img-top h-100" alt="..."> ' +
                    '</div>' +
                    '<div class="col-sm-7">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title">' + reservation.status + '</h5>' +
                    '<p class="card-text">' + reservation.price + '</p>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<a href="rest/reservations/' + reservation.id + '"' + 'class="stretched-link"></a>' +
                    '</div>')


            }

        }



    });
});