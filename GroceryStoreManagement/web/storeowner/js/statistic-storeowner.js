var statTable;
google.charts.load('current', {packages: ['corechart', 'line']});
var viewBy, customInput, period, dateFrom, dateTo;

function setTitle() {
    if (viewBy === "custom") {
        period.innerHTML = "giai đoạn " + formatDateTime(dateFrom.value) + " - " + formatDateTime(dateTo.value);
    } else {
        var currentMonth = new Date().getMonth() + 1;
        var currentYear = new Date().getFullYear();
        var curentQuarter = (new Date().getMonth() / 3 + 1);

        if (viewBy === "month") {
            period.innerHTML = "tháng " + currentMonth + "/" + currentYear;
        }
        if (viewBy === "quarter") {
            period.innerHTML = "quý " + curentQuarter + "/" + currentYear;
        }
        if (viewBy === "year") {
            period.innerHTML = "năm " + currentYear;
        }
    }
}

function prepareInfo(statCriterion) {
    if (viewBy === "custom") {
        customInput.style.display = "block";
    } else {
        customInput.style.display = "none";

        if (statCriterion === "financial") {
            if (viewBy === "month") {
                dateFrom.value = dateTo.value.substring(0, 7);
            }
            if (viewBy === "quarter") {
                var firstMonthOfQuarter = format((new Date().getMonth()) / 3 * 3 + 1);
                dateFrom.value = dateTo.value.substring(0, 5) + firstMonthOfQuarter;
            }
            if (viewBy === "year") {
                dateFrom.value = dateTo.value.substring(0, 5) + "01";
            }
        } else {
            if (viewBy === "month") {
                dateFrom.value = dateTo.value.substring(0, 8) + "01";
            }
            if (viewBy === "quarter") {
                var firstMonthOfQuarter = format((new Date().getMonth()) / 3 * 3 + 1);
                dateFrom.value = dateTo.value.substring(0, 5) + firstMonthOfQuarter + "-01";
            }
            if (viewBy === "year") {
                dateFrom.value = dateTo.value.substring(0, 5) + "01-01";
            }
        }
    }
}

function handleOnchange(statCriterion) {
    getTime();

    viewBy = document.getElementById("view-by-" + statCriterion).value;
    dateFrom = document.getElementById("date-from-" + statCriterion);
    dateTo = document.getElementById("date-to-" + statCriterion);
    period = document.getElementById("period-" + statCriterion);
    customInput = document.getElementById("custom-input-" + statCriterion);

    prepareInfo(statCriterion);

    if (true) {
        if (statCriterion === "product") {
            showProductStatistic();
        } else if (statCriterion === "customer") {
            showCustomerStatistic();
        } else if (statCriterion === "financial") {
            showFinancialStatistic();
        }
    }
}

function showProductStatistic() {
    var request = new XMLHttpRequest();

    var url = "GetProductStatistic";
    url += "?date-from=" + document.getElementById("date-from-product").value;
    url += "&date-to=" + document.getElementById("date-to-product").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            alert(result.dateError);
        } else {
            setTitle();
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
                    data: 'total', render: $.fn.dataTable.render.number('.', '.', 0, '', 'đ')
                }],
            columnDefs: [{
                    "targets": [2, 3],
                    "className": "text-gray-900 dt-body-right"
                },
                {
                    "targets": [1],
                    "className": "text-gray-900 dt-body-left"
                }, {
                    "orderable": false,
                    "targets": 0
                },
                {
                    "searchable": false,
                    "targets": [0, 2, 3]
                }, {
                    "targets": "_all",
                    "className": "text-gray-900"
                }],
            order: [[3, 'desc']],
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
    url += "?date-from=" + document.getElementById("date-from-customer").value;
    url += "&date-to=" + document.getElementById("date-to-customer").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            alert(result.dateError);
        } else {
            setTitle();
            renderCustomerStatistic(result);
        }
    };
    request.send();
}

