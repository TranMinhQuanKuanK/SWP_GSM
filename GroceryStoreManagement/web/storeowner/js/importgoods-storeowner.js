var receiptOnSession;

window.onload = function () {
    getReceipt();
    getPendingList();
};

function getReceipt() {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        receiptOnSession = JSON.parse(this.responseText);
        renderReceiptDetail();
    };
    xhttp.open("GET", "GetReceipt", false);
    xhttp.send();
}

function getPendingList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        var pendingList = JSON.parse(this.responseText);
        renderPendingList(pendingList);
    };
    xhttp.open("GET", "GetPendingItemList", false);
    xhttp.send();
}
;

function renderPendingList(data) {
    document.getElementById("tableContent").innerHTML = "";
    var index = 0;
    for (i = 0; i < data.length; i++) {
        index++;
        var tr = document.createElement("tr");

        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = index;

        var td_name = document.createElement("td");
        td_name.innerHTML = data[i].product_name;
        td_name.style.textAlign = "left";

        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = data[i].product_quantity;
        td_quantity.setAttribute("class", "text-right");

        var td_button = document.createElement("td");
        var Add_bt = document.createElement("a");
        Add_bt.innerHTML = "<i class='fas fa-plus-circle btn-import-goods'></i>";
        Add_bt.setAttribute("onclick", "addToReceipt(" + data[i].product_ID + ")");

        var Ignore_bt = document.createElement("a");
        Ignore_bt.innerHTML = "<i class='fas fa-minus-circle btn-import-goods ml-3'></i>";
        Ignore_bt.setAttribute("onclick", "changeStatusInPendingList(" + data[i].product_ID + ")");

        td_button.appendChild(Add_bt);
        td_button.appendChild(Ignore_bt);

        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_quantity);
        tr.appendChild(td_button);

        document.getElementById("tableContent").appendChild(tr);
    }
}

function changeStatusInPendingList(productID) {
    var xhttp = new XMLHttpRequest();
    content =
            "product_ID=" +
            encodeURIComponent(productID);
    xhttp.open("POST", "UpdateSuggestion", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
    getPendingList();
}

function addToReceipt(productID) {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        receiptOnSession = JSON.parse(this.responseText);
    };
    content =
            "product_ID=" +
            encodeURIComponent(productID);
    xhttp.open("POST", "AddToReceiptFromPending", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
    renderReceiptDetail();
}

function renderReceiptDetail() {
    document.getElementById("receiptContent").innerHTML = "";
    document.getElementById("TOTALCOST").innerHTML = "";
    var totalcost = receiptOnSession.total_cost;
    var td_totalcost = document.createElement("span");
    td_totalcost.innerHTML = totalcost;
    document.getElementById("TOTALCOST").appendChild(td_totalcost);
    var index = 0;
    var receiptItems = receiptOnSession.receipt_detail;
    for (i = 0; i < receiptItems.length; i++) {
        index++;
        var tr = document.createElement("tr");

        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = index;
        th_index.setAttribute("class", "text-right");
        
        var td_name = document.createElement("td");
        td_name.innerHTML = receiptItems[i].product.name;
        td_name.setAttribute("class", "text-left");
        
        var td_quantity = document.createElement("td");
        var input_quantity = document.createElement("input");
        input_quantity.style.width = "80%";
        input_quantity.setAttribute("class", "text-right float-right");
        input_quantity.setAttribute("id", "quantityOf" + receiptItems[i].product.product_ID);
        input_quantity.setAttribute("type", "number");
        input_quantity.setAttribute("value", receiptItems[i].quantity);
        input_quantity.setAttribute("min", "1");
        input_quantity.setAttribute("onchange", "updateQuantityItem(" + receiptItems[i].product.product_ID + ")");
        td_quantity.appendChild(input_quantity);


        var td_price = document.createElement("td");
        td_price.innerHTML = receiptItems[i].product.selling_price;
        td_price.setAttribute("class", "text-right");

        var td_cost = document.createElement("td");
        td_cost.innerHTML = receiptItems[i].product.selling_price * receiptItems[i].quantity;
        td_cost.setAttribute("class", "text-right");

        var td_button = document.createElement("td");
        var Remove_bt = document.createElement("a");
        Remove_bt.innerHTML = "<i class='fas fa-times-circle btn-inventory'></i>";
        Remove_bt.setAttribute("onclick", "removeFromReceipt(" + receiptItems[i].product.product_ID + ")");
        td_button.appendChild(Remove_bt);


        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_quantity);
        tr.appendChild(td_price);
        tr.appendChild(td_cost);
        tr.appendChild(td_button);

        document.getElementById("receiptContent").appendChild(tr);
    }
}

function updateQuantityItem(productID) {
    var tempQuantity = document.getElementById("quantityOf" + productID).value;
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        receiptOnSession = JSON.parse(this.responseText);
    };
    content =
            "product_id=" +
            encodeURIComponent(productID) +
            "&quantity=" +
            encodeURIComponent(tempQuantity);
    xhttp.open("POST", "EditQuantityInReceipt", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
    renderReceiptDetail();
}

function removeFromReceipt(productID) {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        receiptOnSession = JSON.parse(this.responseText);
    };
    content =
            "product_ID=" +
            encodeURIComponent(productID);
    xhttp.open("POST", "RemoveFromReceipt", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
    renderReceiptDetail();
}

function importReceipt() {
    if (receiptOnSession.total_cost !== 0) {
        var xhttp = new XMLHttpRequest();
        xhttp.onload = function () {
            console.log(this.responseText);
            receiptOnSession = JSON.parse(this.responseText);
            if (receiptOnSession == null) {
                alert("Đã nhập hàng vào kho!");
            }
        };
        xhttp.open("GET", "MakeNewReceipt", false);
        xhttp.send();
        getPendingList();
        getReceipt();
    } else {
        alert("Xin nhập dữ liệu vào form!");
    }
}