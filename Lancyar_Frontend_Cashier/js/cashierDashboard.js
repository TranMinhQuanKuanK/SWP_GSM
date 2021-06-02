const productList = [
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
const productList_element = document.getElementById("product-list");
const pagination_element = document.getElementById("page-selection");


function createHTMLForEachProduct(product) {
    let a = document.createElement("a");
    a.setAttribute("href", "#");
    a.setAttribute("class", "list-group-item list-group-item-action product-width");

    let div1 = document.createElement("div");
    div1.setAttribute("class", "d-flex w-100 justify-content-between");

    let p = document.createElement("p");
    p.setAttribute("class", "w-50 my-auto");
    p.innerHTML = product.name;

    let h5 = document.createElement("h5");
    h5.setAttribute("class", "text-muted");
    h5.innerHTML = "<sup>đ</sup>" + product.price;

    let div2 = document.createElement("div");
    div2.setAttribute("class", "product-info-button");

    let button = document.createElement("button");
    button.setAttribute("id", product.id);
    button.setAttribute("onclick", "DisplayProductInfo(" + product.id + ")");
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
}

/* ===========================================================
    DISPLAY PRODUCT INFO WHEN CLICKING THE 'THREE DOTS' 
    ===========================================================
 */
let previousProductInfo = "";
let ProductInfoDuration;

function SearchForProduct(id) {
    for (let index in productList) {
        let product = productList[index];
        if (id == product.id) {
            return product;
        }
    }
    return -1;
}

function ToggleProductInfoState(id) {
    if (previousProductInfo != id) {
        $("#product-info")
            .removeClass("d-none")
            .hide() // hides it first, or style it with 'display: none;' instead
            .fadeIn(300);
    }

    if (previousProductInfo == id && previousProductInfo != "") {
        previousProductInfo = "";
        $("#product-info")
            .fadeOut(300, function () {
                $("#product-info").addClass("d-none");
            })
    }
    else {
        previousProductInfo = id;
        ProductInfoDuration = setTimeout(function () { ToggleProductInfoState(id); }, 9000);
    }
}

function DisplayProductInfo(id) {
    if (ProductInfoDuration) {
        clearTimeout(ProductInfoDuration);
        ProductInfoDuration = 0;
    }
    ToggleProductInfoState(id);


    let product = SearchForProduct(id);
    if (product == -1) console.log("error! Can't find product");
    document.getElementById("product-info-name").innerHTML = product.name;
    document.getElementById("product-info-quantity").innerHTML = product.quantity;
    document.getElementById("product-info-price").innerHTML = "<sup>đ</sup>" + product.price;
    document.getElementById("product-info-location").innerHTML = product.location;

}


/* ====================================
    DISPLAY PAGINATED LIST OF PRODUCTS 
    ===================================
 */
let current_page = 1;
let rows_per_page = 10;

function DisplayProductList(products, rows_per_page, wrapper, page) {
    wrapper.innerHTML = "";
    page--;

    let start = rows_per_page * page;
    let end = start + rows_per_page;
    let paginatedProducts = products.slice(start,end);
    
    for (let i = 0; i < paginatedProducts.length; i++) {
        let product = paginatedProducts[i];

        wrapper.appendChild(createHTMLForEachProduct(product));
    }
}

function SetupPagination(products, wrapper, rows_per_page) {
    wrapper.innerHTML = "";

    let page_counter = Math.ceil(products.length / rows_per_page);
    for (let i = 1; i < page_counter + 1; i++) {
        let btn = PaginationButton(i, products);
        wrapper.appendChild(btn);
    }
}

function PaginationButton(page, products) {
    let button = document.createElement("button");
    button.setAttribute("class", "btn btn-sm btn-outline-warning");
    button.innerText = page;

    if (current_page == page) button.classList.add("active");
    button.addEventListener("click", function() {
        current_page = page;
        DisplayProductList(products, rows_per_page, productList_element, current_page);

        let current_btn = document.querySelector("#page-selection button.active");
        current_btn.classList.remove("active");
        button.classList.add("active");
    })

    return button;
}

DisplayProductList(productList,rows_per_page,productList_element,current_page);
SetupPagination(productList, pagination_element, rows_per_page);
