package com.c3s;


import java.io.IOException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i = 0; i < 5; i++) {
            JavaProcess.exec(Runner.class, Arrays.asList(args));
        }
    }
}
