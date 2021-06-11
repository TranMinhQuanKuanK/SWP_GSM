

$(document).ready(function () {
    var data = [
        {
            "id" : 1,
            "name": "Đường túi 0.5kg",
            "quantity": 100,
            "cost_price": 10000,
            "selling_price": 12000,
            "threshold": 20,
            "category_id": 2,
            "label":"Túi",
            "is_selling":true,
            "location":"Mặc định",
        }
    ];
    $('#product-list').DataTable({
        "processing": true,
        data: data,
        columns: [
            { data: 'id' },
            { data: 'name' },
            { data: 'quantity' },
            { data: 'cost_price' },
            { data: 'selling_price' },
            { data: 'threshold' },
            { data: 'category_id' },
            { data: 'label' },
            { data: 'is_selling' },
            { data: 'location' }
        ]
    });
})
