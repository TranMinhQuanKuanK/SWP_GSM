function showProductStatistic() {
    var request = new XMLHttpRequest();
    
    console.log("This is statistic.js")
    
    var url = "GetProductStatistic";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;
    url += "&sort-by=" + document.getElementById("sort-by").value;
    
    request.open('GET', url, true);
    request.onload() = function () {
        var productStatistic = JSON.parse(this.responseText);
        renderProductStatistic(productStatistic);
    }
    request.send();
}

function renderProductStatistic(productStatistic) {
    console.log(productStatistic);
}