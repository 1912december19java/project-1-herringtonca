'use strict';

let header = document.getElementById('header');

let homeUri = 'http://localhost:8080/project1/main/homescreen';

populateHeader();

let user = { name: '', email: '' };

async function populateHeader() {
    let response = await fetch(homeUri, { method: 'GET' });
    user = await response.json();
    setTimeout(function () {let name = user.name.split(" "); header.innerText = 'Welcome ' + name[0];}, 1000);
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