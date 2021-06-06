function getProduct() {
    var xhttp = new XMLHttpRequest();
    let cat_ID = document.getElementById("inputGroupSelect01");
    let search_val = document.getElementById("inputSearchVal");
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var productObject = JSON.parse(this.responseText);
            processProduct(productObject);
        }
    };
    let url = "GetProductList?search_value="+search_val+"&category_id="+cat_ID
    //xhttp.open("GET", "GetProductList?search_value=sua  tuoi", true);
    xhttp.open("GET",url, true);
    xhttp.send();
}
function processProduct(data) {
    for (i = 0; i < data.length; i++) {
        var productTable = document.getElementById("productTable");
        var row = productTable.insertRow(-1);//append thay vì insert đúng vào vị trí trong index
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        var cell7 = row.insertCell(6);
        var cell8 = row.insertCell(7);
        var cell9 = row.insertCell(8);
        cell1.innerHTML = data[i].name;
        cell2.innerHTML = data[i].quantity;
        cell3.innerHTML = data[i].cost_price;
        cell4.innerHTML = data[i].selling_price;
        cell5.innerHTML = data[i].lower_threshold;
        cell6.innerHTML = data[i].category.name;
        cell7.innerHTML = data[i].unit_label;
        cell8.innerHTML = data[i].is_selling;
        cell9.innerHTML = data[i].location;

    }
}