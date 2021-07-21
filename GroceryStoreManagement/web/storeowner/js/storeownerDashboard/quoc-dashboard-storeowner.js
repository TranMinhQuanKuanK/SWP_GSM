var dateFrom, dateTo, prevFinancialStatistics, prevCustomerStatistics, prevProductStatistics;

window.onload = onloadFunction();

function onloadFunction() {
    getTime();

    var labels = document.getElementsByClassName("statistics-info");
    for (i = 0; i < labels.length; i++) {
        labels[i].innerHTML = "THÁNG " + (new Date().getMonth() + 1);
    }

    //Get current date
    dateTo = document.getElementById("date-to").value;

    //Get first day of month
    dateFrom = dateTo.substring(0, 8) + "01 00:00:00";

    showFinancialStatistic();
    showProductStatistic();
    showCustomerStatistic();
}

function updateAllStatistics() {
    getTime();

    var labels = document.getElementsByClassName("statistics-info");
    for (i = 0; i < labels.length; i++) {
        labels[i].innerHTML = "THÁNG " + (new Date().getMonth() + 1);
    }

    //Get current date
    dateTo = document.getElementById("date-to").value;

    //Get first day of month
    dateFrom = dateTo.substring(0, 8) + "01 00:00:00";

    updateFinancialStatistic();
    updateProductStatistic();
    updateCustomerStatistic();
}

setInterval(updateAllStatistics, 15000);

function showFinancialStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetFinancialStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        renderFinancialStatistic(result);
        prevFinancialStatistics = result;

    };
    request.send();
}

function updateFinancialStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetFinancialStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (JSON.stringify(result) !== JSON.stringify(prevFinancialStatistics)) {
            renderFinancialStatistic(result);
            prevFinancialStatistics = result;
        }
    };
    request.send();
}

function renderFinancialStatistic(financialStatistic) {
    document.getElementById("bill-count").innerHTML = financialStatistic.countBill;
    document.getElementById("sum-revenue").innerHTML = formatNumber(financialStatistic.sumRevenue) + "đ";
    document.getElementById("sum-profit").innerHTML = formatNumber(financialStatistic.sumProfit) + "đ";
}

function showProductStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetProductStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        renderProductStatistic(result);
        prevProductStatistics = result;
    };
    request.send();
}

function updateProductStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetProductStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (JSON.stringify(result) !== JSON.stringify(prevProductStatistics)) {
            renderProductStatistic(result);
            prevProductStatistics = result;
            notifyNewUpdate("top-protudct");
        }
    };
    request.send();
}

function renderProductStatistic(productStatistic) {
    var ul = document.getElementById("top-product");

    for (i = 0; i < Math.min(10, productStatistic.length); i++) {
        var li = document.createElement("li");
        var container = document.createElement("div");
        var name = document.createElement("p");
        var key = document.createElement("p");
        
        li.setAttribute("class", "list-group-item");
        container.setAttribute("class", "d-flex justify-content-between");
        name.setAttribute("class", "my-auto w-80");
        key.setAttribute("class", "my-auto w-20 text-right");
        
        name.innerHTML = (i + 1) + ". " + productStatistic[i].productName;
        key.innerHTML = productStatistic[i].quantity;
        
        container.appendChild(name);
        container.appendChild(key);
        li.appendChild(container);
        ul.appendChild(li);
    }
}

function showCustomerStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetCustomerStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        renderCustomerStatistic(result);
        prevCustomerStatistics = result;
    };
    request.send();
}

function updateCustomerStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetCustomerStatistic";
    url += "?date-from=" + dateFrom;
    url += "&date-to=" + dateTo;

    request.open('GET', url, false);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (JSON.stringify(result) !== JSON.stringify(prevCustomerStatistics)) {
            renderCustomerStatistic(result);
            prevCustomerStatistics = result;
            notifyNewUpdate("top-customer");
        }
    };
    request.send();
}

function renderCustomerStatistic(CustomerStatistic) {
    var ul = document.getElementById("top-customer");

    for (i = 0; i < Math.min(10, CustomerStatistic.length); i++) {
        var li = document.createElement("li");
        var container = document.createElement("div");
        var name = document.createElement("p");
        var key = document.createElement("p");
        
        li.setAttribute("class", "list-group-item");
        container.setAttribute("class", "d-flex justify-content-between");
        name.setAttribute("class", "my-auto w-65");
        key.setAttribute("class", "my-auto w-35 text-right");
        
        name.innerHTML = (i + 1) + ". " + CustomerStatistic[i].customerName;
        key.innerHTML = formatNumber(CustomerStatistic[i].total) + "đ";
        
        container.appendChild(name);
        container.appendChild(key);
        li.appendChild(container);
        ul.appendChild(li);
    }
}