$(document).ready(function () {
    getApartment();
});

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