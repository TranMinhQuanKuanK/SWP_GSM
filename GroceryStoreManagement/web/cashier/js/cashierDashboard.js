var previousProductInfo = "";
var ProductInfoDuration;
var category_id = null; //selected category
var productList = null; //story list ProductDTO
//var currentCustomer; //store current CustomerDTO biến này được gắn vào billObj
var currentBill; //store current billObj
const productList_element = document.getElementById("product-list"); //biến của thành
const pagination_element = document.getElementById("page-selection");
var accountErrObj;
var customerErrObj;

function SearchForProduct(id) {
  for (i = 0; i < productList.length; i++) {
    if (productList[i].product_ID == id) return productList[i];
  }
  return -1;
}

function ToggleProductInfoState(id) {
  if (previousProductInfo != id) {
    $("#product-info")
      .removeClass("d-none")
      .hide() // hides it first, or style it with 'display: none;' instead
      .fadeIn(300);
  }

  if (previousProductInfo == id && previousProductInfo != "") {
    previousProductInfo = "";
    $("#product-info").fadeOut(300, function () {
      $("#product-info").addClass("d-none");
    });
  } else {
    previousProductInfo = id;
    ProductInfoDuration = setTimeout(function () {
      ToggleProductInfoState(id);
    }, 9000);
  }
}

function DisplayProductInfo(id) {
  if (ProductInfoDuration) {
    clearTimeout(ProductInfoDuration);
    ProductInfoDuration = 0;
  }
  ToggleProductInfoState(id);

  let product = SearchForProduct(id);
  if (product == -1) console.log("error! Can't find product");
  document.getElementById("product-info-name").innerHTML = product.name;
  document.getElementById("product-info-quantity").innerHTML = product.quantity;
  document.getElementById("product-info-price").innerHTML =
    product.selling_price;
  document.getElementById("product-info-location").innerHTML = product.location;
}

function createHTMLForEachProduct(product) {
  let tr_el = document.createElement("tr");
  let td_el_name = document.createElement("td");
  let td_el_price = document.createElement("td");
  let td_el_info = document.createElement("td");
  let btn_el = document.createElement("button");

  td_el_name.setAttribute("class", "product-detail align-middle");
  td_el_name.setAttribute(
    "onclick",
    "AddProductToBill(" + product.product_ID + ")"
  );
  td_el_name.setAttribute("style", "cursor:pointer;");

  td_el_price.setAttribute("class", "product-detail align-middle");
  td_el_price.setAttribute("style", "cursor:pointer;");
  td_el_price.setAttribute(
    "onclick",
    "AddProductToBill(" + product.product_ID + ")"
  );
  td_el_name.textContent = product.name;
  td_el_price.textContent =
    formatNumber(product.selling_price) + "/" + product.unit_label;

  td_el_info.setAttribute("class", "text-right");
  btn_el.setAttribute("id", product.product_ID);
  btn_el.setAttribute(
    "onclick",
    "DisplayProductInfo(" + product.product_ID + ")"
  );
  btn_el.setAttribute("class", "btn btn-outline-secondary rounded-circle");

  // Cái này style tooltip cho button, hover là thấy hint á
  btn_el.setAttribute("data-toggle", "tooltip");
  btn_el.setAttribute("data-placement", "top");
  btn_el.setAttribute("title", "Chi tiết sản phẩm");

  btn_el.innerHTML =
    '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"\n' +
    '                                            fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">\n' +
    "                                            <path\n" +
    '                                                d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />\n' +
    "                                        </svg>";

  if (product.quantity <= product.lower_threshold && product.quantity > 0) {
    tr_el.setAttribute("class", "product-low-quantity text-black");
    btn_el.setAttribute("class", "btn btn-outline-light rounded-circle");
  } else if (product.quantity == 0) {
    tr_el.setAttribute("class", "product-out-of-stock text-white");
    btn_el.setAttribute("class", "btn btn-outline-light rounded-circle");
  }

  td_el_info.appendChild(btn_el);
  tr_el.appendChild(td_el_name);
  tr_el.appendChild(td_el_price);
  tr_el.appendChild(td_el_info);

  return tr_el;
}

