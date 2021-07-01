window.onload = getPendingList();

function getPendingList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        console.log(this.responseText);
        var pendingList = JSON.parse(this.responseText);
        renderPendingList(pendingList);
    };
    xhttp.open("GET", "GetPendingItemList", false);
    xhttp.send();
};

function renderPendingList(data) {
    document.getElementById("pending-list-on-dashboard").innerHTML = "";
    for (i = 0; i < data.length; i++) {
        var itemRow = document.createElement("li");
        itemRow.setAttribute("class", "list-group-item");
            var itemContent = document.createElement("div");
            itemContent.setAttribute("class", "d-flex justify-content-between");
                var itemName = document.createElement("span");
                itemName.innerHTML = data[i].product_name;
                
                var btGroup = document.createElement("div");
                btGroup.setAttribute("class", "d-flex");
                btGroup.setAttribute("role", "group");
                    var btAdd = document.createElement("button");
                    btAdd.setAttribute("type", "button");
                    btAdd.setAttribute("class", "btn btn-outline-danger btn-sm mr-2");
                    btAdd.setAttribute("onclick", "addToReceipt(" + data[i].product_ID + ")");
                    btAdd.innerHTML = "Thêm";
                    
                    var btIgnore = document.createElement("button");
                    btIgnore.setAttribute("type", "button");
                    btIgnore.setAttribute("class", "btn btn-outline-secondary btn-sm");
                    btIgnore.setAttribute("onclick", "changeStatusInPendingList(" + data[i].product_ID + ")");
                    btIgnore.innerHTML = "Bỏ qua";
                btGroup.appendChild(btAdd);
                btGroup.appendChild(btIgnore);
            itemContent.appendChild(itemName);
            itemContent.appendChild(btGroup);
        itemRow.appendChild(itemContent);
        document.getElementById("pending-list-on-dashboard").appendChild(itemRow);
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
    content =
            "product_ID=" +
            encodeURIComponent(productID);
    xhttp.open("POST", "AddToReceiptFromPending", false);
    xhttp.setRequestHeader(
            "Content-Type",
            "application/x-www-form-urlencoded;charset=UTF-8"
            );
    xhttp.send(content);
}
