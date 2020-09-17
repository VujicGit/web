$(document).ready(function () {
    var date = new Date();


    getApartment();
    getDatesForIssue();
    reserveApartment();
    isUserLoggedIn();
    confirmReservation();
});


function reserveApartment() {

    let form = $("form[name=reserveForm]");
    form.submit(function (event) {
        event.preventDefault();
        if(checkNightsInput($("#nights").val()) !== true) {
            return;
        }

        $.ajax({
            type: "GET",
            url: "rest/login/reservation/guest",
            success: function (data, textStatus, XMLHttpRequest) {
                openConfirmReservationModal();
                fillConfirmReservationForm();
                $("#noGuestLoggedInError").text("");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                $("#noGuestLoggedInError").text(obj.errorMessage);
            
            }

        })
    });
}

function confirmReservation() {
    $("form[name=confirmReservationForm]").submit(function (event) {
        event.preventDefault();


        formData = {
            id: getUrlParameter("id"),
            startDate: new Date($("#confirmReservationCheckInDate").val()),
            nights: $("#confirmReservationCheckoutDate").val(),
            price: 0,
            message: $("#message").val()
        };
        $.ajax({
            type: "POST",
            url: "rest/apartments/submitReservation",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (data, textStatus, XMLHttpRequest) {
                alert("Reservation created successfull");
                getDatesForIssue();
                $('.date').datepicker('update', '');

            }
        });
    })
}


function fillConfirmReservationForm() {
    var checkInDate = $("#checkInDateInput").val();
    var numberOfNights = $("#nights").val();
    $.ajax({
        type: "GET",
        url: "rest/apartments/getApartment/" + getUrlParameter("id"),
        success: function (apartment) {
            var totalPrice = apartment.price * parseInt(numberOfNights);
            fillReservationModal(checkInDate, numberOfNights, apartment.checkInTime, apartment.checkoutTime, totalPrice);
        }
    });
}

function fillReservationModal(checkInDate, checkOutDate, checkInTime, checkOutTime, totalPrice) {
    $("#confirmReservationCheckInDate").val(checkInDate);
    $("#confirmReservationCheckoutDate").val(checkOutDate);
    $("#confirmReservationCheckInTime").val(checkInTime);
    $("#confirmReservationCheckoutTime").val(checkOutTime);
    $("#confirmReservationTotalPrice").val(totalPrice);
}

function openConfirmReservationModal() {
    $("#confirmReservationModal").modal('toggle');
}

function highlightDates(availableDates) {
    var date = new Date();
    $('.date').datepicker({
        startDate: date,
        beforeShowDay: function (date) {

            var calenderDate = ("0" + (date.getMonth() + 1)).slice(-2) + "/" + ('0' + date.getDate()).slice(-2) + "/" + date.getFullYear();
            var search_index = $.inArray(calenderDate, availableDates);

            if (search_index > -1) {
                return {
                    classes: 'highlighted-cal-dates',
                    tooltip: 'This date is available'
                };
            }
            if (search_index === -1) {
                return false;
            }

        }
    });

}

function getDatesForIssue() {
    var dates = [];
    $.ajax({
        type: "GET",
        url: "rest/apartments/datesForIssue/" + getUrlParameter("id"),
        success: function (data) {
            dates = formatDates(data);
            highlightDates(dates);
        }
    });


}

function formatDate(date) {
    return ("0" + (dateToFormat.getMonth() + 1)).slice(-2) + "/" + ('0' + dateToFormat.getDate()).slice(-2) + "/" + dateToFormat.getUTCFullYear();
}

function formatDates(dates) {
    var formatedDates = [];
    for (let date of dates) {
        let dateToFormat = new Date(date);
        let formatedDate = ("0" + (dateToFormat.getMonth() + 1)).slice(-2) + "/" + ('0' + dateToFormat.getDate()).slice(-2) + "/" + dateToFormat.getUTCFullYear();
        formatedDates.push(formatedDate);
    }

    return formatedDates;
}

function getApartment() {
    $.ajax({
        type: "GET",
        url: "rest/apartments/getApartment/" + getUrlParameter("id"),
        success: function (apartment) {
            fillReservationForm();
            fillContent(apartment);
        }
    });
}

function fillContent(apartment) {
    $.each(apartment.images, function(index, value) {
        $(".carousel-indicators").append(
            '<li data-target="#carouselExampleIndicators" data-slide-to="' + index + '"></li>'
        );
        if(index === 0) {
            $(".carousel-inner").append(
                '<div class="carousel-item active">' +
                '<img class="d-block w-100" src="' + value + '">' + 
                '</div>'
            );
        }
        else {
        $(".carousel-inner").append(
                '<div class="carousel-item">' +
                '<img class="d-block w-100" src="' + value + '">' +
                '</div>'
        );
        }
        

    });

    var tableBody = $("#amenitiesTableBody");
    tableBody.empty();
    $.each(apartment.amenities, function(index, value){
        tableBody.append('<tr>' +
            '<th scope="row">' + (index + 1) + '</th>' +
            '<td scope="col">' + value.name + '</td>' +
            '</tr>'
        )
    });

    $("#apartmentPlace").text(apartment.location.address.place + ", " + apartment.location.address.zipCode);
    $("#apartmentAddress").text(apartment.location.address.street + " " + apartment.location.address.number);
    $("#apartmentCoordinates").text(apartment.location.latitude + ", " + apartment.location.longitude);
    $("#priceInput").val(apartment.price);
    $.each(apartment.comments, function(index, value){
        $("#comments").append(
            '<div class="row" style="margin-left: 10vw; margin-top: 20px;">' +
            '<div class="col-10">' +
            '<div class="card">' +
            '<div class="card-body">' +
            '<h5 class="card-title">' + value.guest.name + " " + value.guest.surname + '</h5>' +
            '<p class="card-text">' + value.content + '</p>' + 
            '</div>' + 
            '</div>' + 
            '</div>' +
            '</div>'
        );
    });
        
    
    
}

function fillReservationForm() {
    let checkInDateInput = $("#checkInDateInput");

    let checkinDate = getUrlParameter("checkInDate");
    let checkoutDate = getUrlParameter("checkoutDate");
    if (checkinDate === "" && checkoutDate === "") {
        return;
    }

    checkInDateInput.val(checkinDate);

    let days = calculateDays(new Date(checkinDate), new Date(checkoutDate));
    let nights = days;

    let nightsInput = $("#nights");
    nightsInput.val(nights);
    

}

function calculateDays(startDate, endDate) {
    let differenceInTime = endDate.getTime() - startDate.getTime();
    return differenceInTime / (1000 * 3600 * 24);
}

function getUrlParameter(param) {
    var pageUrl = window.location.search.substring(1);
    var urlVariables = pageUrl.split('&');
    for (let i = 0; i < urlVariables.length; i++) {
        var parameterName = urlVariables[i].split("=");
        if (parameterName[0] === param) {
            return parameterName[1];
        }
    }
}

function checkNightsInput(nights) {
    var regex = new RegExp('^\\d+$');
    if (regex.test(nights)) {
        let nightsInput = $("#nights");
        nightsInput.css('border', '0');
        return true;
    }
    let nightsInput = $("#nights");
    nightsInput.css('border', '1px solid red');
    return false;
}