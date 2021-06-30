var receiptOnSession;

window.onload = function(){
    getPendingList();
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
            receiptOnSession = JSON.parse(this.responseText);
            renderReceiptDetail();
    };
    xhttp.open("GET", "GetReceipt", false);
    xhttp.send();
};

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

        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = data[i].product_quantity;

        var td_button = document.createElement("td");
        var Add_bt = document.createElement("input");
        Add_bt.setAttribute("type", "button");
        Add_bt.setAttribute("value", "Add to Receipt");
        Add_bt.setAttribute("onclick", "addToReceipt(" + data[i].product_ID + ")");

        var Ignore_bt = document.createElement("input");
        Ignore_bt.setAttribute("type", "button");
        Ignore_bt.setAttribute("value", "Ignore");
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
    var index = 0;
    var receiptItems = receiptOnSession.receipt_detail;
    for (i = 0; i < receiptItems.length; i++) {
        index++;
        var tr = document.createElement("tr");

        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = index;

        var td_name = document.createElement("td");
        td_name.innerHTML = receiptItems[i].product.name;

        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = receiptItems[i].quantity;
        
        var td_price = document.createElement("td");
        td_price.innerHTML = receiptItems[i].product.selling_price;
        
        var td_button = document.createElement("td");
        var Remove_bt = document.createElement("input");
        Remove_bt.setAttribute("type", "button");
        Remove_bt.setAttribute("value", "Remove");
        Remove_bt.setAttribute("onclick", "removeFromReceipt(" + receiptItems[i].product.product_ID + ")");
        td_button.appendChild(Remove_bt);
        

        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_quantity);
        tr.appendChild(td_price);
        tr.appendChild(td_button);

        document.getElementById("receiptContent").appendChild(tr);
    }
}

function removeFromReceipt(productID){
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