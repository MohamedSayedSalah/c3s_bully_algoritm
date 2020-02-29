package com.c3s;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Process [] processes  = new Process[5];

        for (int i = 0 ; i < 5  ;i ++)
            processes[i] = new Process(i) ;

        for (int i = 0 ;i < 5  ; i ++)
            processes[i].startProcess();


    }
}
