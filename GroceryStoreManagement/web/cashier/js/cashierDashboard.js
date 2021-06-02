var previousProductInfo = "";
var ProductInfoDuration;

function SearchForProduct(id) {
    for (i = 0; i < productList.length; i++) {
        if (productList[i].product_ID == id) return productList[i];
    }
    return -1;
}

function ToggleProductInfoState(id) {
    if (previousProductInfo != id) {
        $("#showProductInfo")
            .removeClass("d-none")
            .hide() // hides it first, or style it with 'display: none;' instead
            .fadeIn(300);
    }

    if (previousProductInfo == id && previousProductInfo != "") {
        previousProductInfo = "";
        $("#showProductInfo")
            .fadeOut(300, function() {
                $("#showProductInfo").addClass("d-none");
            })
    } else {
        previousProductInfo = id;
        ProductInfoDuration = setTimeout(function() { ToggleProductInfoState(id); }, 9000);
    }
}

function ShowProductInfo(id) {
    if (ProductInfoDuration) {
        clearTimeout(ProductInfoDuration);
        ProductInfoDuration = 0;
    }
    ToggleProductInfoState(id);


    var product = SearchForProduct(id);
    if (product == -1) console.log("error! Can't find product");
    document.getElementById("product-info-name").innerHTML = product.name;
    document.getElementById("product-info-quantity").innerHTML = product.quantity;
    document.getElementById("product-info-price").innerHTML = "<sup>đ</sup>" + product.selling_price + "/" + product.unit_label;
    document.getElementById("product-info-location").innerHTML = product.location;

}

//KuanK đã sửa - render product list
function RenderProduct() {
    // Load products
    var htmlList = document.getElementById("product-list");
    htmlList.innerHTML = "";
    for (i = 0; i < productList.length; i++) {
        htmlList.appendChild(createHTMLForEachProduct(productList[i]));
    }
}

function createHTMLForEachProduct(product) {
    var a = document.createElement("a");
    a.setAttribute("href", "#");
    a.setAttribute("class", "list-group-item list-group-item-action");

    var div1 = document.createElement("div");
    div1.setAttribute("class", "d-flex w-100 justify-content-between");

    var p = document.createElement("p");
    p.setAttribute("class", "w-50 my-auto");
    p.innerHTML = product.name;

    var h5 = document.createElement("h5");
    h5.setAttribute("class", "text-muted");
    h5.innerHTML = "<sup>đ</sup>" + product.selling_price + "/" + product.unit_label;

    var div2 = document.createElement("div");
    div2.setAttribute("class", "product-info-button");

    var button = document.createElement("button");
    button.setAttribute("id", product.product_ID);
    button.setAttribute("onclick", "ShowProductInfo(" + product.product_ID + ")");
    button.setAttribute("class", "btn btn-outline-secondary rounded-circle");
    button.innerHTML = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\"\n" +
        "                                            fill=\"currentColor\" class=\"bi bi-three-dots\" viewBox=\"0 0 16 16\">\n" +
        "                                            <path\n" +
        "                                                d=\"M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z\" />\n" +
        "                                        </svg>";

    div2.appendChild(button);
    div1.appendChild(p);
    div1.appendChild(h5);
    div1.appendChild(div2);
    a.appendChild(div1);

    return a;


    /*
     <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between ">
                                <p class="w-50 my-auto">Tương ớt chinsu 850gr</p>
                                <h5 class="text-muted"><sup>đ</sup>30000</h5>
                                <div class="product-info-button">
                                    <button onclick="ShowProductInfo(1)" class="btn btn-outline-secondary rounded-circle" data-toggle="collapse"
                                        data-target="#showProductInfo" aria-expanded="false"
                                        aria-controls="showProductInfo">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                            fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">
                                            <path
                                                d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </a>
    */
}

