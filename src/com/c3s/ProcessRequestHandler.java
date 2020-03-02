package com.c3s;

import com.c3s.Utils.Helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

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
            String temp = dos.readUTF();
             if (temp.contains("Slave")) {
                node.setEligibleForElection(false);
            }
        } catch (IOException e) {
            return;
        }
    }

}
