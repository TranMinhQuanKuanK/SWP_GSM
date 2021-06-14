function GetFeedbackList(){
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
        cell4.innerHTML = data[i].cashier_username;
        cell5.innerHTML = "<button onclick='SeenFeedback()'>Mark as read</button>";
    }
}

function SeenFeedback(){
    
}