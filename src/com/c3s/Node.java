package com.c3s;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Node {
    private int pid;
    private boolean leader, failed;
    private ServerSocket serverSocket;
    ProcessBuilder processBuilder ;

    public Node(int pid) throws IOException {
        this.pid = pid;
        this.leader = false;
        this.failed = false;
        this.serverSocket = new ServerSocket(Config.MainPort + pid);
        this.processBuilder =  new ProcessBuilder( );
    }


    public void elect() throws InterruptedException {
        boolean response = false;
        if (failed) return;
        for (int i = this.pid + 1; i <= 5; i++) {
                TimeOut.getInstance().Wait();
            try {
                if (Leader.currentLeader != null) return;
                Socket socket = new Socket(InetAddress.getLocalHost(), Config.MainPort + i);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                System.out.println("p(" + pid + "): Are you there Candidate p(" + i + ")");
                dos.writeUTF("Elect");
                socket.close();
                response = true;
            } catch (IOException ex) {
                System.out.println("P(" + this.pid + ") Candidate (P" + i
                        + ") didn't respond");
            } finally {
                if (!response && Leader.currentLeader == null) {
                    System.out.println("New Leader is P: " + pid);
                    Leader.currentLeader = this;
                    this.leader = true;
                }
            }
        }

    }


    public void coordinate() {
        try {
            for (int i = 0; i < 5; i++) {
                TimeOut.getInstance().Wait();
                if (i == pid) continue;
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), Config.MainPort + i);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    System.out.println("P(" + pid + "): Are You There Slave P:(" + i + ")");
                    dos.writeUTF("Slave");
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("P" + this.pid + ": Slave P" + i
                            + " didn't respond");
                }
            }
        } catch (Exception ex1) {

        } finally {
            System.out.println("leader p(" + pid + ") is down");
            Leader.currentLeader = null;
            this.failed = true;
            this.leader = false;

        }
    }

    public void listen() throws IOException {
        try {
            TimeOut.getInstance().Wait();
            Socket socket = serverSocket.accept();
            DataInputStream dos = new DataInputStream(socket.getInputStream());
            String temp = dos.readUTF();
            if (failed) {
                System.out.println("p(" + pid + ") cant respond its disconnected");
                socket.close();
                serverSocket.close();
                throw new Exception("P: " + pid + " disconnected ");
            }
            if (temp.contains("Elect")) {
                System.out.println("YES: P:" + pid);
            } else if (temp.contains("Slave")) {
                System.out.println("P: " + pid + " Yes , Leader");
            }

        } catch (Exception ignored) {

        }

    }


    public void startProcess() throws IOException {

        // act as a client who send data to server
        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Leader.currentLeader != null && leader) {
                        coordinate();
                    } else {
                        try {
                            elect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        })).start();


        // act as a server who is listen for clients
        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        listen();
                    } catch (Exception ignored) {

                    }
                }
            }
        })).start();


    }



}
