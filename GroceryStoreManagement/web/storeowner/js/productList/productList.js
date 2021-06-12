var productList = null;
var select_el = document.getElementById("category-list");

function getProductList() {
    $.ajax({
        url: "GetProductList",
        type: "GET",
        dataType: "json",
        success: function (data) {
            productList = $('#product-list').DataTable({
                "processing": true,
                data: data,
                columns: [
                    {data: 'product_ID'},
                    {data: 'name'},
                    {data: 'quantity'},
                    {data: 'cost_price', render: $.fn.dataTable.render.number( ',', '.', 0, '','đ' )},
                    {data: 'selling_price', render: $.fn.dataTable.render.number( ',', '.', 0, '','đ' )},
                    {data: 'lower_threshold'},
                    {data: 'category.name'},
                    {data: 'unit_label'},
                    {data: 'is_selling'},
                    {data: 'location'}
                ],
                "language": {
                    "lengthMenu": "Xem _MENU_ hàng hóa mỗi trang",
                    "zeroRecords": "Không tìm thấy hàng hóa",
                    "info": "Đang xem trang thứ _PAGE_ trong _PAGES_ trang",
                    "infoEmpty": "Không có thông tin",
                    "infoFiltered": "(lọc từ _MAX_ lượng hàng hóa)",
                    "paginate": {
                        "first": "Đầu tiên",
                        "last": "Cuối",
                        "next": "Tiếp theo",
                        "previous": "Trước"
                    },
                    "search": "Tìm kiếm: "
                }
            });
            productList.columns('.name').search().draw();
        }
    });
}

function getCategoryList() {
    $.ajax({
        url: "GetCategoryList",
        type: "GET",
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                var json = data[i];
                var option_el = document.createElement(("option"));
                option_el.value = json.name;
                option_el.textContent = json.name;
                console.log(option_el);
                select_el.appendChild(option_el);
            }
        }
    });
}


$(document).ready(function () {
    getCategoryList();
    getProductList();
});


$("#outOfStockItems").on('change', function () {
    if ($(this).is(':checked')) {
        $.fn.dataTable.ext.search.push(
                function (settings, data, dataIndex) {
                    return parseInt(data[2]) <= parseInt(data[5]);
                }
        );
    } else {
        $.fn.dataTable.ext.search.pop();
    }
    productList.draw();
});

$("#category-list").on('change', function () {
    var selected = $("#category-list option:selected").text().normalize('NFD');
    $.fn.dataTable.ext.search.pop();

    if (selected !== "Chọn...".normalize('NFD')) {
        $.fn.dataTable.ext.search.push(
                function (settings, data, dataIndex) {
                    console.log(data[6].normalize('NFD'));
                    console.log(selected);
                    return data[6].normalize('NFD') === selected;
                }
        );
    } else {
        $.fn.dataTable.ext.search.pop();
    }
    productList.draw();
});

