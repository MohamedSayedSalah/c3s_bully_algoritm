package com.c3s;

import com.c3s.GUI.Panel;
import com.c3s.Utils.Helper;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Node {
    private int pid;
    private boolean leader, failed, eligibleForElection;
    private ServerSocket serverSocket;
    private int n_process;
    private Date lastCoordinatorMessage ;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Node(int pid, int n_process) throws IOException {
        this.pid = pid;
        this.leader = false;
        this.failed = false;
        this.serverSocket = new ServerSocket(Config.MainPort + pid);
        this.n_process = n_process;
        this.eligibleForElection = true;
        lastCoordinatorMessage = new Date() ;
    }

    public int getPid() {
        return this.pid;
    }

    public Date getLastCoordinatorMessage(){
        return lastCoordinatorMessage ;
    }


    public void setLastCoordinatorMessage(Date lastDate){
        this.lastCoordinatorMessage = lastDate ;
    }

    public void setEligibleForElection(boolean eligibleForElection) {
        this.eligibleForElection = eligibleForElection;
    }


    public void elect() throws InterruptedException, IOException {
        if (failed) return;
        for (int i = this.pid + 1; i <= n_process; i++) {
            try {
                if (!eligibleForElection) return;
                Socket socket = new Socket(InetAddress.getLocalHost(), Config.MainPort + i);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Helper.messageToMainServer("p(" + pid + ") -> p(" + i + ") : Election Message " + String.valueOf(dateFormat.format(new Date().getTime())));
                System.out.println("p(" + pid + ") -> p(" + i + ") : Election Message " + String.valueOf(dateFormat.format(new Date().getTime())));
                dos.writeUTF(String.valueOf(dateFormat.format(new Date().getTime())));
                socket.close();
                eligibleForElection = false;
            } catch (IOException ex) {
                Helper.messageToMainServer("p(" + pid + ") -> p(" + i + ") : No Response P(" + pid + ") I will be The new Leader " + String.valueOf(dateFormat.format(new Date().getTime())));
                System.out.println("p(" + pid + ") -> p(" + i + ") : No Response P(" + pid + ") I will be The new Leader " + String.valueOf(dateFormat.format(new Date().getTime())));
                this.leader = true;
            }
        }

    }


    public void coordinate() throws IOException {
        try {
            for (int i = 0; i < n_process; i++) {
                if (i == pid) continue;
                try {
                    Socket socket = new Socket(InetAddress.getLocalHost(), Config.MainPort + i);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    Helper.messageToMainServer("P(" + pid + ") ->  P:(" + i + ") : Coordinator Message " + String.valueOf(dateFormat.format(new Date().getTime())));
                    System.out.println("P(" + pid + ") ->  P:(" + i + ") : Coordinator Message " + String.valueOf(dateFormat.format(new Date().getTime())));
                    dos.writeUTF("*"+String.valueOf(dateFormat.format(new Date().getTime())) );
                    socket.close();
                } catch (IOException ex) {
                    Helper.messageToMainServer("p(" + pid + ") -> p(" + i + ") : No Response " + String.valueOf(dateFormat.format(new Date().getTime())));
                    System.out.println("p(" + pid + ") -> p(" + i + ") : No Response" + String.valueOf(dateFormat.format(new Date().getTime())));
                }
            }
        } catch (Exception ex1) {

        } finally {
            Helper.messageToMainServer("Leader P(" + pid + ") is down " + String.valueOf(dateFormat.format(new Date().getTime()) ));
            System.out.println("Leader P(" + pid + ") is down " + String.valueOf(dateFormat.format(new Date().getTime()) ));
            this.failed = true;
            this.leader = false;
        }
    }

    public void listen() throws Exception {
        if(failed) return ;

        try {
            System.out.println( pid  + " " + lastCoordinatorMessage);
            Socket socket = serverSocket.accept();
            if (failed) {
                Helper.messageToMainServer("P(" + pid + ") cant respond its disconnected " + String.valueOf(dateFormat.format(new Date().getTime())));
                System.out.println("P(" + pid + ") cant respond its disconnected" + String.valueOf(dateFormat.format(new Date().getTime())));
                socket.close();
                serverSocket.close();

            }
            new ProcessRequestHandler(socket, this).start();

        } catch (SocketTimeoutException ignored) {
            System.out.println(pid + " Eligable for Election");
        }

    }


    public void startProcess() throws IOException {


        // act as a client who send data to server
        (new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (leader) {
                        try {
                            TimeOut.getInstance().Wait();
                            coordinate();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            TimeOut.getInstance().Wait();
                            if (Math.abs( new Date().getTime() - lastCoordinatorMessage.getTime()) > Config.THRESHOLD && (!failed || leader)){
                                eligibleForElection = true;
                            }
                            elect();
                        } catch (Exception e) {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();


    }


}