// KuanK's function - send feedback to server
function sendFeedback() {
  var xhttp = new XMLHttpRequest();
  content =
    "feedback_content=" +
    encodeURIComponent(document.getElementById("feedback").value);
  xhttp.open("POST", "SendFeedback", true);
  xhttp.setRequestHeader(
    "Content-Type",
    "application/x-www-form-urlencoded;charset=UTF-8"
  );
  xhttp.send(content);
  document.getElementById("feedback").value = "";
  $("#createFeedback").modal("hide");
}

// KuanK's function - get cashier name from server and render ít
function getCashierName() {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "GetCurrentName", true);
  xhttp.onload = function () {
    document.getElementById("cashier-name").innerHTML += this.responseText;
  };

  //gửi request
  xhttp.send();
}

//KuanK function - get bill from server and render it
function getBill() {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "GetBill", true);
  xhttp.onload = function () {
    currentBill = JSON.parse(this.responseText);
    printBill(currentBill);
  };
  xhttp.send();
}

//KuanK function - render bill
function printBill(billObject) {
  //in danh sách bill
  document.getElementById("bill-area").innerHTML = "";
  for (i = 0; i < billObject.Bill_Detail.length; i++) {
    var detail = billObject.Bill_Detail[i];
    var tr = document.createElement("tr");

    var th_index = document.createElement("th");
    th_index.setAttribute("scope", "row");
    th_index.innerHTML = i + 1;

    var td_name = document.createElement("td");
    td_name.innerHTML = detail.product.name;

    var td_selling_price = document.createElement("td");
    td_selling_price.setAttribute("class", "text-right");
    td_selling_price.innerHTML = formatNumber(detail.product.selling_price);

    var td_quantity = document.createElement("td");
    var input_quantity = document.createElement("input");
    input_quantity.setAttribute("class", "text-right float-right");
    input_quantity.setAttribute(
      "id",
      "quantity-for-product-" + detail.product.product_ID
    );
    input_quantity.setAttribute("type", "number");
    input_quantity.setAttribute("value", detail.quantity);
    input_quantity.setAttribute("min", "1");
    input_quantity.setAttribute(
      "onchange",
      "EditQuantityBill(" + detail.product.product_ID + ")"
    ); //muốn lấy một giá trị
    td_quantity.appendChild(input_quantity);

    var td_delete = document.createElement("td");
    td_delete.setAttribute("class", "text-right");
    var delete_button = document.createElement("button");
    delete_button.setAttribute("class", "btn btn-outline-danger btn-sm");
    delete_button.innerHTML =
      '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"' +
      'fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">' +
      "<path " +
      'd="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z">' +
      "</path>" +
      "</svg>";
    delete_button.setAttribute(
      "onclick",
      "RemoveProductFromBill(" + detail.product.product_ID + ")"
    );
    td_delete.appendChild(delete_button);

    var td_total = document.createElement("td");
    td_total.setAttribute("class", "text-right");
    td_total.innerHTML = formatNumber(
      detail.quantity * detail.product.selling_price
    );

    tr.appendChild(th_index);
    tr.appendChild(td_name);
    tr.appendChild(td_selling_price);
    tr.appendChild(td_quantity);
    tr.appendChild(td_delete);
    tr.appendChild(td_total);

    document.getElementById("bill-area").appendChild(tr);
  }

  //tính toán discount
  currentCustomer = currentBill.customer_dto;

  var discount;
  if (currentBill.use_point == true) {
    //console.log("Current bill use point: " + currentBill.use_point);
    if (Math.ceil(currentBill.total_cost / 1000) < currentCustomer.point)
      discount = Math.ceil(currentBill.total_cost / 1000) * 1000;
    else discount = currentCustomer.point * 1000;
  } else discount = 0;

  var total_cost_after_discount = Math.max(0, billObject.total_cost - discount);
  //in ra discount/ đơn giá/
  document.getElementById("total").innerHTML = formatNumber(
    billObject.total_cost
  );
  document.getElementById("discount").innerHTML =
    "<del>" + formatNumber(discount) + "</del>";
  document.getElementById("total-after-discount").innerHTML =
    "<strong>" + formatNumber(total_cost_after_discount) + "</strong>";
  //in ra tên customer
  renderCustomer();

  //thông báo lỗi
  if (currentBill.err_obj.hasError == true) display_Bill_ErrorMessage();
}

