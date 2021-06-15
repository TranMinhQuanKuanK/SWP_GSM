function getProduct() {
    var xhttp = new XMLHttpRequest();
    var cat_ID = document.getElementById("inputGroupSelect01").value;
    var search_val = document.getElementById("inputSearchVal").value;
    var noos = document.getElementById("noos_check");
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var productObject = JSON.parse(this.responseText);
            printProductList(productObject);
        }
    };
    if (noos.checked == true) {
        if (cat_ID === "all") {
            var url = "GetProductList?search_value=" + search_val + "&only_noos_items=1";
        } else {
            var url = "GetProductList?search_value=" + search_val + "&category_id=" + cat_ID + "&only_noos_items=1";
        }
    } else {
        if (cat_ID === "all") {
            var url = "GetProductList?search_value=" + search_val;
        } else {
            var url = "GetProductList?search_value=" + search_val + "&category_id=" + cat_ID;
        }
    }

    xhttp.open("GET", url, true);
    xhttp.send();

}

function printProductList(data){
    document.getElementById("tableContent").innerHTML="";
    for (i = 0; i < data.length; i++) {
        var product = data[i];
        var tr = document.createElement("tr");
        
        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = i + 1;
        
        var td_name = document.createElement("td");
        td_name.innerHTML = data[i].name;
        
        var td_category = document.createElement("td");
        td_category.innerHTML = data[i].category.name;
        
        var td_unit = document.createElement("td");
        td_unit.innerHTML = data[i].unit_label;
        
        var td_status = document.createElement("td");
        td_status.innerHTML = data[i].is_selling;
        
        if (td_status.innerHTML === "false") {
            tr.className = "red-row";
        }
        if (td_status.innerHTML === "true") {
            td_status.innerHTML = "Đang bán";
        }
        if (td_status.innerHTML === "false") {
            td_status.innerHTML = "Ngưng bán";
        }
        
        var td_threshold = document.createElement("td");
        td_threshold.innerHTML = data[i].lower_threshold;
        
        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = data[i].quantity;;
        
        var td_button = document.createElement("td");
            var Add_bt = document.createElement("input");
            Add_bt.setAttribute("type", "button");
            Add_bt.setAttribute("value", "Add to to-import list");
            var Edit_bt = document.createElement("input");
            Edit_bt.setAttribute("type", "button");
            Edit_bt.setAttribute("value", "...");
        td_button.setAttribute("class", "btn-col");
        td_button.appendChild(Add_bt);
        td_button.appendChild(Edit_bt);
        
        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_category);
        tr.appendChild(td_unit);
        tr.appendChild(td_status);
        tr.appendChild(td_threshold);
        tr.appendChild(td_quantity);
        tr.appendChild(td_button);
            
        document.getElementById("tableContent").appendChild(tr);
    }// finish printing a product detail row
}


function processProduct(data) {
    var productTable = document.getElementById("productTable");
    while (productTable.rows.length > 1) {
        productTable.deleteRow(1);
    } // clear old table
    var count = 0;
    for (i = 0; i < data.length; i++) {
        var row = productTable.insertRow(-1);//append thay vì insert đúng vào vị trí trong index
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        var cell7 = row.insertCell(6);
        var cell8 = row.insertCell(7);
        count += 1;
        cell1.innerHTML = count;
        cell2.innerHTML = data[i].name;
        cell3.innerHTML = data[i].category.name;
        cell4.innerHTML = data[i].unit_label;
        cell5.innerHTML = data[i].is_selling;
        cell6.innerHTML = data[i].lower_threshold;
        cell7.innerHTML = data[i].quantity;
        cell8.innerHTML = "<input type='button' value='Add to to-import list' onclick=''>";
        if (cell5.innerHTML === "false") {
            row.style.backgroundColor = '#ff8989';
            row.style.color = 'black';
        }
        if (cell5.innerHTML === "true") {
            cell5.innerHTML = "Đang bán";
        }
        if (cell5.innerHTML === "false") {
            cell5.innerHTML = "Ngưng bán";
        }
//        cell2.innerHTML = data[i].quantity;
//        cell3.innerHTML = data[i].cost_price;
//        cell4.innerHTML = data[i].selling_price;
//        cell5.innerHTML = data[i].lower_threshold;
//        cell6.innerHTML = data[i].category.name;
//        cell7.innerHTML = data[i].unit_label;
//        cell8.innerHTML = data[i].is_selling;
//        cell9.innerHTML = data[i].location;

    }
}