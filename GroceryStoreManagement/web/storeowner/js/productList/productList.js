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
                    {data: 'name', render: function (data, type, row) {
                            return data.normalize();
                        }},
                    {data: 'cost_price', render: $.fn.dataTable.render.number(',', '.', 0, '', 'đ')},
                    {data: 'selling_price', render: $.fn.dataTable.render.number(',', '.', 0, '', 'đ')},
                    {data: 'quantity'},
                    {data: 'lower_threshold'},
                    {data: 'category.name'},
                    {data: 'unit_label'},
                    {data: 'is_selling'},
                    {data: 'location'}
                ],
                "columnDefs": [{
                        "searchable": false,
                        "orderable": false,
                        "targets": 0
                    }],
                "order": [[1, 'asc']],
//                "searching": false,
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
                },
                "dom": 'lrtip'
            });
            productList.on('order.dt search.dt', function () {
                productList.column(0, {order: 'applied', search: 'applied'}).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();
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
                option_el.value = json.category_ID;
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
    $.fn.dataTable.ext.search.push(
            function (settings, data, dataIndex) {
                var isOutOfStockSelected = $("#outOfStockItems").is(':checked');
                if (!isOutOfStockSelected)
                    return true;
                var isOutOfStock = parseInt(data[4]) <= parseInt(data[5]);
                return isOutOfStock;
            }
    );
    productList.draw();
});

$("#category-list").on('change', function () {
    var selected = $("#category-list option:selected").text().normalize();
    if (selected !== "Chọn...".normalize()) {
        productList.column(6).search(selected).draw();
    } else {
        productList.column(6).search("").draw();
    }
});

$('#txtSearchProductName').keyup(function () {
    /* Custom filtering function which will search data in column four between two values */
    var txtSearch = $(this).val().normalize();
    productList.column(1).search(txtSearch).draw();
});


