var receiptList;
function GetPreviousReceipt(){
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

function renderReceiptList(data){
    document.getElementById("tableContent").innerHTML = "";
    var index = 0;
    for (i = 0; i < data.length; i++) {
        if (data[i].is_selling !== false) {
            index++;
            var tr = document.createElement("tr");

            var th_index = document.createElement("th");
            th_index.setAttribute("scope", "row");
            th_index.innerHTML = index;

            var td_date = document.createElement("td");
            td_date.innerHTML = data[i].import_date;

            var td_user = document.createElement("td");
            td_user.innerHTML = data[i].store_owner_username;

            var td_total = document.createElement("td");
            td_total.innerHTML = data[i].total;

            var td_button = document.createElement("td");
                var Add_bt = document.createElement("input");
                Add_bt.setAttribute("type", "button");
                Add_bt.setAttribute("value", "Chi tiết");
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



