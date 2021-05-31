
var productList = [
    {
        id: 1,
        name: "Tương ớt chinsu 850gr",
        price: "20000",
        quantity: 10,
        location: "A"
    },
    {
        id: 2,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 3,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 4,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 5,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 6,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 7,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 8,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 9,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 10,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 11,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 12,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 13,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 14,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 15,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 16,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    },
    {
        id: 17,
        name: "Nước rửa chén Sunlight Extra chanh và bạc hà 3.48L",
        price: "30000",
        quantity: 20,
        location: "B"
    }
]

var previousProductInfo = "";
var ProductInfoDuration;

function SearchForProduct(id) {
    for (var index in productList) {
        var product = productList[index];
        if (id == product.id) {
            return product;
        }
    }
    return -1;
}

function ToggleProductInfoState(id) {
    if (previousProductInfo != id) {
        $("#showProductInfo")
            .removeClass("d-none")
            .hide() // hides it first, or style it with 'display: none;' instead
            .fadeIn(300);
    }

    if (previousProductInfo == id && previousProductInfo != "") {
        previousProductInfo = "";
        $("#showProductInfo")
            .fadeOut(300, function () {
                $("#showProductInfo").addClass("d-none");
            })
    }
    else {
        previousProductInfo = id;
        ProductInfoDuration = setTimeout(function () { ToggleProductInfoState(id); }, 9000);
    }
}

function ShowProductInfo(id) {
    if (ProductInfoDuration) {
        clearTimeout(ProductInfoDuration);
        ProductInfoDuration = 0;
    }
    ToggleProductInfoState(id);


    var product = SearchForProduct(id);
    if (product == -1) console.log("error! Can't find product");
    document.getElementById("product-info-name").innerHTML = product.name;
    document.getElementById("product-info-quantity").innerHTML = product.quantity;
    document.getElementById("product-info-price").innerHTML = "<sup>đ</sup>" + product.price;
    document.getElementById("product-info-location").innerHTML = product.location;

}


// Load all products and categories
$(document).ready(function () {

    // Load products
    var htmlList = document.getElementById("product-list");
    for (var index in productList) {
        var product = productList[index];
        htmlList.appendChild(createHTMLForEachProduct(product));
    }
});

function createHTMLForEachProduct(product) {
    var a = document.createElement("a");
    a.setAttribute("href", "#");
    a.setAttribute("class", "list-group-item list-group-item-action");
    var div1 = document.createElement("div");
    div1.setAttribute("class", "d-flex w-100 justify-content-between");

    var p = document.createElement("p");
    p.setAttribute("class", "w-50 my-auto");
    p.innerHTML = product.name;
    var h5 = document.createElement("h5");
    h5.setAttribute("class", "text-muted");
    h5.innerHTML = "<sup>đ</sup>" + product.price;

    var div2 = document.createElement("div");
    div2.setAttribute("class", "product-info-button");
    var button = document.createElement("button");
    button.setAttribute("id", product.id);
    button.setAttribute("onclick", "ShowProductInfo(" + product.id + ")");
    button.setAttribute("class", "btn btn-outline-secondary rounded-circle");
    button.innerHTML = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\"\n" +
        "                                            fill=\"currentColor\" class=\"bi bi-three-dots\" viewBox=\"0 0 16 16\">\n" +
        "                                            <path\n" +
        "                                                d=\"M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z\" />\n" +
        "                                        </svg>";

    div2.appendChild(button);
    div1.appendChild(p);
    div1.appendChild(h5);
    div1.appendChild(div2);
    a.appendChild(div1);

    return a;


    /*
     <a href="#" class="list-group-item list-group-item-action">
                            <div class="d-flex w-100 justify-content-between ">
                                <p class="w-50 my-auto">Tương ớt chinsu 850gr</p>
                                <h5 class="text-muted"><sup>đ</sup>30000</h5>
                                <div class="product-info-button">
                                    <button onclick="ShowProductInfo(1)" class="btn btn-outline-secondary rounded-circle" data-toggle="collapse"
                                        data-target="#showProductInfo" aria-expanded="false"
                                        aria-controls="showProductInfo">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                            fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">
                                            <path
                                                d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </a>
    */
}