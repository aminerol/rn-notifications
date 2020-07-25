"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class NotificationRequest {
    constructor(payload) {
        this.payload = payload;
    }
    get title() {
        return this.payload.title;
    }
    get category() {
        return this.payload.category;
    }
    get fireDate() {
        return this.payload.fireDate;
    }
    get sound() {
        return this.payload.sound;
    }
    get body() {
        return this.payload.body;
    }
}
exports.NotificationRequest = NotificationRequest;
