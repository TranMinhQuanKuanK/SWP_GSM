var productList = $('#product-list').DataTable({
    processing: true,
    responsive: true,
    ajax: {
        url: "GetProductList",
        type: "GET",
        dataSrc: "",
        data:
                function (d) {
                    return $.extend(d, {
                        "category_id": $("#category-list option:selected").val(),
                        "search_value": $("#txtSearchProductName").val(),
                        "only_noos_items": $("#outOfStockItems").is(':checked')
                    });
                }},                
    columns: [
        {data: 'product_ID'},
        {data: 'name', render: function (data, type, row) {
                return data.normalize();
            }},
        {data: 'cost_price', render: $.fn.dataTable.render.number('.', '.', 0, '', 'đ')},
        {data: 'selling_price', render: $.fn.dataTable.render.number('.', '.', 0, '', 'đ')},
        {data: 'quantity'},
        {data: 'lower_threshold'},
        {data: 'category.name'},
        {data: 'unit_label'},
        {data: 'is_selling', render: function (data) {
                return (data) ? "Bán" : "Không bán"
            }},
        {data: 'location'}
    ],
    columnDefs: [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
    order: [[1, 'asc']],
    language: {
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
    dom: 'lrtip'
});

productList.on('order.dt search.dt', function () {
    productList.column(0, {order: 'applied', search: 'applied'}).nodes().each(function (cell, i) {
        cell.innerHTML = i + 1;
    });
});


var select_el = document.getElementById("category-list");

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
            select_el.appendChild(option_el);
        }
    }
});


$(document).ready(function () {
    productList.draw();

    $("#category-list, #outOfStockItems").on('change', function () {
        productList.ajax.reload();
    });
    
    $("#txtSearchProductName").keyup(function() {
        productList.ajax.reload();
    });
});


