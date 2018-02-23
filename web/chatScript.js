function startRefreshFunctions() {
    setInterval('refresh()', 2000);
    setInterval('refreshUsers()', 2000);

}

function sendMessage() {
    var name = $("#uName").val();
    var message = $("#uMessage").val();
    var objMessage = { "name": name, "message": message };
    $.ajax({
        url: 'chatServlet',
        type: 'GET',
        contentType: 'application/json',
        data: objMessage,
        dataType: 'json',
        success: function(value) {
            $("#test").val = value.name;
            console.log("Success");
            console.log("value of name is:" + value.name);
        }
    });
}

function refresh() {
    $.ajax({
        url: 'chatServlet',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: refreshMessages
    });
}

function refreshMessages(val) {
    var messages = val;

    $("#tableRows tr").remove();
    for (var i = 0; i < val.length; i++) {
        $('#tableRows').append('<tr><td>' + messages[i].name + '</td><td>' + messages[i].message + '</td > </tr>');
    }
}



function refreshUsers() {
    $.ajax({
        url: 'UsersOnlineServlet',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: refreshUsersList
    });
}

function refreshUsersList(val) {
    var users = val;
    $("#usersList li").remove();
    for (var i = 0; i < val.length; i++) {
        $('#usersList').append('<li>' + val[i].name + '</li>');
        console.log("name: " + users[i].name);
    }
}

function logout() {
    $.ajax({
        url: 'UsersOnlineServlet',
        type: 'GET',
        success: userRemoved
    });
}


function userRemoved() {
    window.location.href = "index.html";
}