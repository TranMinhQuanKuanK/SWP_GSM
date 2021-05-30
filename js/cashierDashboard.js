







// // Load all products and categories
// $(document).ready(function () {

//     /*
//         THIS IS WHEN U CAN INTERACT WITH DB
//     */
//     $.ajax({
//         url: "/SWP_GSM/GetCategoryList",
//         type: "GET",
//         success: function (data) {
//             renderHTMLForCategory(data);
//         }
//     });

//     $.ajax({
//         url: "/SearchDemo/GetProductList",
//         type: "GET",
//         success: function (data) {
//             renderHTMLForProduct(data);
//         }
//     });


//     /*
//         THIS IS WHEN U HAVE RAW JSON TO TEST ON FRONTEND HAHA :(
//     */
// });



// // Search for Product list based on user search input
// function SearchProduct(param) {
//     var txtSearch = param.value;

//     /*
//         THIS IS WHEN U CAN INTERACT WITH DB
//     */
//     $.ajax({
//         url: "/SWP_GSM/SearchProduct",
//         type: "GET",
//         data: {
//             txtSearch: txtSearch
//         },
//         success: function (data) {
//             renderHTMLForProduct(data);
//         }
//     });


//     /*
//         THIS IS WHEN U HAVE RAW JSON TO TEST ON FRONTEND HAHA :(
//     */

// }


// function renderHTMLForCategory(data) {
//     var html = "";
//     for (var int in data) {
//         var json = data[int];
//         html += "<li class=\"nav-item\">\n"
//             + "         <a class=\"nav-link p-2\" href=\"#\">\n"
//             + "             <span data-feather=\"file-text\">" + json.name + "</span>\n"
//             + "         </a>\n"
//             + "    </li>";
//     }
//     document.getElementById("category-list").innerHTML = html;
// }

// function renderHTMLForProduct(data) {
//     var html = "";
//     for (var int in data) {
//         var json = data[int];
//         html += "<li class=\"nav-item\">\n"
//             + "         <a class=\"nav-link p-2\" href=\"#\">\n"
//             + "             <span data-feather=\"file-text\">" + json.name + "</span>\n"
//             + "         </a>\n"
//             + "    </li>";
//     }
//     document.getElementById("product-list").innerHTML = html;
// }
