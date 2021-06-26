var productList = null;
var select_el = document.getElementById("category-list");


$(document).ready(function () {
    $.get("Sidebar", function (data) {
        $("#sidebar-placeholder").replaceWith(data);
    });
    productList = $('#product-list').DataTable({
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
            "buttons": {
                pageLength: "Xem %d hàng hóa"
            },
            "lengthMenu": "Xem _MENU_ hàng hóa",
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
            "search": "Tìm kiếm: ",
            "processing": `<div class="spinner-border text-primary" role="status">
                            <span class="sr-only">Loading...</span>
                           </div>`
        },
        dom: 'lrtip',
        buttons: ['pageLength'],
//        lengthMenu: [
//            [10, 25, 50, 100],
//            ['10 hàng hóa', '25 hàng hóa', '50 hàng hóa', '100 hàng hóa']
//        ],
        initComplete: function () {
//            this.api().buttons().container()
//                    .appendTo('#length-menu');
            $('#product-list_length').appendTo('#length-menu');


        }
    });

    productList.on('order.dt search.dt', function () {
        productList.column(0, {order: 'applied', search: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    });

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
});

$("#category-list, #outOfStockItems").on('change', function () {
    setTimeout(function () {
        productList.ajax.reload();
    }, 500);
});

$("#txtSearchProductName").keyup(function () {
    setTimeout(function () {
        productList.ajax.reload();
    }, 500);
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
    $('#edit-category-name').html($('#category-list').clone().html());
    $('#edit-category-name option').first().remove();
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
            $("#edit-category-name").val(data.category.category_ID).prop("selected", true);
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
    toggleDisabledForProductInfo(false);
});


$('#editProductModal').on('hidden.bs.modal', function (e) {
    $('#edit-product-name-warning').text("").addClass("d-none");
    $('#edit-cost-price-warning').text("").addClass("d-none");
    $('#edit-selling-price-warning').text("").addClass("d-none");
    $('#edit-lower-threshold-warning').text("").addClass("d-none");
    toggleDisabledForProductInfo(true);
});


$('#edit-product-save-btn').on("click", function () {
    if ($("#edit-product-name").val() === ""
            || $("#edit-lower-threshold").val() === "" || $("#edit-cost-price").val() === ""
            || $("#edit-selling-price").val() === "") {
        $('#fail-to-save-toast').toast({
            delay: 2000
        });
        $('#fail-to-save-toast').toast('show');

    } else {
        console.log(clickedProductID);
        $.ajax({
            url: "EditProductInfo",
            type: "POST",
            dataType: "json",
            data: {
                "productID": clickedProductID,
                "productName": $("#edit-product-name").val().normalize(),
                "productCategoryID": $("#edit-category-name option:selected").val(),
                "productLowerThreshold": $("#edit-lower-threshold").val(),
                "productCostPrice": $("#edit-cost-price").val().replaceAll('.', ''),
                "productSellingPrice": $("#edit-selling-price").val().replaceAll('.', ''),
                "productUnitLabel": $("#edit-unit-label").val().normalize(),
                "productLocation": $("#edit-location").val().normalize(),
                "productIsSelling": $("#edit-is-selling").prop("checked")
            },
            success: function (data) {
                console.log(data);
                console.log("edit running");
                if (isEmpty(data)) {
                    console.log(" edit success running");
                    $('#editProductModal').modal("hide");
                    setTimeout(function () {
                        productList.ajax.reload();
                    }, 500);
                    $('#success-to-save-toast').toast({
                        delay: 2000
                    });
                    $('#success-to-save-toast').toast('show');
                    toggleDisabledForProductInfo(true);
                } else {
                    console.log("edit error running");
                    clearWarning("edit");
                    $('#edit-product-name-warning').text(data.nameErr).removeClass("d-none");
                    $('#edit-cost-price-warning').text(data.costPriceErr).removeClass("d-none");
                    $('#edit-selling-price-warning').text(data.sellingPriceErr).removeClass("d-none");
                    $('#edit-lower-threshold-warning').text(data.lowerThresholdErr).removeClass("d-none");
                    toggleDisabledForProductInfo(false);
                }
            }
        });

    }
});

// Format user input for currency
$("#add-cost-price, #add-selling-price, #edit-cost-price, #edit-selling-price").on("input", function () {
    /*
     * These additional lines prevent the function from running when the user 
     makes a selection within the input
     or presses the arrow keys on the keyboard
     */
    var selection = window.getSelection().toString();
    if (selection !== '') {
        return;
    }
    if ($.inArray(event.keyCode, [38, 40, 37, 39]) !== -1) {
        return;
    }
    // End of additional checks

    /*
     *  Retrieve the value from the input.
     Sanitize the value using RegEx by removing unnecessary characters such as spaces, underscores, dashes, and letters.
     Deploy parseInt() function to make sure the value is an integer (a round number).
     Add the thousand separator with the eVietnam() function, then pass the sanitised value back to the input element.
     */
    var input = $(this).val();
    var input = input.replace(/[\D\s\._\-]+/g, "");
    input = input ? parseInt(input, 10) : 0;
    $(this).val(function () {
        return (input === 0) ? "" : eVietnam(input);
    });
});

// Add new product
$('#addProductModal').on('show.bs.modal', function (e) {
    $('#add-category-name').html($('#category-list').clone().html());
    $('#add-category-name option').first().remove();
});

$('#addProductModal').on('hidden.bs.modal', function (e) {
    $('#add-product-name-warning').text("").addClass("d-none");
    $('#add-cost-price-warning').text("").addClass("d-none");
    $('#add-selling-price-warning').text("").addClass("d-none");
    $('#add-lower-threshold-warning').text("").addClass("d-none");
});

$('#add-product-save-btn').on("click", function () {
    if ($("#add-product-name").val() === "" || $("#add-lower-threshold").val() === ""
            || $("#add-cost-price").val() === "" || $("#add-selling-price").val() === "") {
        $('#fail-to-save-toast').toast({
            delay: 2000
        });
        $('#fail-to-save-toast').toast('show');

    } else {
        console.log($("#add-is-selling").prop("checked"));
        $.ajax({
            url: "AddNewProduct",
            type: "POST",
            dataType: "json",
            data: {
                "productName": $("#add-product-name").val().normalize(),
                "productCategoryID": $("#add-category-name option:selected").val(),
                "productLowerThreshold": $("#add-lower-threshold").val(),
                "productCostPrice": $("#add-cost-price").val().replaceAll('.', ''),
                "productSellingPrice": $("#add-selling-price").val().replaceAll('.', ''),
                "productUnitLabel": $("#add-unit-label").val().normalize(),
                "productLocation": $("#add-location").val().normalize(),
                "productIsSelling": $("#add-is-selling").prop("checked")
            },
            success: function (data) {
                console.log(data);
                if (isEmpty(data)) {
                    console.log("add running");
                    $('#addProductModal').modal("hide");
                    setTimeout(function () {
                        productList.ajax.reload();
                    }, 500);
                    $('#success-to-save-toast').toast({
                        delay: 2000
                    });
                    $('#success-to-save-toast').toast('show');

                    $("#add-product-name").val("");
                    $("#add-category-name option:selected").val("");
                    $("#add-lower-threshold").val("");
                    $("#add-cost-price").val("");
                    $("#add-selling-price").val("");
                    $("#add-unit-label").val("");
                    $("#add-location").val("");
                    $("#add-is-selling").prop("checked", false);
                } else {
                    clearWarning("add");
                    $('#add-product-name-warning').text(data.nameErr).removeClass("d-none");
                    $('#add-cost-price-warning').text(data.costPriceErr).removeClass("d-none");
                    $('#add-selling-price-warning').text(data.sellingPriceErr).removeClass("d-none");
                    $('#add-lower-threshold-warning').text(data.lowerThresholdErr).removeClass("d-none");
                }
            }
        });
    }
});

function clearWarning(value) {
    $(`#${value}-product-name-warning`).text("");
    $(`#${value}-cost-price-warning`).text("");
    $(`#${value}-selling-price-warning`).text("");
    $(`#${value}-lower-threshold-warning`).text("");
}

function toggleDisabledForProductInfo(bool) {
    var isNotDisabled = bool;

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


function isEmpty(obj) {
    for (var x in obj) {
        return false;
    }
    return true;
}

