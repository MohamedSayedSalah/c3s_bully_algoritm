package com.c3s;


import com.c3s.GUI.Panel;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private static Panel panel;
    private static ServerSocket serverSocket;
    public static void main(String[] args) throws IOException {
        panel = new Panel();
    }



    public static  void init(int processes) throws IOException {
            for (int i = 0; i < processes; i++) {
                start( i, processes);
            }
            serverSocket = new ServerSocket(12345);

            (new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            mainServer();
                        } catch (Exception ignored) {

                        }
                    }
                }
            })).start();

        }


    public static void mainServer() throws IOException {
        try {
            Socket socket = serverSocket.accept();
            new MainServerHandler(socket,panel).start();
        } catch (SocketTimeoutException ignored) {

        }

    }


    public static void start( int id, int n_process) {
        (new Thread(new Runnable() {
            public void run() {
                try {
                    JavaProcess.exec(Runner.class, Arrays.asList(new String []{}), id, n_process);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

}
