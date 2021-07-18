(function ($) {
    "use strict"; // Start of use strict

    // Toggle the side navigation
    $("#sidebarToggle, #sidebarToggleTop").on('click', function (e) {
        $("body").toggleClass("sidebar-toggled");
        $(".sidebar").toggleClass("toggled");
        if ($(".sidebar").hasClass("toggled")) {
            $('.sidebar .collapse').collapse('hide');
        }
        ;
    });

    // Close any open menu accordions when window is resized below 768px
    $(window).resize(function () {
        if ($(window).width() < 768) {
            $('.sidebar .collapse').collapse('hide');
        }
        ;

        // Toggle the side navigation when window is resized below 480px
        if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
            $("body").addClass("sidebar-toggled");
            $(".sidebar").addClass("toggled");
            $('.sidebar .collapse').collapse('hide');
        }
        ;
    });

    // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
    $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function (e) {
        if ($(window).width() > 768) {
            var e0 = e.originalEvent,
                    delta = e0.wheelDelta || -e0.detail;
            this.scrollTop += (delta < 0 ? 1 : -1) * 30;
            e.preventDefault();
        }
    });

    // Scroll to top button appear
    $(document).on('scroll', function () {
        var scrollDistance = $(this).scrollTop();
        if (scrollDistance > 100) {
            $('.scroll-to-top').fadeIn();
        } else {
            $('.scroll-to-top').fadeOut();
        }
    });

    // Smooth scrolling using jQuery easing
    $(document).on('click', 'a.scroll-to-top', function (e) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: ($($anchor.attr('href')).offset().top)
        }, 1000, 'easeInOutExpo');
        e.preventDefault();
    });

})(jQuery); // End of use strict

function openPage(pageName, elmnt, pageBtn) {
    // Hide all elements with class="tabcontent" by default 
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Remove the background color of all previous active button
    tablinks = document.getElementsByClassName("tablink-btn");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "#fff";
        tablinks[i].style.opacity = 0.7;
        tablinks[i].classList.add("border");
        tablinks[i].classList.add("border-bottom-0");
        tablinks[i].classList.add("border-primary");
        tablinks[i].childNodes[1].style.color = "#4e73df";
        tablinks[i].childNodes[3].style.color = "#4e73df";
    }

    // Show the specific tab content
    document.getElementById(pageName).style.display = "block";

    // Add the specific color to the button used to open the tab content
    document.getElementById(pageBtn).childNodes[1].style.color = "#fff";
    document.getElementById(pageBtn).childNodes[3].style.color = "#fff";
    document.getElementById(pageBtn).style.backgroundColor = "#4e73df";
    document.getElementById(pageBtn).style.opacity = 1;
}

// Get the element with id="defaultOpen" and click on it
if (document.getElementById("defaultOpen") !== null) {
    document.getElementById("defaultOpen").click();
}

function formatNumber(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function format(time) {
    return (time < 10 ? '0' : '') + time;
}

function formatDateTime(original) {
    var normalizedDate = "";
    if (original.length > 7) {
        normalizedDate += original.substring(8, 10) + '/';
    }
    normalizedDate += original.substring(5, 7) + '/';
    normalizedDate += original.substring(0, 4);
    return normalizedDate;
}

function getTime() {
    var now = new Date();

    var year = now.getFullYear();
    var month = format(now.getMonth() + 1);
    var day = format(now.getDate());
    var hour = format(now.getHours());
    var minute = format(now.getMinutes());
    var second = format(now.getSeconds());
    var preMonth;

    if (now.getMonth() === 0) {
        preMonth = 12;
        year = year - 1;
    } else {
        preMonth = format(now.getMonth());
    }

    //Set datetime-type elements
    var dateTo = document.getElementsByClassName("datetime-date-to");
    for (i = 0; i < dateTo.length; i++) {
        dateTo[i].value = [year, month, day].join('-') + 'T' + [hour, minute, second].join(':');
        dateTo[i].max = dateTo[i].value;
    }
    var dateFrom = document.getElementsByClassName("datetime-date-from");
    for (i = 0; i < dateFrom.length; i++) {
        dateFrom[i].value = [year, preMonth, day].join('-') + 'T' + [hour, minute, second].join(':');
        dateFrom[i].max = dateTo[i].value;
    }

    //Set date-type elements
    var dateTo = document.getElementsByClassName("date-date-to");
    for (i = 0; i < dateTo.length; i++) {
        dateTo[i].value = [year, month, day].join('-');
        dateTo[i].max = dateTo[i].value;
    }
    var dateFrom = document.getElementsByClassName("date-date-from");
    for (i = 0; i < dateFrom.length; i++) {
        dateFrom[i].value = [year, preMonth, day].join('-');
        dateFrom[i].max = dateTo[i].value;
    }

    //Set month-type elements
    var dateTo = document.getElementsByClassName("month-date-to");
    for (i = 0; i < dateTo.length; i++) {
        dateTo[i].value = [year, month].join('-');
        dateTo[i].max = dateTo[i].value;
    }
    var dateFrom = document.getElementsByClassName("month-date-from");
    for (i = 0; i < dateFrom.length; i++) {
        dateFrom[i].value = [year - 1, month].join('-');
        dateFrom[i].max = dateTo[i].value;
    }
}