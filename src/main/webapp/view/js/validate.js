function validateUserEditForm() {

    let login = document.forms["edit"]["login"].value;
    let fname = document.forms["edit"]["first_name"].value;
    let lname = document.forms["edit"]["last_name"].value;
    let email = document.forms["edit"]["email"].value;

    if (hasNumber (fname) || hasNumber (lname)) {
        alert("Name cannot contain numbers");
        return false;
    }

    function hasNumber (str) {
        return str.split('').some(function (ch) { return parseInt(ch) });
    }
    if (login == "" || fname == "" || lname == "" || email == "") {
        alert("All the fields should be filled out");
        return false;
    }
}

function validateRegisterForm() {
    let login = document.forms["edit"]["login"].value;
    let fname = document.forms["edit"]["first_name"].value;
    let lname = document.forms["edit"]["last_name"].value;
    let email = document.forms["edit"]["email"].value;
    let pass = document.forms["edit"]["password"].value;

    if (hasNumber (fname) || hasNumber (lname)) {
        alert("Name cannot contain numbers");
        return false;
    }

    function hasNumber (str) {
        return str.split('').some(function (ch) { return parseInt(ch) });
    }
    if (login == "" || fname == "" || lname == "" || email == "" || pass == "") {
        alert("All the fields should be filled out");
        return false;
    }
}
function validateEditPasswordForm() {
    let old_password = document.forms["edit"]["old_password"].value;
    let password = document.forms["edit"]["password"].value;
    let re_password = document.forms["edit"]["re_password"].value;

    if (old_password == "" || password =="" || re_password == "") {
        alert("All the fields should be filled out");
        return false;
    }
}

function validateSeatForm() {
    var radios = document.getElementsByName("number");
    var formValid = false;

    var i = 0;
    while (!formValid && i < radios.length) {
        if (radios[i].checked) formValid = true;
        i++;
    }

    if (!formValid) alert("You have to choose a seat!");
    return formValid;
}

function validateRouteForm() {
    let start_station = document.forms["edit"]["start_station"].value;
    let end_station = document.forms["edit"]["end_station"].value;
    let minutes = document.forms["edit"]["minutes"].value;
    let hours = document.forms["edit"]["hours"].value;
    let days = document.forms["edit"]["days"].value;
    let price = document.forms["edit"]["price"].value;


    if (start_station == end_station) {
        alert("Departure and arrival stations can't be the same");
        return false;
    }

    if (minutes < 0 || hours < 0 || days < 0 || price < 0) {
        alert("The values can't be negative");
        return false;
    }
    if (price == 0 || price == "") {
        alert("You have to set valid price");
        return false;
    }

    if ((minutes != 0 || minutes != "") || (hours != 0 || hours != "") || (days != 0 || days != "")) {
        return true;
    } else {
        alert("You have to set valid time");
        return false;
    }


}
function validateStationForm() {
    let name = document.forms["edit"]["name"].value;
    if (name == "") {
        alert("You have to fill the field");
        return false;
    }
}
function validateSearchForm() {
    let start_station = document.forms["edit"]["depart_station"].value;
    let end_station = document.forms["edit"]["arrival_station"].value;

    if (start_station == end_station) {
        alert("Departure and arrival stations can't be the same");
        return false;
    }
}
function validateTopUpForm() {
    let amount = document.forms["edit"]["amount_to_add"].value;
    if (amount == "") {
        alert("You should fill out the field");
        return false;
    }
}