function printPreviewBill(billObject) {
  //in tên và điểm
  document.getElementById(
    "bill-preview-customer-name"
  ).innerHTML = document.getElementById("customer-name").innerHTML;

  document.getElementById(
    "bill-preview-customer-point"
  ).innerHTML = document.getElementById("point-of-customer").innerHTML;
  //in bảng bill
  document.getElementById("bill-preview-area").innerHTML = "";
  for (i = 0; i < billObject.Bill_Detail.length; i++) {
    var detail = billObject.Bill_Detail[i];
    var tr = document.createElement("tr");

    var th_index = document.createElement("th");
    th_index.setAttribute("scope", "row");
    th_index.innerHTML = i + 1;

    var td_name = document.createElement("td");
    td_name.innerHTML = detail.product.name;

    var td_selling_price = document.createElement("td");
    td_selling_price.setAttribute("class", "text-right");
    td_selling_price.innerHTML = formatNumber(detail.product.selling_price);

    var td_quantity = document.createElement("td");
    td_quantity.setAttribute("class", "text-right");
    td_quantity.innerHTML = detail.quantity;

    var td_total = document.createElement("td");
    td_total.setAttribute("class", "text-right");
    td_total.innerHTML = formatNumber(
      detail.quantity * detail.product.selling_price
    );

    tr.appendChild(th_index);
    tr.appendChild(td_name);
    tr.appendChild(td_selling_price);
    tr.appendChild(td_quantity);
    tr.appendChild(td_total);

    document.getElementById("bill-preview-area").appendChild(tr);
  }
  //in phần thành tiền/giảm giá/tổng tiền/khách đưa:
  document.getElementById(
    "bill-preview-total"
  ).innerHTML = document.getElementById("total").innerHTML;

  document.getElementById(
    "bill-preview-discount"
  ).innerHTML = document.getElementById("discount").innerHTML;

  document.getElementById(
    "bill-preview-total-after-discount"
  ).innerHTML = document.getElementById("total-after-discount").innerHTML;

  document.getElementById("bill-preview-cash").innerHTML =
    document.getElementById("cash").value == ""
      ? "...Chưa nhập..."
      : formatNumber(document.getElementById("cash").value);
  $("#bill-preview-modal").modal("show");
}
function display_Bill_ErrorMessage() {
  errorObj = currentBill.err_obj;
  //clear error area
  document.getElementById("bill-error-element").innerHTML = "";
  for (i = 0; i < errorObj.error_list.length; i++) {
    var li = document.createElement("li");
    li.innerHTML = errorObj.error_list[i];
    document.getElementById("bill-error-element").appendChild(li);
  }
  $("#bill-error-modal").modal("show");
  currentBill.err_obj.hasError = false;
}
//KuanK - search and render product results
function SearchProduct() {
  //lấy tên từ thanh search gán vào name
  var name = document.getElementById("product-search-bar").value;
  url = "GetProductList?search_value=" + name;
  //nếu category khác null thì thêm vào query string
  if (category_id != null) url += "&category_id=" + category_id;
  //nếu không có tên và category_id = tất cả (null) thì cho productList là rỗng
  if (name == "" && category_id == null) {
    productList = [];
    RenderProduct();
  } else {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url, true);
    xhttp.onload = function () {
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
  // var ProducthtmlList = document.getElementById("product-list");
  // ProducthtmlList.innerHTML = "";
  // for (i = 0; i < productList.length; i++) {
  //   ProducthtmlList.appendChild(createHTMLForEachProduct(productList[i]));
  // }

  DisplayProductList(
    productList,
    rows_per_page,
    productList_element,
    current_page
  );
  SetupPagination(productList, pagination_element, rows_per_page);
}
//KuanK - get and render category
function getCategory() {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "GetCategoryList", true);
  xhttp.onload = function () {
    renderCategory(JSON.parse(this.responseText));
  };
  xhttp.send();
}
//render category
function renderCategory(categoryListObject) {
  document.getElementById("category-list").innerHTML = "";
  //create "All" category
  var tr = document.createElement("tr");
  tr.setAttribute("onclick", "setCategoryAndSearch(event," + null + ")");
  var td = document.createElement("td");
  td.innerHTML = "(Tất cả)";
  td.setAttribute("class", "bg-secondary");
  tr.appendChild(td);
  document.getElementById("category-list").appendChild(tr);

  //for loop create all
  for (i = 0; i < categoryListObject.length; i++) {
    var tr = document.createElement("tr");
    tr.setAttribute(
      "onclick",
      "setCategoryAndSearch(event," + categoryListObject[i].category_ID + ")"
    );
    var td = document.createElement("td");
    td.innerHTML = categoryListObject[i].name;
    tr.appendChild(td);
    document.getElementById("category-list").appendChild(tr);
  }

  //   <tr>
  //                                             <td>Haha</td>
  //                                         </tr>
}

