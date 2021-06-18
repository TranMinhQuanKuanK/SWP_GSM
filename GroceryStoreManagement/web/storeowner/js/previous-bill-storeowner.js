function showPreBillList() {
    var request = new XMLHttpRequest();

    var url = "GetPreviousBillList";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;
    if (document.getElementById("search-value").value !== "") {
        url += "&search-value=" + document.getElementById("search-value").value;
    }
    request.open('GET', url, true);
    request.onload = function () {
        var preBillList = JSON.parse(this.responseText);
        renderPreBillList(preBillList);
    };
    request.send();
}

function renderPreBillList(preBillList) {
    var table = document.getElementById("pre-bill-area");
    table.innerHTML = "";

    for (i = 0; i < preBillList.length; i++) {
        var row = table.insertRow(-1);

        var cellNo = row.insertCell(0);
        var cellCustName = row.insertCell(1);
        var cellCustPhone = row.insertCell(2);
        var cellTotalCost = row.insertCell(3);
        var cellBuyDate = row.insertCell(4);

        cellNo.innerHTML = i + 1;
        cellCustName.innerHTML = preBillList[i].name;
        cellCustPhone.innerHTML = preBillList[i].phoneNo;
        cellTotalCost.innerHTML = preBillList[i].totalCost;
        cellBuyDate.innerHTML = preBillList[i].buyDate;
    }
}