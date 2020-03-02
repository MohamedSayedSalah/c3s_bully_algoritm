package com.c3s;

import com.c3s.Utils.Helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;

public class ProcessRequestHandler extends Thread {

    private Socket socket;
    private Node node;

    public ProcessRequestHandler(Socket clientSocket, Node node) {
        this.socket = clientSocket;
        this.node = node;
    }

    public void run() {
        try {
            DataInputStream dos = new DataInputStream(socket.getInputStream());
            String strDate = dos.readUTF();
            if (strDate.charAt(0) == '*'){
            strDate  =  strDate.substring(1);
            node.setEligibleForElection(false);
            Date date   = node.dateFormat.parse(strDate) ;
            node.setLastCoordinatorMessage(date);
            }
        } catch (IOException | ParseException e) {
           return ;
        }
    }
}
