package com.c3s;

import java.io.IOException;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args) throws IOException {

        StartSeparateProcess(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    public static void StartSeparateProcess(int processId, int n_process) throws IOException {
        new Node(processId, n_process).startProcess();
    }


}




