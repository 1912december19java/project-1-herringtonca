'use strict';

let updateBtn = document.getElementById("update");
let pass = document.getElementById("pass");
let confirmPass = document.getElementById("confirm-pass");

let profileUri = 'http://localhost:8080/project1/main/profile-pass';
let homeUri = 'http://localhost:8080/project1/main/homescreen';

updateBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (pass.value === confirmPass.value && pass.value != '') {
        updatePassword();
    } else {
        showAlertsFailure();
    }
});

async function updatePassword() {
    console.log(pass.value);
    console.log(confirmPass.value);
    let password = {password : pass.value};
    let response = await fetch(profileUri, { method: 'POST', body: JSON.stringify(password)});
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