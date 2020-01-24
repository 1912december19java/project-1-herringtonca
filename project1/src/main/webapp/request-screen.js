'use strict';

let newRequest = document.getElementById("new-btn");
let viewHistory = document.getElementById("history-btn");
let viewFufilled = document.getElementById("fufilled-btn");
let viewPending = document.getElementById("pending-btn");
let submitRequest = document.getElementById("submit-request");
let table = document.getElementById("table-body");

let requestUri = 'http://localhost:8080/project1/main/request';
let homeUri = 'http://localhost:8080/project1/main/homescreen';

newRequest.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    populateNewRequest();
});

viewHistory.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    populateViewHistory();
});

viewFufilled.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    populateViewFufilled();
});

viewPending.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    populateViewPending();
});

submitRequest.addEventListener('click', (e)=>{
    e.preventDefault();
    sendNewReqest();
});

function clearRequestInfo() {
    document.getElementById('request-form').style.display = 'none';
    document.getElementById('table').style.display = 'none';
    table.innerHTML = '';
}

function populateNewRequest() {

    document.getElementById('request-form').style.display = 'block';

}

async function sendNewReqest() {
    let request = {};
    request.amount = document.getElementById('amount').value;
    request.description = document.getElementById('description').value;        
    let response = await fetch(requestUri, { method: 'POST', body: JSON.stringify(request)});
    if (response.ok) {
        showAlertsSuccess();
    }
}

async function populateViewHistory() {
    showTable();
    showResolvedColumn();
    let response = await fetch(requestUri + '1', { method: 'POST' })
    let body = await response.text();
    makeTable(body);
}

async function populateViewFufilled() {
    showTable();
    showResolvedColumn();
    let response = await fetch(requestUri +'2', { method: 'POST', body: '' })
    let body = await response.text();
    makeTable(body);
}

async function populateViewPending() {
    showTable();
    hideResolvedColumn();
    let response = await fetch(requestUri +'3', { method: 'POST', body: '' });
    let body = await response.text();
    makeTable(body);
}

function showAlertsSuccess() {
    document.getElementById('bootstrap-alert-success').style.display = 'block';
    setTimeout(function () { document.getElementById('bootstrap-alert-success').style.display = 'none' }, 5000);
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

function makeTable(body) {
    body = JSON.parse(body);
    console.log(body);
    for (let e in body) {
        let status = document.createElement('th');
        status.innerText = body[e].status;
        let desc = document.createElement('td');
        desc.innerText = body[e].description;
        let amt = document.createElement('td')
        amt.innerText = '$'+ body[e].amount;
        let resolvedBy = document.createElement('td');
        resolvedBy.innerText = body[e].resolvedBy;
        let row = document.createElement('tr');
        row.appendChild(status);
        row.appendChild(amt);
        row.appendChild(desc);
        row.appendChild(resolvedBy);
        table.appendChild(row);
    }
}

function hideTable() {
    document.getElementById('table').style.display = 'none';
}

function hideResolvedColumn() {
    document.getElementById('resolved-column').style.display = 'none';
}

function showResolvedColumn() {
    document.getElementById('resolved-column').style.display = 'table-cell';
}

function showTable() {
    document.getElementById('table').style.display = 'table';
}