"use strict"

class Player {

    constructor(data) {
        this.name = data.name;
        this.color = data.color;
        this.score = data.score;
        this.x = data.x;
        this.y = data.y;
        this.size = data.size;
    }

    draw(ctx, x, y) {

        ctx.fillStyle = this.color
        ctx.beginPath()
        ctx.arc(x, y, this.size, 0, 2 * Math.PI);
        ctx.fill()


        ctx.fillStyle = "#000";
        let fontSize = 15 * this.size / 20;
        ctx.font = fontSize + "px Arial";
        ctx.textAlign = "center";
        ctx.fillText(this.name, x, y + fontSize / 3);
        //ctx.fillText(this.name, x, y + this.size + fontSize);
    }
}

class StaticObject {

    constructor(data) {
        this.color = data.color;
        this.x = data.x;
        this.y = data.y;
        this.size = data.size;
    }

    draw(ctx, x, y) {
        ctx.fillStyle = this.color
        ctx.beginPath()
        ctx.arc(x, y, this.size, 0, 2 * Math.PI);
        ctx.fill()
    }
}


class Camera {

    constructor(x, y) {
        this.x = x;
        this.y = y;
    }

    update(x, y) {
        this.x = x;
        this.y = y;
    }
}

class CurrentState {

    constructor() {
        this.players = [];
        this.staticObjects = [];
    }

    update(players, staticObjects) {
        this.players = players;
        this.staticObjects = staticObjects;
    }
}