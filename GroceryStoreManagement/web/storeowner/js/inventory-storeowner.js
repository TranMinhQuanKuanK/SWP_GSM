function getProduct() {
    var xhttp = new XMLHttpRequest();
    var cat_ID = document.getElementById("inputGroupSelect01").value;
    var search_val = document.getElementById("inputSearchVal").value;
    var noos = document.getElementById("noos_check");
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var productObject = JSON.parse(this.responseText);
            processProduct(productObject);
        }
    };
    if (noos.checked === true) {
        if (cat_ID === "all") {
            var url = "GetProductList?search_value=" + search_val + "&only_noos_items=" + noos;
        } else {
            var url = "GetProductList?search_value=" + search_val + "&category_id=" + cat_ID + "&only_noos_items=" + noos;
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

//        var cell8 = row.insertCell(7);
//        var cell9 = row.insertCell(8);
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