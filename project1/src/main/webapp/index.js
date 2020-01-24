'use strict';

let loginForm = document.getElementById("login-form");
let email = document.getElementById("login-email");
let password = document.getElementById("login-password");
let header = document.getElementById("header");

let loginUri = 'http://localhost:8080/project1/main/';

loginForm.addEventListener('submit', (e)=>{
    e.preventDefault();
    sendLoginCredentials();
})

async function sendLoginCredentials(){
    let user = {};
    user.email = email.value;
    user.password = password.value;

    let response = await fetch(loginUri, {method: 'POST', body: JSON.stringify(user)});
    if (response.status>=400) {
        showAlertsFailure();
    }
    else{
        window.location.href = await response.url;
    }
}

function showAlertsFailure() {
    document.getElementById('bootstrap-alert-failure').style.display = 'block';
    setTimeout(function () { document.getElementById('bootstrap-alert-failure').style.display = 'none' }, 2500);
}