function renderCustomerStatistic(CustomerStatistic) {
    $(document).ready(function () {
        statTable = $('#customer-stat-area').DataTable({
            destroy: true,
            processing: true,
            data: CustomerStatistic,
            columns: [{
                    data: null
                }, {
                    data: 'customerName'
                }, {
                    data: 'phoneNum'
                }, {
                    data: 'quantity'
                }, {
                    data: 'total', render: $.fn.dataTable.render.number('.', '.', 0, '', 'đ')
                }],
            columnDefs: [{
                    "targets": [3, 4],
                    "className": "text-gray-900 dt-body-right"
                },
                {
                    "targets": [1],
                    "className": "text-gray-900 dt-body-left"
                }, {
                    "orderable": false,
                    "targets": [0, 2]
                }, {
                    "searchable": false,
                    "targets": [0, 3, 4]
                }, {
                    "targets": "_all",
                    "className": "text-gray-900"
                }],
            order: [[4, 'desc']],
            language: {
                "lengthMenu": "_MENU_ khách hàng mỗi trang",
                "zeroRecords": "Không tìm thấy khách hàng nào",
                "info": "Trang _PAGE_ trong tổng số _PAGES_ trang",
                "infoEmpty": "Không có thông tin",
                "infoFiltered": "(lọc từ _MAX_ khách hàng)",
                "paginate": {
                    "first": "Trang đầu",
                    "last": "Trang cuối",
                    "next": "Trước",
                    "previous": "Tiếp"
                },
                "search": "Tên/SĐT khách hàng: "
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
    url += "?date-from=" + document.getElementById("date-from-financial").value;
    url += "&date-to=" + document.getElementById("date-to-financial").value;

    request.open('GET', url, true);
    request.onload = function () {
        var result = JSON.parse(this.responseText);
        if (result.isError) {
            alert(result.dateError);
        } else {
            setTitle();
            renderFinancialStatistic(result);
            showChart();
        }
    };
    request.send();
}

function renderFinancialStatistic(financialStatistic) {
//    document.getElementById("bill-count").innerHTML = financialStatistic.countBill;
//    document.getElementById("receipt-count").innerHTML = financialStatistic.countReceipt;
    document.getElementById("sum-revenue").innerHTML = eVietnam(financialStatistic.sumRevenue);
    document.getElementById("sum-cost").innerHTML = eVietnam(financialStatistic.sumCost);
    document.getElementById("sum-profit").innerHTML = eVietnam(financialStatistic.sumProfit);
    document.getElementById("financial-stat-area").style.display = "block";
}

function showChart() {
    var request = new XMLHttpRequest();

    var url = "GetFinancialChart";
    url += "?date-from=" + document.getElementById("date-from-financial").value;
    url += "&date-to=" + document.getElementById("date-to-financial").value;

    request.open('GET', url, true);
    request.onload = function () {
        var chartData = JSON.parse(this.responseText);
        renderChart(chartData);
    };
    request.send();
}

function renderChart(chartData) {
    google.charts.setOnLoadCallback(drawCurveTypes(chartData));
}

function drawCurveTypes(chartData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'X');
    data.addColumn('number', 'Tổng giá vốn');
    data.addColumn('number', 'Doanh thu');
    data.addColumn('number', 'Lợi nhuận');

    var items = new Array(chartData.events.length);

    for (var i = 0; i < items.length; i++) {
        items[i] = new Array(4);
        items[i] = [chartData.events[i], chartData.cost[i], chartData.revenue[i], chartData.profit[i]];
    }

    if (document.getElementById("empty-row-display").value === "hide") {
        var emptyItems = new Array();
        
        while (items[0][1] === 0 && items[0][2] === 0 && items[0][3] === 0) {
            emptyItems.push(items[0][0]);
            items.shift();
        }
        if (emptyItems.length !== 0) {
            alert("Giai đoạn " + emptyItems[0] + " - " + emptyItems[emptyItems.length - 1]
                    + " không có dữ liệu nên sẽ không được hiển thị trong biểu đồ");
        }
    }

    data.addRows(items);

    var options = {
        title: 'Doanh thu và lợi nhuận trong giai đoạn '
                + items[0][0] + ' - ' + items[items.length - 1][0]
                + ' (đvt: VND)',
        fontName: 'Nunito',
        fontSize: 17,
        height: 400,
        hAxis: {
            textStyle: {
                fontSize: 12
            }
        },
        vAxis: {
            textStyle: {
                fontSize: 12
            },
            viewWindowMode: 'pretty'
        },
        legend: {
            position: 'right',
            textStyle: {
                fontSize: 12
            }
        },
        series: {
            0: {
                color: '#dc3912'
            },
            1: {
                color: '#3366cc'
            },
            2: {
                color: '#1cc88a'
            }
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
    setTimeout(centerChartTitle, 10);
}


function eVietnam(num) {
    return num.toLocaleString('vi', {style: 'currency', currency: 'VND'});
}

function centerChartTitle() {
    var width = window.innerWidth
            || document.documentElement.clientWidth
            || document.body.clientWidth;
    var sideBarWidth = document.getElementById("perfect-scrollbar").offsetWidth || document.getElementById("perfect-scrollbar").clientWidth;
    var chartTitle = document.querySelector('#chart_div > div > div:nth-child(1) > div > svg > g:nth-child(3) > text');
    var centerXAxis = width / 2 - chartTitle.innerHTML.length - sideBarWidth - 110;
    chartTitle.setAttribute("x", centerXAxis);
}