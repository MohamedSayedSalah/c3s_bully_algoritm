package com.c3s.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Helper {


    public static void messageToMainServer(String message) throws IOException {
        Socket toMainServer = new Socket(InetAddress.getLocalHost(), 12345);
        DataOutputStream dos = new DataOutputStream(toMainServer.getOutputStream());
        dos.writeUTF(message);
        toMainServer.close();
    }

}