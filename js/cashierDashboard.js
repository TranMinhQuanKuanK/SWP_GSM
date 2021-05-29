// Load all products and categories
$(document).ready(function() {
    document.body.style.zoom = 1.0

    /*
        THIS IS WHEN U CAN INTERACT WITH DB
    */
    // $.ajax({
    //     url: "/SWP_GSM/GetCategoryList",
    //     type: "GET",
    //     success: function (data) {
    //         renderHTMLForCategory(data);
    //     }
    // });

    // $.ajax({
    //     url: "/SearchDemo/GetProductList",
    //     type: "GET",
    //     success: function (data) {
    //         renderHTMLForProduct(data);
    //     }
    // });


    /*
        THIS IS WHEN U HAVE RAW JSON TO TEST ON FRONTEND HAHA :(
    */
    fetch("/public/productListTest.json")
        .then(response => {
            return response.json();
        })
        .then(data => renderHTMLForProduct(data));

    fetch("/public/categoryListTest.json")
        .then(response => {
            return response.json();
        })
        .then(data => renderHTMLForCategory(data));
});



// Search for Product list based on user search input
function SearchProduct(param) {
    var txtSearch = param.value;

    /*
        THIS IS WHEN U CAN INTERACT WITH DB
    */
    // $.ajax({
    //     url: "/SWP_GSM/SearchProduct",
    //     type: "GET",
    //     data: {
    //         txtSearch: txtSearch
    //     },
    //     success: function (data) {
    //         renderHTMLForProduct(data);
    //     }
    // });


    /*
        THIS IS WHEN U HAVE RAW JSON TO TEST ON FRONTEND HAHA :(
    */
    var list;
    fetch("../public/productListTest.json")
        .then(response => {
            return response.json();
        })
        .then(data => {
            return data.filter(product => {
                var name = JSON.stringify(product.name);
                return name.includes(txtSearch)
            });
        })
        .then(data => renderHTMLForProduct(data));

}


function renderHTMLForCategory(data) {
    var html = "";
    for (var int in data) {
        var json = data[int];
        html += "<li class=\"nav-item\">\n" +
            "         <a class=\"nav-link p-2\" href=\"#\">\n" +
            "             <span data-feather=\"file-text\">" + json.name + "</span>\n" +
            "         </a>\n" +
            "    </li>";
    }
    document.getElementById("category-list").innerHTML = html;
}

function renderHTMLForProduct(data) {
    var html = "";
    for (var int in data) {
        var json = data[int];
        html += "<li class=\"nav-item\">\n" +
            "         <a class=\"nav-link p-2\" href=\"#\">\n" +
            "             <span data-feather=\"file-text\">" + json.name + "</span>\n" +
            "         </a>\n" +
            "    </li>";
    }
    document.getElementById("product-list").innerHTML = html;
}