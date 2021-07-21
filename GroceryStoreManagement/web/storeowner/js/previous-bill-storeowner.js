var preBillList;

function showPreBillList() {
  var request = new XMLHttpRequest();

  var url = "GetPreviousBillList";
  url += "?date-from=" + document.getElementById("date-from").value;
  url += "&date-to=" + document.getElementById("date-to").value;
  if (document.getElementById("search-value").value !== "") {
    url += "&search-value=" + document.getElementById("search-value").value;
  }
  request.open("GET", url, true);
  request.onload = function () {
    var result = JSON.parse(this.responseText);
    if (result.isError) {
      alert(result.dateError);
    } else {
      preBillList = result;
      renderPreBillList();
    }
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

    var cellNo = row.insertCell(0);
    var cellCustName = row.insertCell(1);
    var cellTotalCost = row.insertCell(2);
    var cellBuyDate = row.insertCell(3);

    cellNo.innerHTML = i + 1;
    cellCustName.innerHTML = preBillList[i].name;
    cellTotalCost.innerHTML = formatNumber(preBillList[i].totalCost);
    cellBuyDate.innerHTML = preBillList[i].buyDate.substring(0, 10);

    cellTotalCost.style.textAlign = "right";

    var createClickHandler = function (i, row) {
      return function () {
        for (var j = 0; j < table.rows.length; j++) {
          table.rows[j].classList.remove("active-row");
        }
        row.className = "active-row";
        renderPreBillDetailList(preBillList[i].billID);
      };
    };

    row.onclick = createClickHandler(i, row);
    row.style.cursor = "pointer";
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
      cellProductName.style.textAlign = "left";
      cellQuantity.innerHTML = billDetails[i].quantity;
      cellCost.innerHTML = formatNumber(billDetails[i].cost);
      cellTotal.innerHTML = formatNumber(billDetails[i].total);

      cellQuantity.style.textAlign = "right";
      cellCost.style.textAlign = "right";
      cellTotal.style.textAlign = "right";
    }
  }
  var tableSum = document.getElementById("pre-bill-detail-summary");
  tableSum.innerHTML = "";
  var row = tableSum.insertRow(-1);

  var cellTotalCostLabel = row.insertCell(0);
  var cellTotalCost = row.insertCell(1);
  cellTotalCostLabel.innerHTML = "Tổng cộng:";
  cellTotalCostLabel.style.width = "80%";
  cellTotalCostLabel.style.fontWeight = "bold";
  cellTotalCostLabel.style.textAlign = "right";
  cellTotalCost.innerHTML = formatNumber(totalCost); // Thêm tổng cộng vào đây
  cellTotalCost.style.textAlign = "right";
  cellTotalCost.style.width = "20%";


  var row = tableSum.insertRow(-1);
  var cellPointUsedLabel = row.insertCell(0);
  var cellPointUsed = row.insertCell(1);
  cellPointUsedLabel.innerHTML = "Giảm giá:";
  cellPointUsedLabel.style.width = "80%";
  cellPointUsedLabel.style.fontWeight = "bold";
  cellPointUsedLabel.style.textAlign = "right";
  cellPointUsed.innerHTML = pointUsed; // Thêm giảm giá vào đây
  cellPointUsed.style.textAlign = "right";
  cellPointUsed.style.width = "20%";
  cellPointUsed.style.textDecoration = "line-through";


  var row = tableSum.insertRow(-1);
  var cellCashLabel = row.insertCell(0);
  var cellCash = row.insertCell(1);
  cellCashLabel.innerHTML = "Thành tiền:";
  cellCashLabel.style.width = "80%";
  cellCashLabel.style.fontWeight = "bold";
  cellCashLabel.style.textAlign = "right";
  cellCash.innerHTML = formatNumber(cash); // Thêm thành tiền vào đây
  cellCash.style.textAlign = "right";
  cellCash.style.color = "red";
  cellCash.style.fontWeight = "bold";
  cellCash.style.width = "20%";


  var row = tableSum.insertRow(-1);
  var cellPointLabel = row.insertCell(0);
  var cellPoint = row.insertCell(1);
  cellPointLabel.innerHTML = "Điểm tích lũy:";
  cellPointLabel.style.width = "80%";
  cellPointLabel.style.fontWeight = "bold";
  cellPointLabel.style.textAlign = "right";
  cellPoint.innerHTML = `
        <del>
            <span style="font-size: 0.8em;">
                ${
                    10 // Thêm điểm cũ vào đây
                }
            </span>
        </del>
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
        </svg>
        <span>${
            10 // Thêm điểm tích lũy mới vào đây
        }</span>
    `; 
  cellPoint.style.textAlign = "right";
  cellPoint.style.color = "royalBlue";
  cellPoint.style.width = "20%";
  cellPoint.style.fontWeight = "bold";
}
