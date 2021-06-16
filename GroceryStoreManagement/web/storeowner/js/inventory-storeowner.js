window.onload = function () {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            const selectOptions = JSON.parse(this.responseText);
            processCategory(selectOptions);
        }
    };
    xhttp.open("GET", "GetCategoryList", true);
    xhttp.send();
};

function processCategory(data) {
    var defaultOps = document.createElement("option");
    defaultOps.setAttribute("value","all");
    defaultOps.innerHTML = "(*)";
    document.getElementById("inputGroupSelect01").appendChild(defaultOps);
    for (i = 0; i < data.length; i++) {
        var selectOps = document.createElement("option");
        selectOps.setAttribute("value", data[i].category_ID);
        selectOps.innerHTML = data[i].name;
        document.getElementById("inputGroupSelect01").appendChild(selectOps);
    }
}
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

function printProductList(data) {
    document.getElementById("tableContent").innerHTML = "";
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

        var td_threshold = document.createElement("td");
        td_threshold.innerHTML = data[i].lower_threshold;

        var td_quantity = document.createElement("td");
        td_quantity.innerHTML = data[i].quantity;
        ;

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
        tr.appendChild(td_threshold);
        tr.appendChild(td_quantity);
        tr.appendChild(td_button);

        document.getElementById("tableContent").appendChild(tr);
    }// finish printing a product detail row
}


