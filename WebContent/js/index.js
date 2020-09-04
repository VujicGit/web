$(document).ready(function () {
    $('.input-daterange').datepicker({});

    let btnSubmit = $("#btnSubmit");
    let btnCancel = $("#btnCancel");
    var apartmentsCol = $("#apartmentsCol");
    let form = $("form[name=searchForm]");



    getAllApartments(apartmentsCol)



    btnCancel.click(function () {
        getAllApartments(apartmentsCol);
        resetForm();
    });

    form.submit(function (event) {
        event.preventDefault();
        searchData = {
            location: $("input[name=place]").val(),
            checkinDate: new Date($("input[name=startDate]").val()),
            checkoutDate: new Date($("input[name=endDate]").val()),
            minPrice: $("input[name=minPrice]").val(),
            maxPrice: $("input[name=maxPrice]").val(),
            numberOfRooms: $("input[name=rooms]").val(),
            numberOfGuests: $("input[name=guests]").val()
        }
        if (checkFormData(searchData.location, searchData.minPrice, searchData.maxPrice, searchData.numberOfRooms, searchData.numberOfGuests) !== true) {
            return;
        }
        if (checkForReset(searchData.location, searchData.minPrice, searchData.maxPrice, searchData.numberOfRooms, searchData.numberOfGuests, searchData.checkinDate, searchData.checkoutDate)) {
            getAllApartments(apartmentsCol);
        }

        searchApartments(apartmentsCol);
    });

});

function getAllApartments(apartmentsCol) {
    $.ajax({
        type: "GET",
        url: "rest/apartments/",
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
}

function searchApartments(apartmentsCol) {

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
}

function resetForm() {
    $("input[name=place]").val("");
    $("input[name=startDate]").val("");
    $("input[name=endDate]").val("");
    $("input[name=minPrice]").val("");
    $("input[name=maxPrice]").val("");
    $("input[name=rooms]").val("");
    $("input[name=guests]").val();

}

function checkPlace(place) {
    let regex = RegExp(".*");
    if (regex.test(place)) {
        let placeInput = $("input[name=place]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=place]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkMinPrice(minPrice) {
    let regex = RegExp("(^\\s*$)|(^[1-9]+(.)?[0-9]*$)");
    if (regex.test(minPrice)) {
        let placeInput = $("input[name=minPrice]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=minPrice]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkMaxPrice(maxPrice) {
    let regex = RegExp("(^\\s*$)|(^[1-9]+(.)?[0-9]*$)");
    if (regex.test(maxPrice)) {
        let placeInput = $("input[name=maxPrice]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=maxPrice]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkRooms(rooms) {
    let regex = RegExp("(^\\s*$)|(^([1-9]|[1-9][0-9]|100)$)");
    if (regex.test(rooms)) {
        let placeInput = $("input[name=rooms]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=rooms]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkGuests(guests) {
    let regex = RegExp("(^\\s*$)|(^([1-9])$)");
    if (regex.test(guests)) {
        let placeInput = $("input[name=guests]");
        placeInput.css('border', '0');
        return true;
    }
    let placeInput = $("input[name=guests]");
    placeInput.css('border', '1px solid red');
    return false;
}

function checkFormData(place, minPrice, maxPrice, numberOfRooms, numberOfGuests) {
    let placeState = false;
    let minPriceState = false;
    let maxPriceState = false;
    let numberOfRoomsState = false;
    let numberOfGuestsState = false;

    if (checkPlace(place)) {
        placeState = true;
    }
    if (checkMinPrice(minPrice)) {
        minPriceState = true;
    }
    if (checkMaxPrice(maxPrice)) {
        maxPriceState = true;
    }
    if (checkRooms(numberOfRooms)) {
        numberOfRoomsState = true;
    }
    if (checkGuests(numberOfGuests)) {
        numberOfGuestsState = true;
    }
    if (placeState && minPriceState && maxPriceState && numberOfRoomsState && numberOfGuestsState) {

        return true;
    }

    return false;
}

function checkForReset(place, minPrice, maxPrice, numberOfRooms, numberOfGuests, checkinDate, checkoutDate) {
    if (place === "" && minPrice === "" && maxPrice === "" && numberOfRooms === "" && numberOfGuests === "" && !checkinDate && !checkoutDate) {
        alert(checkinDate.toString());
        return true;
    }
    return false;
}