// KuanK's function - send feedback to server
function sendFeedback() {
    var xhttp = new XMLHttpRequest();
    content = "feedback_content=" + encodeURIComponent(document.getElementById("feedback").value);
    xhttp.open("POST", "SendFeedback", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send(content);
    document.getElementById("feedback").value = "";
    $('#createFeedback').modal('hide');
}

// KuanK's function - get cashier name from server
function getCashierName() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetCurrentName", true);
    xhttp.onload = function() {
        document.getElementById("cashier-name").innerHTML += this.responseText;
    };
    xhttp.send();
}

//KuanK function - get bill from server
function getBill() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetBill", true);
    xhttp.onload = function() {
        printBill(JSON.parse(this.responseText));
    };
    xhttp.send();
}

//KuanK function - render bill
function printBill(billObject) {
    document.getElementById('bill-area').innerHTML = "";
    for (i = 0; i < billObject.Bill_Detail.length; i++) {
        var detail = billObject.Bill_Detail[i]
        var tr = document.createElement("tr");

        var td_name = document.createElement("td");
        td_name.innerHTML = billObject.Bill_Detail[i].product.name;

        var td_selling_price = document.createElement("td");
        td_selling_price.setAttribute("class", "text-right pr-5 mr-5");
        td_selling_price.innerHTML = "<sup>đ</sup>" + detail.product.selling_price;

        var td_quantity = document.createElement("td");
        var input_quantity = document.createElement("input");
        input_quantity.setAttribute("style", "width: 50px");
        input_quantity.setAttribute("class", "text-center");
        input_quantity.setAttribute("type", "number");
        input_quantity.setAttribute("value", detail.quantity);
        input_quantity.setAttribute("min", "1");
        td_quantity.appendChild(input_quantity);

        var td_delete = document.createElement("td");
        td_delete.setAttribute("class", "text-center");
        var delete_button = document.createElement("button");
        delete_button.setAttribute("class", "btn btn-outline-danger btn-sm");
        delete_button.innerHTML = "XÓA";
        td_delete.appendChild(delete_button);

        var td_total = document.createElement("td");
        td_total.setAttribute("class", "text-right");
        td_total.innerHTML = "<sup>đ</sup>" + detail.quantity * detail.product.selling_price;

        tr.appendChild(td_name);
        tr.appendChild(td_selling_price);
        tr.appendChild(td_quantity);
        tr.appendChild(td_delete);
        tr.appendChild(td_total);

        document.getElementById('bill-area').appendChild(tr);
    }
    /* <tr>
<td>Bánh mì</td>s
<td class="text-right pr-5 mr-5"><sup>đ</sup>5000</td>
<td><input style="width: 50px;" class="text-center" type="number" value="1" min="1">
</td>

<td class="text-center"><button class="btn btn-outline-danger btn-sm">Xóa</button></td>
<td class="text-right"><sup>đ</sup>5000</td>
</tr> */
}

var category_id = null;
var productList;

function SearchProduct() {
    var name = document.getElementById("product-search-bar").value;
    url = "GetProductList?search_value=" + name;
    if (category_id != null) url += "&category_id=" + category_id;
    if (name == "") {
        productList = [];
        RenderProduct();
        console.log("name is empty!!!");
    } else {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", url, true);
        xhttp.onload = function() {
            productList = JSON.parse(this.responseText);
            //  console.log(productList);
            RenderProduct();
        };
        xhttp.send();
    }
}

function getCategory() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetCategoryList", true);
    xhttp.onload = function() {
        renderCategory(JSON.parse(this.responseText));
    };
    xhttp.send();
}

function renderCategory(categoryListObject) {
    document.getElementById('category-list').innerHTML = "";
    for (i = 0; i < categoryListObject.length; i++) {

        var li = document.createElement("li");
        li.setAttribute("class", "nav-item");
        var a = document.createElement("a");
        a.setAttribute("class", "nav-link");
        a.setAttribute("href", "#");
        a.innerHTML = categoryListObject[i].name;
        li.appendChild(a);
        document.getElementById('category-list').appendChild(li);
    }
    //     <li class="nav-item">
    //     <a href="#" class="nav-link">Tương ớt</a>
    // </li>
}

// KuanK's function
function pageLoadKuanK() {
    getCashierName();
    getBill();
    getCategory();
}