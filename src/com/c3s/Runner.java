package com.c3s;

import java.io.IOException;
import java.util.Arrays;

public class Runner {


    public static void main (String [] args) throws IOException {
        Node[] nodes = new Node[5];

        for (int i = 0; i < 5; i++)
            nodes[i] = new Node(i);

        for (int i = 0; i < 5; i++) {
            nodes[i].startProcess();
        }

    }

}




