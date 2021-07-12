//window.onload = function (){
//    var receiptList;
//    GetPreviousReceipt();
//};
function GetPreviousReceipt() {
    var request = new XMLHttpRequest();
    var dateStart = document.getElementById("date-from").value;
    var dateEnd = document.getElementById("date-to").value;

    var url = "GetPreviousReceiptList";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;
    request.open('GET', url, false);
    request.onload = function () {
        receiptList = JSON.parse(this.responseText);
        renderReceiptList(receiptList);
    };
    request.send();
}

function renderReceiptList(data) {
    document.getElementById("tableContent").innerHTML = "";
    var index = 0;
    for (i = 0; i < data.length; i++) {
        if (data[i].is_selling !== false) {
            index++;
            var tr = document.createElement("tr");

            var th_index = document.createElement("th");
            th_index.setAttribute("scope", "row");
            th_index.innerHTML = index;
            th_index.style.verticalAlign = "middle";

            var td_date = document.createElement("td");
            td_date.innerHTML = data[i].import_date;
            td_date.style.verticalAlign = "middle";

            var td_user = document.createElement("td");
            td_user.innerHTML = data[i].owner_name;
            td_user.style.verticalAlign = "middle";

            var td_total = document.createElement("td");
            td_total.innerHTML = data[i].total.toLocaleString('vi', {style : 'currency', currency : 'VND'});
            td_total.setAttribute("class", "text-right");
            td_total.style.verticalAlign = "middle";

            var td_button = document.createElement("td");
            td_button.style.verticalAlign = "middle";
            var Add_bt = document.createElement("a");
            Add_bt.innerHTML = "<i class='fas fa-info-circle btn-inventory'></i>";
            Add_bt.setAttribute("onclick", "GetDetail(" + data[i].receipt_ID + ")");
            td_button.appendChild(Add_bt);

            tr.appendChild(th_index);
            tr.appendChild(td_date);
            tr.appendChild(td_user);
            tr.appendChild(td_total);
            tr.appendChild(td_button);

            document.getElementById("tableContent").appendChild(tr);
        }
    } // finish printing a product detail row
}

var receiptDetail;

function GetDetail(receiptID) {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        receiptDetail = JSON.parse(this.responseText);
    };
    content =
            "receipt_ID=" +
            encodeURIComponent(receiptID);
    xhttp.open("POST", "GetReceiptInformation", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
    renderReceiptDetail();

    for (i = 0; i < receiptList.length; i++) {
        if (receiptList[i].receipt_ID == receiptID) {
            var totalReceipt = receiptList[i].total;
            var username = receiptList[i].owner_name;
            var import_date = receiptList[i].import_date;
        }
    }

    if (receiptList.length > 0) {
        document.getElementById("receipt-info").style.display = "block";
    }

    document.getElementById("STOREOWNERUSERNAME").innerHTML = "";
    document.getElementById("IMPORTDATE").innerHTML = "";
    document.getElementById("TOTALCOST").innerHTML = "";
    var td_totalcost = document.createElement("span");
    td_totalcost.innerHTML = totalReceipt.toLocaleString('vi', {style : 'currency', currency : 'VND'});
    td_totalcost.style.color = "red";
    td_totalcost.style.fontWeight = "600";
    
    var td_username = document.createElement("span");
    td_username.innerHTML = username;

    var td_date = document.createElement("span");
    td_date.innerHTML = import_date;

    document.getElementById("TOTALCOST").appendChild(td_totalcost);
    document.getElementById("STOREOWNERUSERNAME").appendChild(td_username);
    document.getElementById("IMPORTDATE").appendChild(td_date);
}

function renderReceiptDetail() {
    document.getElementById("receiptDetailContent").innerHTML = "";
    var tempTotal = 0;
    var index = 0;
    for (i = 0; i < receiptDetail.length; i++) {
        index++;
        var tr = document.createElement("tr");

        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = index;
        th_index.style.verticalAlign = "middle";

        var td_name = document.createElement("td");
        td_name.innerHTML = receiptDetail[i].productName;
        td_name.style.textAlign = "left";
        td_name.style.verticalAlign = "middle";

        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = receiptDetail[i].quantity;
        td_quantity.style.verticalAlign = "middle";

        var td_price = document.createElement("td");
        td_price.innerHTML = receiptDetail[i].cost.toLocaleString('vi', {style : 'currency', currency : 'VND'});
        td_price.setAttribute("class", "text-right");
        td_price.style.verticalAlign = "middle";

        var td_total = document.createElement("td");
        td_total.innerHTML = receiptDetail[i].total.toLocaleString('vi', {style : 'currency', currency : 'VND'});
        td_total.setAttribute("class", "text-right");
        tempTotal += receiptDetail[i].total;
        td_total.style.verticalAlign = "middle";

        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_quantity);
        tr.appendChild(td_price);
        tr.appendChild(td_total);

        document.getElementById("receiptDetailContent").appendChild(tr);
    }

}

