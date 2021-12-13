let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (message) => {
            $("#clients").html("");
            let clients = JSON.parse(message.body);
            for (var i = 0; i < clients.length; i++) {
                showClient(clients[i].name);
            }
        });
        clientList();
    });
}

const clientList = () => stompClient.send("/app/clients", {}, {})

//const createUser = () => stompClient.send("/app/createUser", {}, JSON.stringify({
//    'login': $("#userLoginTextBox").val(),
//    'password': $("#userPassTextBox").val(),
//    'name': $("#userNameTextBox").val(),
//    'age': $("#userAgeTextBox").val()
//}))

const showClient = (client) => $("#clients").append("<tr><td>" + client + "</td></tr>")

window.onload = function () {
    connect();
};

//$(function () {
//    $("form").on('submit', (event) => {
//        event.preventDefault();
//    });
//    $("#create").click(createUser);
//});