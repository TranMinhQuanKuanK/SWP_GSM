var accountList;

function showAccountList() {
    var request = new XMLHttpRequest();

    var url = "GetAccountList";

    request.open('GET', url, true);
    request.onload = function () {
        accountList = JSON.parse(this.responseText);
        renderAccountList();
    };
    request.send();
}

function resetAccount(username) {
    var request = new XMLHttpRequest();

    var url = "ResetAccount";
    var content = "username=" + encodeURIComponent(username);

    //Hiện modal confirm reset password cho cashier
    //if confirmed 
    {
        request.open('POST', url, true);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        request.onload = function () {
            accountErr = JSON.parse(this.responseText);
            //if accountErr != null, alert error
            //else thông báo success
        };
        request.send(content);
    }
    //else thông báo failure
}

function deleteAccount(username) {
    var request = new XMLHttpRequest();

    var url = "DeleteAccount";
    var content = "username=" + encodeURIComponent(username);

    //Hiện modal delete cashier
    //if confirmed 
    {
        request.open('POST', url, true);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        request.onload = function () {
            accountErr = JSON.parse(this.responseText);
            //if accountErr != null, alert error
            //else 
            {
                //thông báo success
                showAccountList();
            }
        };
        request.send(content);
    }
    //else thông báo failure
}

function renderAccountList() {
    var table = document.getElementById("account-list-area");
    table.innerHTML = "";

    for (i = 0; i < accountList.length; i++) {
        var row = table.insertRow(-1);

        var cellNo = row.insertCell(0);
        var cellProductName = row.insertCell(1);
        var cellQuantity = row.insertCell(2);
        var cellTotal = row.insertCell(3);
        var cellResetButton = row.insertCell(4);
        var cellDeleteButton = row.insertCell(5);

        cellNo.innerHTML = i + 1;
        cellProductName.innerHTML = accountList[i].username;
        cellQuantity.innerHTML = accountList[i].name;
        cellTotal.innerHTML = (accountList[i].is_owner ? "Chủ tiệm" : "Thu ngân");

        if (!accountList[i].is_owner) {
            var buttonReset = document.createElement('input');
            buttonReset.type = "button";
            buttonReset.addEventListener('click', function () {
                resetAccount(accountList[i].username);
            });

            var buttonDelete = document.createElement('input');
            buttonDelete.type = "button";
            buttonDelete.addEventListener('click', function () {
                deleteAccount(accountList[i].username);
            });

            cellResetButton.innerHTML = '<input type="button" onClick="resetAccount(\'' + accountList[i].username +
                    '\')" value="Icon refresh" />';
            cellDeleteButton.innerHTML = '<input type="button" onClick="deleteAccount(\'' + accountList[i].username +
                    '\')" value="Icon dấu X" />';
        }
    }
}