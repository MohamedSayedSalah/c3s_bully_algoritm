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

            System.out.println(strDate.contains("Kill") +" "+ node.isLeader() + node.getPid());
            if (strDate.charAt(0) == '*') {
                strDate = strDate.substring(1);
                node.setEligibleForElection(false);
                Date date = Config.dateFormat.parse(strDate);
                node.setLastCoordinatorMessage(date);
            }else if (strDate.contains("Kill") && node.isLeader()){
                node.setState(true);
                node.setLeaderState(false);
                Helper.messageToMainServer("Leader P(" + node.getPid() + ") is down " + String.valueOf(Config.dateFormat.format(new Date().getTime()) ));
                System.out.println("Leader P(" + node.getPid() + ") is down " + String.valueOf(Config.dateFormat.format(new Date().getTime()) ));
            }

        } catch (IOException | ParseException e) {
           return ;
        }
    }
}
