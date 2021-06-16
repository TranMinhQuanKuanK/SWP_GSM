var productList = $('#product-list').DataTable({
    processing: true,
    responsive: true,
    ajax: {
        url: "GetProductList",
        type: "GET",
        dataSrc: "",
        data:
                function (d) {
                    if ($("#outOfStockItems").is(':checked'))
                    {
                        if ($("#category-list option:selected").val() !== "")
                            return $.extend(d, {
                                "category_id": $("#category-list option:selected").val(),
                                "search_value": $("#txtSearchProductName").val(),
                                "only_noos_items": $("#outOfStockItems").is(':checked')
                            });

                        return $.extend(d, {
                            "search_value": $("#txtSearchProductName").val(),
                            "only_noos_items": $("#outOfStockItems").is(':checked')
                        });
                    }
                    if ($("#category-list option:selected").val() !== "")
                        return $.extend(d, {
                            "category_id": $("#category-list option:selected").val(),
                            "search_value": $("#txtSearchProductName").val()
                        });

                    return $.extend(d, {
                        "search_value": $("#txtSearchProductName").val()
                    });
                }},
    columns: [
        {data: 'product_ID'},
        {data: 'name', render: function (data, type, row) {
                return data.normalize();
            }},
        {data: 'selling_price', render: $.fn.dataTable.render.number('.', '.', 0, '', 'đ')},
        {data: 'category.name'},
        {data: 'unit_label'},
        {data: 'is_selling', render: function (data) {
                return (data) ? "Bán" : "Không bán";
            }}
    ],
    createdRow: function (row, data, dataIndex, cells) {
        $(row).attr("id", $(cells[0]).text());
    },
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

    $("#txtSearchProductName").keyup(function () {
        productList.ajax.reload();
    });


});

var clickedProductID;
// Handle edit product

$('#product-list tbody').on('click', 'tr', function () {
    clickedProductID = ($(this).closest('tr').attr("id"));
    $('#editProductModal').modal("show");
});

$("#product-list tbody").css("cursor", "pointer");


$('#editProductModal').on('hide.bs.modal', function (e) {
    productList.$('tr.selected').removeClass('selected');
});

$('#editProductModal').on('show.bs.modal', function (e) {
    console.log(clickedProductID);
    $.ajax({
        url: "GetProductInfo",
        type: "GET",
        dataType: "json",
        data: {
            "clickedProductID": clickedProductID
        },
        success: function (data) {
            console.log(data);
            $("#edit-product-name").val(data.name);
            $("#edit-category-name").val(data.category.name);
            $("#edit-lower-threshold").val(data.lower_threshold);
            $("#edit-cost-price").val(eVietnam(data.cost_price));
            $("#edit-selling-price").val(eVietnam(data.selling_price));
            $("#edit-unit-label").val(data.unit_label);
            $("#edit-location").val(data.location);
            $("#edit-is-selling").prop("checked", data.is_selling);
        }
    });
});

function eVietnam(num) {
    console.log(num.toLocaleString('vi'));
    return num.toLocaleString('vi');
}

$('#edit-product-btn').on("click", function () {
    $(this).prop("disabled", true);

    toggleDisabledForProductInfo();
});


$('.edit-product-close-btn').on("click", function () {
    toggleDisabledForProductInfo();
});


$('#edit-product-save-btn').on("click", function () {
    if ($("#edit-product-name").val() === "" || $("#edit-category-name").val() === ""
            || $("#edit-lower-threshold").val() === "" || $("#edit-cost-price").val() === ""
            || $("#edit-selling-price").val() === "" || $("#edit-unit-label").val() === "") {
        $('#fail-to-save-toast').toast({
            delay: 2000
        });
        $('#fail-to-save-toast').toast('show');

    } else {
        $('#editProductModal').modal("hide");

        toggleDisabledForProductInfo();
    }
});

function toggleDisabledForProductInfo() {
    var isNotDisabled = !$('#edit-product-name').is(":disabled");

    $("#edit-product-name").prop("disabled", isNotDisabled);
    $("#edit-category-name").prop("disabled", isNotDisabled);
    $("#edit-lower-threshold").prop("disabled", isNotDisabled);
    $("#edit-cost-price").prop("disabled", isNotDisabled);
    $("#edit-selling-price").prop("disabled", isNotDisabled);
    $("#edit-unit-label").prop("disabled", isNotDisabled);
    $("#edit-location").prop("disabled", isNotDisabled);
    $("#edit-is-selling").prop("disabled", isNotDisabled);

    $('#edit-product-save-btn').prop("disabled", isNotDisabled);
    $('#edit-product-btn').prop("disabled", !isNotDisabled);
}


