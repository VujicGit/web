$(document).ready(function () {

    getAllUsers();
    searchUsers();
    isAdminLoggedIn();
});

function getAllUsers() {

    $.ajax({
        type: "GET",
        url: "rest/users/getAll",
        success: function (users) {
            createTableData(users);
        }

    });


}

function createTableData(users) {
    let tableBody = $("#usersTableBody");
    tableBody.empty();
    $.each(users, function (index, value) {
        tableBody.append('<tr>' +
            '<th scope="row">' + (index + 1) + '</th>' +
            '<td scope="col">' + value.username + '</td>' +
            '<td scope="col">' + value.name + '</td>' +
            '<td scope="col">' + value.surname + '</td>' +
            '<td scope="col">' + value.role + '</td>' +
            '<td scope="col">' + value.gender + '</td>' +
            '</tr>')
    });
}

function searchUsers() {
    $("form[name=searchForm]").submit(function (event) {
        event.preventDefault();
        let searchInput = $("#searchInput").val();
        searchData = {
            text: searchInput
        };
        $.ajax({
            type: "POST",
            url: "rest/users/search",
            contentType: "application/json",
            data: JSON.stringify(searchInput),
            success: function (users) {
                createTableData(users);
            }
        });
    });

}