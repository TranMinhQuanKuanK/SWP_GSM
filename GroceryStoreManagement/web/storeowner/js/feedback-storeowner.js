// Function triggered when page first load
function GetAllFeedbackList(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var feedbackObject = JSON.parse(this.responseText);
            processFeedback(feedbackObject);
        }
    };
    
    xhttp.open("GET","GetFeedbackList", true);
    xhttp.send();
}

// Function triggered when button "mark as read" is clicked
function GetAllFeedbackListFromButton(button){
    var xhttp = new XMLHttpRequest();
    var feedback_ID = button.getAttribute("data-feedbackID")
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var feedbackObject = JSON.parse(this.responseText);
            processFeedback(feedbackObject);
        }
    };
    
    if (feedback_ID == null){
        var url = "GetFeedbackList";
    } else {
        var url = "GetFeedbackList?feedback_ID=" + feedback_ID;
    }
    
    xhttp.open("GET",url, true);
    xhttp.send();
}

// Function triggered when checkbox "Display read feedback" is check
function displayFeedback(checkboxFeedback){
    if (checkboxFeedback.checked){
        GetSeenFeedbackList();
    } else {
        GetAllFeedbackList();
    }
}
    
function GetSeenFeedbackList(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState >= 4 && this.status <= 200) {
            console.log(this.responseText);
            var feedbackObject = JSON.parse(this.responseText);
            processFeedback(feedbackObject);
        }
    };
    
    xhttp.open("GET","SeenFeedback", true);
    xhttp.send();
}


function processFeedback(data){
    // get and clear table body
    var feedbackTable = document.getElementById("feedbackTable");
    while(feedbackTable.rows.length > 1) {
            feedbackTable.deleteRow(1);
    }
    
    // insert table content
    var count = 0;
    for (i = 0; i < data.length; i++) {
        var row = feedbackTable.insertRow(-1);//append thay vì insert đúng vào vị trí trong index
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);

        count += 1;
        cell1.innerHTML = count;
        cell2.innerHTML = data[i].feedback_date;
        cell3.innerHTML = data[i].feedback_content;
        cell4.innerHTML = data[i].account.name;
        cell5.innerHTML = "<button onclick='GetAllFeedbackListFromButton(this)' data-feedbackID="+ data[i].feedback_ID +">Mark as read</button>";
        
        // append disabled class and remove button if the feedback is seen
        if (data[i].is_seen == 1 ){
            row.className = "seen-feedback";
            cell5.innerHTML = "";
        }
    }
    
}






