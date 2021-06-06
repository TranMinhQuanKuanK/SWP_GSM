var previousProductInfo = "";
var ProductInfoDuration;
var category_id = null; //selected category
var productList; //story list ProductDTO
//var currentCustomer; //store current CustomerDTO
var currentBill; //store current billObj

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

function createHTMLForEachProduct(product) {
    var a = document.createElement("a");
    a.setAttribute("class", "list-group-item list-group-item-action");
    a.setAttribute("onclick", "AddProductToBill(" + product.product_ID + ")");

    var div1 = document.createElement("div");
    div1.setAttribute("class", "d-flex w-100 justify-content-between");

    var p = document.createElement("p");
    p.setAttribute("class", "w-50 my-auto");
    p.innerHTML = product.name;

    var h5 = document.createElement("h5");
    h5.setAttribute("class", "text-muted");
    h5.innerHTML = "<sup>đ</sup>" + formatNumber(product.selling_price) + "/" + product.unit_label;

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

// KuanK's function - get cashier name from server and render ít
function getCashierName() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetCurrentName", true);
    xhttp.onload = function() {
        document.getElementById("cashier-name").innerHTML += this.responseText;
    };
    xhttp.send();
}

//KuanK function - get bill from server and render it
function getBill() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetBill", true);
    xhttp.onload = function() {
        currentBill = JSON.parse(this.responseText);
        printBill(currentBill);
    };
    xhttp.send();
}

//KuanK function - render bill
function printBill(billObject) {
    document.getElementById('bill-area').innerHTML = "";
    for (i = 0; i < billObject.Bill_Detail.length; i++) {
        var detail = billObject.Bill_Detail[i]
        var tr = document.createElement("tr");

        var th_index = document.createElement("th");
        th_index.setAttribute("scope", "row");
        th_index.innerHTML = i + 1;

        var td_name = document.createElement("td");
        td_name.innerHTML = detail.product.name;

        var td_selling_price = document.createElement("td");
        td_selling_price.setAttribute("class", "text-right");
        td_selling_price.innerHTML = "<sup>đ</sup>" + formatNumber(detail.product.selling_price);

        var td_quantity = document.createElement("td");
        var input_quantity = document.createElement("input");
        input_quantity.setAttribute("class", "text-right float-right");
        input_quantity.setAttribute("id", "quantity-for-product-" + detail.product.product_ID);
        input_quantity.setAttribute("type", "number");
        input_quantity.setAttribute("value", detail.quantity);
        input_quantity.setAttribute("min", "1");
        input_quantity.setAttribute("onchange", "EditQuantityBill(" + detail.product.product_ID + ")"); //muốn lấy một giá trị 
        td_quantity.appendChild(input_quantity);


        var td_delete = document.createElement("td");
        td_delete.setAttribute("class", "text-right");
        var delete_button = document.createElement("button");
        delete_button.setAttribute("class", "btn btn-outline-danger btn-sm");
        delete_button.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"' +
            'fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">' +
            '<path ' +
            'd="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z">' +
            '</path>' +
            '</svg>';
        delete_button.setAttribute("onclick", "RemoveProductFromBill(" + detail.product.product_ID + ")");
        td_delete.appendChild(delete_button);

        var td_total = document.createElement("td");
        td_total.setAttribute("class", "text-right");
        td_total.innerHTML = "<sup>đ</sup>" + formatNumber(detail.quantity * detail.product.selling_price);

        tr.appendChild(th_index);
        tr.appendChild(td_name);
        tr.appendChild(td_selling_price);
        tr.appendChild(td_quantity);
        tr.appendChild(td_delete);
        tr.appendChild(td_total);

        document.getElementById('bill-area').appendChild(tr);
    }

    //tính toán discount
    currentCustomer = currentBill.customer_dto;

    var discount;
    if (currentBill.use_point == true) {
        console.log("Current bill use point: " + currentBill.use_point);
        if (Math.ceil(currentBill.total_cost / 1000) < currentCustomer.point)
            discount = Math.ceil(currentBill.total_cost / 1000) * 1000;
        else discount = currentCustomer.point * 1000;
    } else discount = 0;

    var total_cost_after_discount = Math.max(0, billObject.total_cost - discount);

    document.getElementById('total').innerHTML = "<sup>đ</sup>" + formatNumber(billObject.total_cost);
    document.getElementById('discount').innerHTML = "<del><sup>đ</sup>" + formatNumber(discount) + "</del>";
    document.getElementById('total-after-discount').innerHTML = "<strong><sup>đ</sup>" + formatNumber(total_cost_after_discount) + "</strong>";

    renderCustomer();


    /* <tr>
                                    <th scope="row">1</td>
                                    <td>Tương ớt</td>
                                    <td class="text-right"><sup>đ</sup>10000</td>
                                    <td ><input class="text-right float-right" type="number" value="3"
                                            min="1">
                                    </td>

                                    <td class="text-right">
                                        <button class="btn btn-outline-danger btn-sm">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                                <path
                                                    d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z">
                                                </path>
                                            </svg>
                                        </button>
                                    </td>
                                    
                                    <td class="text-right"><sup>đ</sup>30000</td>
                                </tr> */

}

