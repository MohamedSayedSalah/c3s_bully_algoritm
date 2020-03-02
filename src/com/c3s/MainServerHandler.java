package com.c3s;

import com.c3s.GUI.Panel;

import java.io.*;
import java.net.Socket;

public class MainServerHandler extends Thread {
    private Socket socket;
    private Panel panel ;

    public MainServerHandler(Socket clientSocket, Panel panel) {
        this.socket = clientSocket;
        this.panel = panel ;
    }

    public void run() {
        try {
            DataInputStream dos = new DataInputStream(socket.getInputStream());
            panel.addLogsOutput(dos.readUTF());
        } catch (IOException e) {
            return;
        }
    }
}