function setCategoryAndSearch(event, id) {
  //In đậm các category được click
  $("#category-list tr td").removeClass("bg-secondary");
  event.target.setAttribute("class", "bg-secondary");
  category_id = id;
  SearchProduct();
}

function searchCustomerByPhone() {
  var xhttp = new XMLHttpRequest();
  var phone_no = document.getElementById("phone-no-input").value;
  xhttp.open("GET", "GetCustomerByPhone?phone_no=" + phone_no, true);
  xhttp.onload = function () {
    //nếu kết quả trả về null thì thôi, còn nếu khác null thì in ra
    result_dto = JSON.parse(this.responseText);
    if (result_dto != null) {
      currentBill.customer_dto = result_dto;
      printBill(currentBill);
    } else if (result_dto == null) {
      alert("Không tìm thấy khách hàng tương ứng!");
    }
  };
  xhttp.send();
}

function renderCustomer() {
  currentCustomer = currentBill.customer_dto;
  // console.log(currentBill.customer_dto);
  if (currentCustomer != null) {
    document.getElementById("customer-name").innerHTML = currentCustomer.name;

    document.getElementById("point-of-customer").innerHTML =
      currentCustomer.point;
    //set attribute cho ô giảm giá
    document.getElementById("discount-checkbox").checked =
      currentBill.use_point == true;

    document.getElementById("phone-no-input").value = "";
  } else if (currentCustomer == null) {
    document.getElementById("customer-name").innerHTML = "Khách hàng vãng lai";
    document.getElementById("point-of-customer").innerHTML = "...";
    document.getElementById("discount-checkbox").checked =
      currentBill.use_point == false;
    document.getElementById("phone-no-input").value = "";
  }
}

function AddProductToBill(product_ID) {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "AddProductToBill?product_id=" + product_ID, true);
  xhttp.onload = function () {
    // console.log(this.responseText); //debug
    currentBill = JSON.parse(this.responseText);
    printBill(currentBill);
  };
  xhttp.send();
}

function RemoveProductFromBill(product_ID) {
  var xhttp = new XMLHttpRequest();
  xhttp.open("GET", "RemoveProductFromBill?product_id=" + product_ID, true);
  xhttp.onload = function () {
    //console.log(this.responseText); //debug
    currentBill = JSON.parse(this.responseText);
    printBill(currentBill);
  };
  xhttp.send();
}

function usePointToggle() {
  //set use_point state
  var customer_dto = currentBill.customer_dto;
  if (customer_dto != null) {
    var use_point;
    if (document.getElementById("discount-checkbox").checked == true) {
      use_point = "true";
    } else use_point = "false";

    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "ToggleDiscount?use_point=" + use_point, true);
    xhttp.onload = function () {
      //console.log(this.responseText); //debug
      currentBill = JSON.parse(this.responseText);

      printBill(currentBill);
    };
    xhttp.send();
  }
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
  quantity = document.getElementById("quantity-for-product-" + product_id)
    .value;
  xhttp.open(
    "GET",
    "EditQuantityBill?product_id=" + product_id + "&quantity=" + quantity,
    true
  );
  xhttp.onload = function () {
    //console.log(this.responseText); //debug
    currentBill = JSON.parse(this.responseText);
    printBill(currentBill);
  };
  xhttp.send();
}

