import SockJS from "sockjs-client";
import { over } from "stompjs";

const socket = new SockJS("http://localhost:8080/ws");
const stompClient = over(socket);

stompClient.connect(
    { Authorization: "Bearer " + localStorage.getItem("jwt") }, // Gửi JWT trong header
    () => {
        console.log("Connected");

        stompClient.subscribe("/topic/messages", (msg) => {
            console.log("Received: ", msg.body);
        });

        stompClient.send("/app/chat.send", {}, "Hello everyone!");
    }
);
