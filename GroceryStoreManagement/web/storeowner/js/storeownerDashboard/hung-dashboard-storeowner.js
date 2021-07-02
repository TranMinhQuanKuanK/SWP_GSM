/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = GetUnSeenFeedbackListDashboard();

function GetUnSeenFeedbackListDashboard(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            let feedbackObject = JSON.parse(this.responseText);
            processUnSeenFeedbackDashboard(feedbackObject);
        }
    };
    
    xhttp.open("GET","UnSeenFeedback", true);
    xhttp.send();
}

function GetUnSeenFeedbackListFromButtonDashboard(button){
    var xhttp = new XMLHttpRequest();
    var feedback_ID = button.getAttribute("data-feedbackID");
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            let feedbackObject = JSON.parse(this.responseText);
            processUnSeenFeedbackDashboard(feedbackObject);
        }
    };
    
    if (feedback_ID == null){
        var url = "UnSeenFeedback";
    } else {
        var url = "UnSeenFeedback?feedback_ID=" + feedback_ID;
    }
    
    xhttp.open("GET",url, true);
    xhttp.send();
}

function processUnSeenFeedbackDashboard(data){
    // get and clear table body
    var feedbackSection = document.getElementById("feedback-section");
    // Clear feedback section
    feedbackSection.innerHTML = "";
    
    
    if (data.length == 0) {
        let li = document.createElement("li");
        li.className = "list-group-item";
        
        let p = document.createElement("p");
        p.className = "none-feedback-noti";
        p.innerHTML = "Bạn đã đọc hết tất cả phản hồi";
        
        li.appendChild(p);
        feedbackSection.appendChild(li);
    } 
    else for (i = 0; i < data.length; i++) {
        let li = document.createElement("li");
        li.className = "list-group-item";
        
        let div_outside = document.createElement("div");
        div_outside.className = "d-flex justify-content-between";
        
        let p = document.createElement("p");
        p.className = "my-auto w-75";
        p.innerHTML = data[i].feedback_content;
        
        let div_inside = document.createElement("div");
        div_inside.className = "w-25 text-right";
        //create a button to append to the div_inside
        let button = document.createElement("button");
        button.className = "btn btn-sm";
        button.innerHTML = "<i class='fas fa-check-circle fa-2x'></i>";
        button.setAttribute("onclick", "GetUnSeenFeedbackListFromButtonDashboard(this)");
        button.setAttribute("data-feedbackID", data[i].feedback_ID);
        
        div_inside.appendChild(button);
        
        div_outside.appendChild(p);
        div_outside.appendChild(div_inside);
        
        li.appendChild(div_outside);
        
        feedbackSection.appendChild(li);
    }
    
}









