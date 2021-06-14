function showProductStatistic() {
    var request = new XMLHttpRequest();
    
    var url = "GetProductStatistic";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;
    url += "&sort-by=" + document.getElementById("sort-by").value;
    
    request.open('GET', url, true);
    request.onload = function () {
        var productStatistic = JSON.parse(this.responseText);
        renderProductStatistic(productStatistic);
    };
    request.send();
}

function renderProductStatistic(productStatistic) {
    var table = document.getElementById("product-stat-area");
    table.innerHTML = "";
    
    for (i = 0; i < productStatistic.length; i++) {
        var row = table.insertRow(-1);
        
        var cellNo = row.insertCell(0);
        var cellProductName = row.insertCell(1);
        var cellQuantity = row.insertCell(2);
        var cellTotal = row.insertCell(3);
        
        cellNo.innerHTML = i + 1;
        cellProductName.innerHTML = productStatistic[i].productName;
        cellQuantity.innerHTML = productStatistic[i].quantity;
        cellTotal.innerHTML = productStatistic[i].total;
    }
}