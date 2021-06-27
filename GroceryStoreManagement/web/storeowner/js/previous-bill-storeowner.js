var preBillList;

function showPreBillList() {
    var request = new XMLHttpRequest();
    var error = document.getElementById("error-date-prebill");

    var url = "GetPreviousBillList";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;
    if (document.getElementById("search-value").value !== "") {
        url += "&search-value=" + document.getElementById("search-value").value;
    }
    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            error.innerHTML = result.dateError;
            error.style.display = "block";
        } else {
            error.style.display = "none";
            renderCustomerStatistic(result);
        }
        preBillList = JSON.parse(this.responseText);
        renderPreBillList();
    };
    request.send();
}

function renderPreBillList() {
    var table = document.getElementById("pre-bill-area");
    table.innerHTML = "";

    if (preBillList.length === 0) {
        document.getElementById("not-found-label").style = "display:block";
    } else {
        document.getElementById("not-found-label").style = "display:none";
    }

    for (i = 0; i < preBillList.length; i++) {
        var row = table.insertRow(-1);

        var createClickHandler = function (i) {
            return function () {
                renderPreBillDetailList(preBillList[i].billID);
            };
        };

        row.onclick = createClickHandler(i);
        row.style = "cursor:pointer";

        var cellNo = row.insertCell(0);
        var cellCustName = row.insertCell(1);
        var cellTotalCost = row.insertCell(2);
        var cellBuyDate = row.insertCell(3);

        cellNo.innerHTML = i + 1;
        cellCustName.innerHTML = preBillList[i].name;
        cellTotalCost.innerHTML = formatNumber(preBillList[i].totalCost);
        cellBuyDate.innerHTML = preBillList[i].buyDate;
    }
}

function renderPreBillDetailList(billID) {
    var billInfo = document.getElementById("bill-info");
    billInfo.style = "display:block";

    var table = document.getElementById("pre-bill-detail-area");
    table.innerHTML = "";

    var customerName = document.getElementById("customer-name");
    var customerPhone = document.getElementById("customer-phone");
    var buyDate = document.getElementById("buy-date");
    var cashierName = document.getElementById("cashier-name");
    billDetails = null;

    for (i = 0; i < preBillList.length; i++) {
        if (preBillList[i].billID === billID) {
            billDetails = preBillList[i].details;
            customerName.innerHTML = preBillList[i].name;
            customerPhone.innerHTML = preBillList[i].phoneNo;
            buyDate.innerHTML = preBillList[i].buyDate;
            cashierName.innerHTML = preBillList[i].cashier;
            totalCost = preBillList[i].totalCost;
            pointUsed = preBillList[i].pointUsed;
            cash = preBillList[i].cash;
        }
    }

    if (!billDetails) {
        document.getElementById("empty-bill-label").style = "display:block";
    } else {
        document.getElementById("empty-bill-label").style = "display:none";

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
            cellTotal.innerHTML = formatNumber(billDetails[i].total);
        }
    }

    var row = table.insertRow(-1);
    row.insertCell(0);
    row.insertCell(1);
    row.insertCell(2);
    var cellTotalCostLabel = row.insertCell(3);
    var cellTotalCost = row.insertCell(4);
    cellTotalCostLabel.innerHTML = "Tổng cộng";
    cellTotalCost.innerHTML = formatNumber(totalCost);

    var row = table.insertRow(-1);
    row.insertCell(0);
    row.insertCell(1);
    row.insertCell(2);
    var cellPointUsedLabel = row.insertCell(3);
    var cellPointUsed = row.insertCell(4);
    cellPointUsedLabel.innerHTML = "Điểm sử dụng";
    cellPointUsed.innerHTML = pointUsed;

    var row = table.insertRow(-1);
    row.insertCell(0);
    row.insertCell(1);
    row.insertCell(2);
    var cellCashLabel = row.insertCell(3);
    var cellCash = row.insertCell(4);
    cellCashLabel.innerHTML = "Khách đưa";
    cellCash.innerHTML = formatNumber(cash);
}