//KuanK - search and render product results
function SearchProduct() {
    var name = document.getElementById("product-search-bar").value;
    url = "GetProductList?search_value=" + name;
    if (category_id != null) url += "&category_id=" + category_id;
    if (name == "" && category_id == null) {
        productList = [];
        RenderProduct();
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

//KuanK đã sửa - render product list
function RenderProduct() {
    // Load products
    var htmlList = document.getElementById("product-list");
    htmlList.innerHTML = "";
    for (i = 0; i < productList.length; i++) {
        htmlList.appendChild(createHTMLForEachProduct(productList[i]));
    }
}
//KuanK - get and render category
function getCategory() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "GetCategoryList", true);
    xhttp.onload = function() {
        renderCategory(JSON.parse(this.responseText));
    };
    xhttp.send();
}
//render category
function renderCategory(categoryListObject) {
    document.getElementById('category-list').innerHTML = "";
    //create "All" category
    var li = document.createElement("li");
    li.setAttribute("class", "nav-item");
    li.setAttribute("onclick", "setCategoryAndSearch(" + null + ")");
    var a = document.createElement("a");
    a.setAttribute("class", "nav-link");
    a.setAttribute("href", "#");
    a.innerHTML = "(Tất cả)";
    li.appendChild(a);
    document.getElementById('category-list').appendChild(li);
    //for loop create all
    for (i = 0; i < categoryListObject.length; i++) {

        var li = document.createElement("li");
        li.setAttribute("class", "nav-item");
        li.setAttribute("onclick", "setCategoryAndSearch(" + categoryListObject[i].category_ID + ")");
        var a = document.createElement("a");
        a.setAttribute("class", "nav-link");
        a.setAttribute("href", "#");
        a.innerHTML = categoryListObject[i].name;
        li.appendChild(a);
        document.getElementById('category-list').appendChild(li);
    }
    //     <li class="nav-item" onclick="setCategory(id)">
    //     <a href="#" class="nav-link">Tương ớt</a>
    // </li>
}

function setCategoryAndSearch(id) {
    category_id = id;
    SearchProduct();
}

function searchCustomerByPhone() {

    var xhttp = new XMLHttpRequest();
    var phone_no = document.getElementById("phone-no-input").value;
    xhttp.open("GET", "GetCustomerByPhone?phone_no=" + phone_no, true);
    xhttp.onload = function() {
        //nếu kết quả trả về null thì thôi, còn nếu khác null thì in ra
        result_dto = JSON.parse(this.responseText);
        if (result_dto != null) {
            currentBill.customer_dto = result_dto;
            printBill(currentBill);
        }
    };
    xhttp.send();
}

function renderCustomer() {
    currentCustomer = currentBill.customer_dto;
    // console.log(currentBill.customer_dto);
    if (currentCustomer != null) {
        document.getElementById("customer-name").innerHTML = " " + currentCustomer.name + ", điểm: " + currentCustomer.point + " điểm";
        //set attribute cho ô giảm giá
        document.getElementById("discount-checkbox").checked = (currentBill.use_point == true);
    }
}

function AddProductToBill(product_ID) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "AddProductToBill?product_id=" + product_ID, true);
    xhttp.onload = function() {
        // console.log(this.responseText); //debug
        currentBill = JSON.parse(this.responseText);
        printBill(currentBill);
    };
    xhttp.send();
}

function RemoveProductFromBill(product_ID) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "RemoveProductFromBill?product_id=" + product_ID, true);
    xhttp.onload = function() {
        //console.log(this.responseText); //debug
        currentBill = JSON.parse(this.responseText);
        printBill(currentBill);
    };
    xhttp.send();
}

function usePointToggle() {
    //set use_point state
    var use_point;
    if (document.getElementById("discount-checkbox").checked == true) {
        use_point = "true";
    } else use_point = "false";

    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "ToggleDiscount?use_point=" + use_point, true);
    xhttp.onload = function() {
        console.log(this.responseText); //debug
        currentBill = JSON.parse(this.responseText);
        printBill(currentBill);
    };
    xhttp.send();
}

function calculateDiscount() {
    //trả về discount để in ra bill
    currentCustomer = currentBill.customer_dto;
    if (currentBill.use_point == true) {
        if (Math.floor(currentBill.total_cost / 1000) < currentCustomer.point)
            return Math.floor(currentBill.total_cost / 1000) * 1000;
        else return currentCustomer.point * 1000;
    } else return 0;
}

function EditQuantityBill(product_id) {
    var xhttp = new XMLHttpRequest();
    quantity = document.getElementById("quantity-for-product-" + product_id).value;
    xhttp.open("GET", "EditQuantityBill?product_id=" + product_id + "&quantity=" + quantity, true);
    xhttp.onload = function() {
        console.log(this.responseText); //debug
        currentBill = JSON.parse(this.responseText);
        printBill(currentBill);
    };
    xhttp.send();
}

function formatNumber(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
// KuanK's function
function pageLoadKuanK() {
    getCashierName();
    getBill();
    getCategory();
}