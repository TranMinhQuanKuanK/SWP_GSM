var preBillList;

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
        preBillList = JSON.parse(this.responseText);
        renderPreBillList(preBillList);
    }
    request.send();
}

function renderPreBillList() {
    var table = document.getElementById("pre-bill-area");
    table.innerHTML = "";

    for (i = 0; i < preBillList.length; i++) {
        var row = table.insertRow(-1);
        
        var createClickHandler = function(i) {
            return function() {
                renderPreBillDetailList(preBillList[i].billID);
            };
        };
       
        row.onclick = createClickHandler(i);
        row.style = "cursor:pointer";

        var cellNo = row.insertCell(0);
        var cellCustName = row.insertCell(1);
        var cellCustPhone = row.insertCell(2);
        var cellTotalCost = row.insertCell(3);
        var cellBuyDate = row.insertCell(4);
        
        cellNo.innerHTML = i + 1;
        cellCustName.innerHTML = preBillList[i].name;
        cellCustPhone.innerHTML = preBillList[i].phoneNo;
        cellTotalCost.innerHTML = formatNumber(preBillList[i].totalCost);
        cellBuyDate.innerHTML = preBillList[i].buyDate;
    }
}

function renderPreBillDetailList(billID) {
    var table = document.getElementById("pre-bill-detail-area");
    table.innerHTML = "";
    
    for (i = 0; i < preBillList.length; i++) {
        if (preBillList[i].billID === billID) {
            billDetails = preBillList[i].details;
        }
    }

    for (i = 0; i < billDetails.length; i++) {
        var row = table.insertRow(-1);

        var cellNo = row.insertCell(0);
        var cellProductName = row.insertCell(1);
        var cellQuantity = row.insertCell(2);
        var cellCost = row.insertCell(3);
        var cellTotal = row.insertCell(4);

        cellNo.innerHTML = i + 1;
        cellProductName.innerHTML = billDetails[i].productName;
        cellQuantity.innerHTML = billDetails[i].quantity;
        cellCost.innerHTML = formatNumber(billDetails[i].cost);
        cellTotal.innerHTML = billDetails[i].total;
    }
}