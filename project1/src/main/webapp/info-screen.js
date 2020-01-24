'use strict';

let firstName = document.getElementById('first-name');
let lastName = document.getElementById('last-name');
let email = document.getElementById('email');
let updateBtn = document.getElementById('update');

let profileUri = 'http://localhost:8080/project1/main/profile';
let homeUri = 'http://localhost:8080/project1/main/homescreen';

let user = { name: '', email: '' };

getProfileInfo();

async function getProfileInfo() {
    let response = await fetch(profileUri, { method: 'GET' });
    user = await response.json();
    populateInfoFields();
}

function populateInfoFields() {
    let name = user.name.split(" ");
    firstName.value = name[0];
    lastName.value = name[1];
    email.value = user.email;
}

updateBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (firstName.value != '' && lastName.value != '' && email.value != '') {
        updateUserInfo();
    } else
        showAlertsFailure();
});

async function updateUserInfo() {
    user.name = firstName.value + ' ' + lastName.value;
    user.email = email.value;
    let response = await fetch(profileUri, { method: 'POST', body: JSON.stringify(user) })
    if (response.status >= 200 && response.status < 300) {
        showAlertsSuccess();
    }
}

function showAlertsSuccess() {
    document.getElementById('bootstrap-alert-success').style.display = 'block';
    setTimeout(function () { document.getElementById('bootstrap-alert-success').style.display = 'none' }, 5000);
}

function showAlertsFailure() {
    document.getElementById('bootstrap-alert-failure').style.display = 'block';
    setTimeout(function () { document.getElementById('bootstrap-alert-failure').style.display = 'none' }, 5000);
}

let logout = document.getElementById('logout');


logout.addEventListener('click', (e) => {
    e.preventDefault();
    logoutUser();
});

async function logoutUser() {
    let response = await fetch(homeUri, { method: 'POST' });
    window.location.href = await response.url;    
}