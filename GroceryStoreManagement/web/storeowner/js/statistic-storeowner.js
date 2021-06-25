var statTable;

function processError(errorObject) {
    document.getElementById("error-date").innerHTML = errorObject.dateError;
}

function showProductStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetProductStatistic";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            processError(result);
        } else {
            renderProductStatistic(result);
        }
    };
    request.send();
}

function renderProductStatistic(productStatistic) {
    $(document).ready(function () {
        statTable = $('#product-stat-area').DataTable({
            destroy: true,
            processing: true,
            data: productStatistic,
            columns: [{
                    data: null
                }, {
                    data: 'productName'
                }, {
                    data: 'quantity'
                }, {
                    data: 'total', render: $.fn.dataTable.render.number('.', '.', 0, '', '')
                }],
            columnDefs: [{
                    "orderable": false,
                    "targets": 0
                }, {
                    "searchable": false,
                    "targets": [0, 2, 3]
                }],
            order: [[1, 'asc']],
            language: {
                "lengthMenu": "_MENU_ sản phẩm mỗi trang",
                "zeroRecords": "Không tìm thấy sản phẩm nào",
                "info": "Trang _PAGE_ trong tổng số _PAGES_ trang",
                "infoEmpty": "Không có thông tin",
                "infoFiltered": "(lọc từ _MAX_ sản phẩm)",
                "paginate": {
                    "first": "Trang đầu",
                    "last": "Trang cuối",
                    "next": "Trước",
                    "previous": "Tiếp"
                },
                "search": "Tên sản phẩm: "
            }
        });

        statTable.on('order.dt', function () {
            statTable.column(0, {order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).draw();
    });
}

function showCustomerStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetCustomerStatistic";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            processError(result);
        } else {
            renderCustomerStatistic(result);
        }
    };
    request.send();
}

function renderCustomerStatistic(CustomerStatistic) {
    $(document).ready(function () {
        statTable = $('#product-stat-area').DataTable({
            destroy: true,
            processing: true,
            data: CustomerStatistic,
            columns: [{
                    data: null
                }, {
                    data: 'productName'
                }, {
                    data: 'quantity'
                }, {
                    data: 'total', render: $.fn.dataTable.render.number('.', '.', 0, '', '')
                }],
            columnDefs: [{
                    "orderable": false,
                    "targets": 0
                }, {
                    "searchable": false,
                    "targets": [0, 2, 3]
                }],
            order: [[1, 'asc']],
            language: {
                "lengthMenu": "_MENU_ sản phẩm mỗi trang",
                "zeroRecords": "Không tìm thấy sản phẩm nào",
                "info": "Trang _PAGE_ trong tổng số _PAGES_ trang",
                "infoEmpty": "Không có thông tin",
                "infoFiltered": "(lọc từ _MAX_ sản phẩm)",
                "paginate": {
                    "first": "Trang đầu",
                    "last": "Trang cuối",
                    "next": "Trước",
                    "previous": "Tiếp"
                },
                "search": "Tên sản phẩm: "
            }
        });

        statTable.on('order.dt', function () {
            statTable.column(0, {order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).draw();
    });
}

function showFinancialStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetFinancialStatistic";
    url += "?date-from=" + document.getElementById("date-from").value;
    url += "&date-to=" + document.getElementById("date-to").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            processError(result);
        } else {
            renderFinancialStatistic(result);
        }
    };
    request.send();
}

function renderFinancialStatistic(financialStatistic) {
    $(document).ready(function () {
        statTable = $('#product-stat-area').DataTable({
            destroy: true,
            processing: true,
            data: financialStatistic,
            columns: [{
                    data: null
                }, {
                    data: 'productName'
                }, {
                    data: 'quantity'
                }, {
                    data: 'total', render: $.fn.dataTable.render.number('.', '.', 0, '', '')
                }],
            columnDefs: [{
                    "orderable": false,
                    "targets": 0
                }, {
                    "searchable": false,
                    "targets": [0, 2, 3]
                }],
            order: [[1, 'asc']],
            language: {
                "lengthMenu": "_MENU_ sản phẩm mỗi trang",
                "zeroRecords": "Không tìm thấy sản phẩm nào",
                "info": "Trang _PAGE_ trong tổng số _PAGES_ trang",
                "infoEmpty": "Không có thông tin",
                "infoFiltered": "(lọc từ _MAX_ sản phẩm)",
                "paginate": {
                    "first": "Trang đầu",
                    "last": "Trang cuối",
                    "next": "Trước",
                    "previous": "Tiếp"
                },
                "search": "Tên sản phẩm: "
            }
        });

        statTable.on('order.dt', function () {
            statTable.column(0, {order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).draw();
    });
}

