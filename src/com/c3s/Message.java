package com.c3s;

public class Message {
    private Process from  ;
    private Process to ;
    private MessageType messageType ;

    Message(Process from , Process to , MessageType messageType){
        this.from = from ;
        this.to = to ;
        this.messageType = messageType ;
    }

}
