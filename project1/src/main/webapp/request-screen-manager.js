'use strict';

let viewPending = document.getElementById("view-pending");
let viewResolved = document.getElementById("view-resolved");
let viewEmployees = document.getElementById("employees");
let search = document.getElementById("search");
let searchRequest = document.getElementById("search-request");
let table = document.getElementById("table-body");
let resolveRequests = document.getElementById("resolve");

let homeUri = 'http://localhost:8080/project1/main/homescreen';
let requestUri = 'http://localhost:8080/project1/main/request-manager';
let searchUri = 'http://localhost:8080/project1/main/request-search';

search.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    clearTable();
    clearEmployeeView();
    populateSearch();
});

viewResolved.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    clearTable();
    clearEmployeeView();
    populateResolved();
});

viewEmployees.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    clearTable();
    clearEmployeeView();
    populateViewEmployees();
});

viewPending.addEventListener('click', (e) => {
    e.preventDefault();
    clearRequestInfo();
    clearTable();
    clearEmployeeView();
    showRequestsTable();
    populateViewPending();
});

searchRequest.addEventListener('click', (e) => {
    e.preventDefault();
    showRequestsTable();
    searchReqest();
});

function clearRequestInfo() {
    document.getElementById('request-form').style.display = 'none';
    document.getElementById('table').style.display = 'none';
    table.innerHTML = '';
}

function populateSearch() {

    document.getElementById('request-form').style.display = 'block';
}

async function searchReqest() {
    document.getElementById("search-table").innerHTML = '';
    let response = await fetch(searchUri, { method: 'POST', body: JSON.stringify(document.getElementById('name').value) });
    let body = await response.text();
    body = JSON.parse(body);
    let tableBody = document.getElementById("search-table");

    for (let key in body) {
        let row = document.createElement("tr");
        row.appendChild(document.createElement("td")).innerHTML = body[key].description;
        row.appendChild(document.createElement("td")).innerHTML = "$" + body[key].amount;
        row.appendChild(document.createElement("td")).innerHTML = "<select id=\"" + key + "\"><option value=\"nothing\"></option>"
            + "<option value=\"Approved\">Approve</option><option value=\"Denied\">Deny</option></select>";
        tableBody.appendChild(row);
    }
    resolveRequests.addEventListener('click', (e)=>{
        clearTable();
        updateRequests(body);
        
    });
}

async function updateRequests(body){
    document.getElementById("search-table").innerHTML = '';
    let i = 0;
    let requestArray = [];
    for(let key in body){
        if (document.getElementById(key).value !== "nothing") {
            body[key].status = document.getElementById(key).value;
            requestArray[i++] = body[key];
        }
    }
    let response = await fetch( requestUri + '3', {method: 'POST', body: JSON.stringify(requestArray)});
    populateViewPending();
}

async function populateResolved() {
    document.getElementById('table').style.display = 'inline';
    let response = await fetch(requestUri + '1', { method: 'POST' })
    let body = await response.text();
    makeTable(body);
}

async function populateViewEmployees() {
    let response = await fetch(requestUri + '2', { method: 'POST', body: '' })
    let body = await response.text();
    body = JSON.parse(body);
    for (let key in body) {
        document.getElementById("employee-view").appendChild(document.createElement('p')).innerHTML = body[key].type + " : " + body[key].name;
    }
}

async function populateViewPending() {
    document.getElementById("search-table").innerHTML = '';
    let response = await fetch(requestUri, { method: 'POST', body: '' })
    let body = await response.text();
    body = JSON.parse(body);
    let tableBody = document.getElementById("search-table");

    for (let key in body) {
        let row = document.createElement("tr");
        row.appendChild(document.createElement("td")).innerHTML = body[key].description;
        row.appendChild(document.createElement("td")).innerHTML = "$" + body[key].amount;
        row.appendChild(document.createElement("td")).innerHTML = "<select id=\"" + key + "\"><option value=\"nothing\"></option>"
            + "<option value=\"Approved\">Approve</option><option value=\"Denied\">Deny</option></select>";
        tableBody.appendChild(row);
    }
    resolveRequests.addEventListener('click', (e)=>{
        document.getElementById("search-table").innerHTML = '';
        updateRequests(body);
    });

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
    console.log(body);
    body = JSON.parse(body);
    for (let e in body) {
        let status = document.createElement('th');
        status.innerText = body[e].status;
        let desc = document.createElement('td');
        desc.innerText = body[e].description;
        let amt = document.createElement('td')
        amt.innerText = '$' + body[e].amount;
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

function showRequestsTable() {
    document.getElementById("requests").style.display = 'inline';
}

function hideRequestsTable() {
    document.getElementById("requests").style.display = 'none';
    document.getElementById("search-table").innerHTML = '';
}

function clearEmployeeView() {
    document.getElementById("employee-view").innerHTML = '';
}

function clearTable(){
    document.getElementById("search-table").innerHTML = '';
}