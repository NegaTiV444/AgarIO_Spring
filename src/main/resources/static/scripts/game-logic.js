"use strict"

var gameFieldWidth;
var gameFieldHeight;

var visibleHeight = document.body.clientHeight;
var visibleWidth = document.body.clientWidth;

var mouseX;
var mouseY;

var isConfigured = false;

var name;
var score;
var stompClient = null;

var leaders = document.getElementById("leaders");

var canvas = document.getElementById("game-field");
var ctx = canvas.getContext('2d');

var camera;
var updateTimer;

var subscription;

canvas.width = visibleWidth;
canvas.height = visibleHeight;

function init() {
    document.onmousemove = mouseMove;
    connect();
}

function updateLeaders(players) {
    leaders.innerHTML = "";
    var len = players.length < 4 ? players.length : 3;
    var i = 0;
    while (i < len) {
        var li = document.createElement('li');
        var span = document.createElement('span');
        span.textContent = players[len - i - 1].name + " : " + players[len - i - 1].size;
        li.appendChild(span);
        leaders.appendChild(li);
        i++;
    }
}

function redrawGameField(players, staticObjects) {
    ctx.clearRect(0, 0, visibleWidth, visibleHeight);
    var data = players.filter(player => player.name === name)[0];
    staticObjects.forEach(item => {
        var staticObject = new StaticObject(item);
        staticObject.draw(ctx, staticObject.x - camera.x, staticObject.y - camera.y);
    });
    if (data == null) {
        alert("You lose. Your score is " + score);
        location = "/home";
        exit;
    }
    var currentPlayer = new Player(data);
    score = currentPlayer.size * 10;
    camera.x = currentPlayer.x - visibleWidth / 2;
    camera.y = currentPlayer.y - visibleHeight / 2;
    //currentPlayer.draw(ctx, visibleWidth / 2, visibleHeight / 2);
    players.forEach(item => {
        var player = new Player(item);
        player.draw(ctx, player.x - camera.x, player.y - camera.y);
    })
}

function sendUpdate() {
    stompClient.send("/game/update", {}, JSON.stringify(
        {name: name, mouseX: mouseX + camera.x, mouseY: mouseY + camera.y}));
    //console.log("message sent");
}

function connect() {
    var socket = new SockJS('/game/subscription');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    //event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    console.log("Connection successful");
    subscription = stompClient.subscribe('/config', onConfigMessageReceived);
    name = prompt("Enter your name");
    stompClient.send("/game/add-user", {}, JSON.stringify({name: name}));
}

function onConfigMessageReceived(payload) {
    //subscription.unsubscribe();
    if (!isConfigured) {
        isConfigured = true;
        var message = JSON.parse(payload.body);
        gameFieldWidth = message.gameFieldWidth;
        gameFieldHeight = message.gameFieldHeight;
        camera = new Camera(gameFieldWidth / 2, gameFieldHeight / 2);
        console.log("Configuration successful");
        stompClient.subscribe('/update', onMessageReceived);
        updateTimer = setInterval(sendUpdate, 30);
    }

}

function onError(error) {
    alert("Connection failed")
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    redrawGameField(message.players, message.staticObjects);
    updateLeaders(message.players);
    //console.log("message received");
}

window.addEventListener('resize', function (event) {
    visibleHeight = document.body.clientHeight;
    visibleWidth = document.body.clientWidth;
    canvas.width = visibleWidth;
    canvas.height = visibleHeight;
});

function mouseMove(event) {
    mouseX = event.clientX;
    mouseY = event.clientY;
}

init();