function formatNumber(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function passwordChange() {
  function clearAllError() {
    document.getElementById("current-password-error").innerHTML = "";
    document.getElementById("new-password-error").innerHTML = "";
    document.getElementById("confirm-password-error").innerHTML = "";
  }
  function clearAllInput() {
    document.getElementById("currentPassword").value = "";
    document.getElementById("newPassword").value = "";
    document.getElementById("confirmNewPassword").value = "";
  }
  clearAllError();

  var currentPassword = document.getElementById("currentPassword").value;
  var newPassword = document.getElementById("newPassword").value;
  var confirmNewPassword = document.getElementById("confirmNewPassword").value;
  if (currentPassword.length > 1) {
    if (confirmNewPassword != newPassword) {
      document.getElementById("confirm-password-error").innerHTML =
        "Mật khẩu xác nhận không trùng khớp";
    } else {
      //gửi request về nhận về object lỗi
      var xhttp = new XMLHttpRequest();

      content =
        "currentPassword=" +
        encodeURIComponent(document.getElementById("currentPassword").value) +
        "&newPassword=" +
        encodeURIComponent(document.getElementById("newPassword").value);

      xhttp.open("POST", "ChangePasswordCashier", true);
      xhttp.setRequestHeader(
        "Content-Type",
        "application/x-www-form-urlencoded;charset=UTF-8"
      );
      xhttp.onload = function () {
        accountErrObj = JSON.parse(this.responseText);
        processError();
      };
      xhttp.send(content);
      //console.log(accountErrObj); //debug
      // xem in ra lỗi hay đóng modal
      function processError() {
        if (accountErrObj.hasError == true) {
          clearAllError();
          if (accountErrObj.currentPasswordError.length > 2) {
            document.getElementById("current-password-error").innerHTML =
              accountErrObj.currentPasswordError;
          }
          if (accountErrObj.newPasswordError.length > 2) {
            document.getElementById("new-password-error").innerHTML =
              accountErrObj.newPasswordError;
          }
        } else {
          //đóng modal
          clearAllError();
          clearAllInput();
          $("#changePassword").modal("hide");
        }
      }
    }
  }
}

function RegisterCustomer() {
  function clearAllError() {
    document.getElementById("customer-name-error").innerHTML = "";
    document.getElementById("phone-no-error").innerHTML = "";
  }
  function clearAllInput() {
    document.getElementById("customerName").value = "";
    document.getElementById("customerPhoneNo").value = "";
  }
  var customerName = document.getElementById("customerName").value;
  var customerPhoneNo = document.getElementById("customerPhoneNo").value;
  if (customerName.length == 0) {
    document.getElementById("customer-name-error").innerHTML =
      "Tên không được bỏ trống";
  } else {
    var xhttp = new XMLHttpRequest();

    content =
      "phone_no=" +
      encodeURIComponent(customerPhoneNo) +
      "&name=" +
      encodeURIComponent(customerName);

    xhttp.open("POST", "CreateNewCustomer", true);
    xhttp.setRequestHeader(
      "Content-Type",
      "application/x-www-form-urlencoded;charset=UTF-8"
    );
    xhttp.onload = function () {
      customerErrObj = JSON.parse(this.responseText);
      processError();
    };
    xhttp.send(content);
    function processError() {
      if (customerErrObj.has_Error == true) {
        clearAllError();
        if (customerErrObj.phone_noError.length > 2) {
          document.getElementById("phone-no-error").innerHTML =
            customerErrObj.phone_noError;
        }
      } else {
        //đóng modal
        clearAllError();
        clearAllInput();
        $("#registerCustomer").modal("hide");
      }
    }
  }
}

function Checkout() {
  if (currentBill.total_cost == 0) {
    alert("Chưa mua gì mà bấm thanh toán????? Bị khùng hả?");
  } else {
    var cash = document.getElementById("cash").value;
    var xhttp = new XMLHttpRequest();

    xhttp.open("GET", "Checkout?cash=" + cash, true);
    xhttp.onload = function () {
      // customerErrObj = JSON.parse(this.responseText);
      clearBill();
      getBill();
      document.getElementById("cash").value = "";
      $("#bill-preview-modal").modal("hide");
    };
    xhttp.send();
    //clear bill, đóng modal
    function clearBill() {
      // printBill(customerErrObj);
    }
  }
}

// KuanK's function
function pageLoadKuanK() {
  getCashierName();
  getBill();
  getCategory();
}

/* ====================================
    DISPLAY PAGINATED LIST OF PRODUCTS 
    ===================================
 */

let current_page = 1;
let rows_per_page = 10;
let buttons_per_page = 3;

function DisplayProductList(products, rows_per_page, wrapper, page) {
  wrapper.innerHTML = "";
  page--;

  let start = rows_per_page * page;
  let end = start + rows_per_page;
  let paginatedProducts = products.slice(start, end);

  for (let i = 0; i < paginatedProducts.length; i++) {
    let product = paginatedProducts[i];
    wrapper.appendChild(createHTMLForEachProduct(product));
  }
}

function SetupPagination(products, wrapper, rows_per_page) {
  wrapper.innerHTML = "";

  // Create disabled three dots button
  let disabled_btn = document.createElement("button");
  disabled_btn.setAttribute(
    "class",
    "btn btn-light btn-sm btn-disabled d-none"
  );
  disabled_btn.disabled = true;

  let page_counter = Math.ceil(products.length / rows_per_page);
  for (let i = 1; i < page_counter + 1; i++) {
    let btn = PaginationButton(i, products);
    wrapper.appendChild(btn);

    if (i == 1 || i == page_counter - 1) {
      let clone_disabled_btn = disabled_btn.cloneNode();
      clone_disabled_btn.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">
            <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z"/>
          </svg>`;
      wrapper.appendChild(clone_disabled_btn);
    }
  }

  DisplayPagination();
}

function PaginationButton(page, products) {
  let button = document.createElement("button");
  button.setAttribute("class", "btn btn-sm btn-outline-warning");
  button.innerText = page;

  if (current_page == page) button.classList.add("active");
  button.addEventListener("click", function () {
    current_page = page;
    DisplayProductList(
      products,
      rows_per_page,
      productList_element,
      current_page
    );

    let current_btn = document.querySelector("#page-selection button.active");
    current_btn.classList.remove("active");
    button.classList.add("active");
    DisplayPagination();
  });

  return button;
}

function DisplayPagination() {
  // Get the list of buttons to handle
  let buttons = pagination_element.querySelectorAll(
    "button:not(.btn-disabled)"
  );

  // Get the active button
  let current_btn = pagination_element.querySelector("button.active");
  let disabled_btns = pagination_element.querySelectorAll(
    "button.btn-disabled"
  );

  for (let i = 0; i < buttons.length; i++) {
    let btn = buttons[i];
    //console.log(parseInt(btn.textContent));
    if (i >= current_btn.textContent - 2 && i <= current_btn.textContent) {
      btn.classList.remove("d-none");
    } else btn.classList.add("d-none");

    buttons[0].classList.remove("d-none");
    buttons[buttons.length - 1].classList.remove("d-none");
  }
  if (buttons.length > 2) {
    if (buttons[1].classList.contains("d-none")) {
      disabled_btns[0].classList.remove("d-none");
    } else disabled_btns[0].classList.add("d-none");
    if (buttons[buttons.length - 2].classList.contains("d-none")) {
      disabled_btns[1].classList.remove("d-none");
    } else disabled_btns[1].classList.add("d-none");
  }
}

// DisplayProductList(
//   productList,
//   rows_per_page,
//   productList_element,
//   current_page
// );

//SetupPagination(productList, pagination_element, rows_per_page);

/*
    MAKE EACH ROW OF TABLE CATEGORY ACTIVE
*/

// $('#product-list tr td.product-detail').click(function() {
//     $('#product-list tr').removeClass('bg-success');
//     $(this).parent().addClass('bg-success');
// });
//
//
//
//
//
//
//
//
