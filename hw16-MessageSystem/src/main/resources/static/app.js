let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (message) => {
            $("#clients").html("");
            let clients = JSON.parse(message.body);
            for (var i = 0; i < clients.clientDtoList.length; i++) {
                showClient(clients.clientDtoList[i]);
            }
        });
        clientList();
    });
}

const clientList = () => stompClient.send("/app/clients", {}, {})

const createClient = () => stompClient.send("/app/createClient", {}, JSON.stringify({
    'name': $("#nameTextBox").val(),
    'addressCountry': $("#addressCountryTextBox").val(),
    'addressCity': $("#addressCityTextBox").val(),
    'addressStreet': $("#addressStreetTextBox").val(),
    'addressHouseNumber': $("#addressHouseNumberTextBox").val(),
    'addressBuildingNumber': $("#addressBuildingNumberTextBox").val(),
    'addressApartmentNumber': $("#addressApartmentNumberTextBox").val(),
    'phoneNumber': $("#phoneNumberTextBox").val(),
}))

const showClient = (client) => $("#clients")
        .append("<tr>")
        .append("<td>" + client.id + "</td>")
        .append("<td>" + client.name + "</td>")
        .append("<td>" + client.addressView + "</td>")
        .append("<td>" + client.phonesView + "</td>")
        .append("</tr>")

window.onload = function () {
    connect();
};

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#create").click(createClient);
});