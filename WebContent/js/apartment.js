$(document).ready(function () {
    var date = new Date();


    getApartment();
    getDatesForIssue();
    reserveApartment();
    isUserLoggedIn();
});


function reserveApartment() {

    let form = $("form[name=reserveForm]");
    form.submit(function (event) {
        event.preventDefault();
        /*formData = {
             id: getUrlParameter("id"),
             startDate: new Date($("#checkInDateInput").val()),
             nights: $("#nights").val()
         };
         $.ajax({
             type: "POST",
             url: "rest/apartments/submitReservation",
             contentType: "application/json",
             data: JSON.stringify(formData),
             success: function (data, textStatus, XMLHttpRequest) {
                 alert("prosao");
             },
             error: function (XMLHttpRequest, textStatus, errorThrown) {
                 var obj = JSON.parse(XMLHttpRequest.responseText);
                 alert(obj.hello);
             }
         })*/

        $.ajax({
            type: "GET",
            url: "rest/login/reservation/guest",
            success: function (data, textStatus, XMLHttpRequest) {
                openConfirmReservationModal();
                fillConfirmReservationForm();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var obj = JSON.parse(XMLHttpRequest.responseText);
                alert(obj.errorMessage);
            }

        })
    });
}



function fillConfirmReservationForm() {
    var checkInDate = $("#checkInDateInput").val();
    var numberOfNights = $("#nights").val();
    $.ajax({
        type: "GET",
        url: "rest/apartments/getApartment/" + getUrlParameter("id"),
        success: function (apartment) {
            fillReservationModal(checkInDate, numberOfNights, apartment.checkInTime, apartment.checkoutTime);
        }
    });
}

function fillReservationModal(checkInDate, checkOutDate, checkInTime, checkOutTime) {
    $("#confirmReservationCheckInDate").text(checkInDate);
    $("#confirmReservationCheckoutDate").text(checkOutDate);
    $("#confirmReservationCheckInTime").text(checkInTime);
    $("#confirmReservationCheckoutTime").text(checkOutTime);
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
        }
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