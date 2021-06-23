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

    {
        request.open('POST', url, true);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        request.onload = function () {
            accountErr = JSON.parse(this.responseText);
            if (accountErr.hasError) {
                alert(accountErr.resetPasswordError);
            } else {
                alert("Đặt lại mật khẩu thành công");
            }
        };
        request.send(content);
    }
    
    $('#reset-password-modal').modal('hide');
}

function deleteAccount(username) {
    var request = new XMLHttpRequest();

    var url = "DeleteAccount";
    var content = "username=" + encodeURIComponent(username);

    {
        request.open('POST', url, true);
        request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        request.onload = function () {
            accountErr = JSON.parse(this.responseText);
            if (accountErr.hasError) {
                alert(accountErr.deleteAccountError);
            } else {
                alert("Xóa tài khoản thành công");
                showAccountList();
            }
        };
        request.send(content);
    }
    
    $('#delete-account-modal').modal('hide');
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

            var resetButton = '<input type="button" value="Icon refresh" ';
            resetButton += 'onclick="document.getElementById(\'reset-username\').innerHTML=';
            resetButton += '\'' + accountList[i].username + '\'" ';
            resetButton += 'data-toggle="modal" data-target="#reset-password-modal" />';
            
            var deleteButton = '<input type="button" value="Icon dấu X" ';
            deleteButton += 'onclick="document.getElementById(\'delete-username\').innerHTML=';
            deleteButton += '\'' + accountList[i].username + '\'" ';
            deleteButton += 'data-toggle="modal" data-target="#delete-account-modal" />';

            cellResetButton.innerHTML = resetButton;
            cellDeleteButton.innerHTML = deleteButton;
        }
    }
}

function createNewAccount() {
    var request = new XMLHttpRequest();

    var url = "CreateAccount";
    var content = "empty-parameter=";

    if (document.getElementById("new-name").value !== "") {
        content += "&new-name=" + document.getElementById("new-name").value;
    }
    if (document.getElementById("new-username").value !== "") {
        content += "&new-username=" + document.getElementById("new-username").value;
    }
    if (document.getElementById("new-password").value !== "") {
        content += "&new-password=" + document.getElementById("new-password").value;
    }
    if (document.getElementById("new-confirm").value !== "") {
        content += "&new-confirm=" + document.getElementById("new-confirm").value;
    }
    if (document.getElementsByName('new-role')[0].checked) {
        content += "&new-is-owner=true";
    }
    
    request.open('POST', url, true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    request.onload = function () {
        accountErr = JSON.parse(this.responseText);
        if (accountErr.hasError) {
            document.getElementById("error-name").innerHTML = (accountErr.nameLengthError ? accountErr.nameLengthError : "");
            document.getElementById("error-username").innerHTML = (accountErr.usernameLengthError ? accountErr.usernameLengthError : "");
            document.getElementById("error-password").innerHTML = (accountErr.passwordLengthError ? accountErr.passwordLengthError : "");
            document.getElementById("error-confirm").innerHTML = (accountErr.confirmNotMatch ? accountErr.confirmNotMatch : "");
            document.getElementById("error-username-exist").innerHTML = (accountErr.usernameExist ? accountErr.usernameExist : "");
            document.getElementById("new-password").value = "";
            document.getElementById("new-confirm").value = "";
        } else {
            showAccountList();
            alert("Đăng ký tài khoản thành công");
            
            document.getElementById("new-name").value = "";
            document.getElementById("new-username").value = "";
            document.getElementById("new-password").value = "";
            document.getElementById("new-confirm").value = "";
            document.getElementById("error-name").innerHTML = "";
            document.getElementById("error-username").innerHTML = "";
            document.getElementById("error-password").innerHTML = "";
            document.getElementById("error-confirm").innerHTML = "";
            document.getElementById("error-username-exist").innerHTML = "";
            
            $('#add-new-modal').modal('hide');
        };
    };
    request.send(content);
}