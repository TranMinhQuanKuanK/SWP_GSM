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
  defaultOps.setAttribute("value", "all");
  defaultOps.innerHTML = "(*)";
  document.getElementById("inputGroupSelect01").appendChild(defaultOps);
  for (i = 0; i < data.length; i++) {
    var selectOps = document.createElement("option");
    selectOps.setAttribute("value", i + 2);
    selectOps.innerHTML = data[i].name;
    document.getElementById("inputGroupSelect01").appendChild(selectOps);
  }
}

var productObject;
function getProduct() {
  productObject = null;
  var xhttp = new XMLHttpRequest();
  var cat_ID = document.getElementById("inputGroupSelect01").value;
  var search_val = document.getElementById("inputSearchVal").value;
  var noos = document.getElementById("noos_check");
  xhttp.onload = function () {
      console.log(JSON.parse(this.responseText));
      productObject = JSON.parse(this.responseText);
      printProductList(productObject);
  };
  
  if (noos.checked == true) {
    if (cat_ID === "all") {
      var url =
        "GetProductList?search_value=" + search_val + "&only_noos_items=1";
    } else {
      var url =
        "GetProductList?search_value=" +
        search_val +
        "&category_id=" +
        cat_ID +
        "&only_noos_items=1";
    }
  } else {
    if (cat_ID === "all") {
      var url = "GetProductList?search_value=" + search_val;
    } else {
      var url =
        "GetProductList?search_value=" + search_val + "&category_id=" + cat_ID;
    }
  }

  xhttp.open("GET", url, false);
  xhttp.send();
}

function printProductList(data) {
  document.getElementById("tableContent").innerHTML = "";
  var index = 0;
  for (i = 0; i < data.length; i++) {
    if (data[i].is_selling !== false) {
      index++;
      var tr = document.createElement("tr");


      var th_index = document.createElement("th");
      th_index.setAttribute("scope", "row");
      th_index.innerHTML = index;

      var td_name = document.createElement("td");
      td_name.innerHTML = data[i].name;

      var td_category = document.createElement("td");
      td_category.innerHTML = data[i].category.name;

      var td_threshold = document.createElement("td");
      td_threshold.innerHTML = data[i].lower_threshold;
 
      var td_quantity = document.createElement("td");
      td_quantity.innerHTML = data[i].quantity;

      if (data[i].lower_threshold >= data[i].quantity) {
        tr.className = "red-row";
      }

      var td_button = document.createElement("td");
      var Add_bt = document.createElement("input");
      Add_bt.setAttribute("type", "button");
      Add_bt.setAttribute("value", "Add to to-import list");
      var Edit_bt = document.createElement("input");
      Edit_bt.setAttribute("type", "button");
      Edit_bt.setAttribute("value", "...");
      Edit_bt.setAttribute("id", data[i].product_ID);
      Edit_bt.setAttribute("data-toggle", "modal");
      Edit_bt.setAttribute("data-target", "#editModal");
      Edit_bt.onclick = function () {
        setUpModal(this.getAttribute("id"));
      };
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
    }
  } // finish printing a product detail row
}

function setUpModal(productID) {
  for (i = 0; i < productObject.length; i++) {
    if (productObject[i].product_ID == productID) {
      document
        .getElementById("productname")
        .setAttribute("value", productObject[i].name);
      document
        .getElementById("product-oldquantity")
        .setAttribute("value", productObject[i].quantity);
      document
        .getElementById("hiddenProductID")
        .setAttribute("value", productObject[i].product_ID);
      break;
    }
  }
}

function updateQuantity() {
  var xhttp = new XMLHttpRequest();
  content =
    "product_ID=" +
    encodeURIComponent(document.getElementById("hiddenProductID").value) +
    "&new_quantity=" +
    encodeURIComponent(document.getElementById("product-newquantity").value);
  xhttp.open("POST", "UpdateQuantity", false);
  xhttp.setRequestHeader(
    "Content-Type",
    "application/x-www-form-urlencoded;charset=UTF-8"
  );
  xhttp.send(content);
  document.getElementById("product-newquantity").value = "";
  $("#editModal").modal("hide");
  getProduct();
}
