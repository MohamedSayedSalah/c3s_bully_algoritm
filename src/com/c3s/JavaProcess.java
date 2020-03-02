package com.c3s;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class JavaProcess {

    private JavaProcess() {}

    public static Process exec(Class klass, List<String> args , int process_id , int n_process) throws IOException,
            InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getName();

        List<String> command = new LinkedList<String>();
        command.add(javaBin);
        command.add("-cp");
        command.add(classpath);
        command.add(className);
        command.add(Integer.toString(process_id)) ;
        command.add(Integer.toString(n_process)) ;
        if (args != null) {
            command.addAll(args);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.inheritIO().start();
        Main.processes.add(process);
        process.waitFor();
        return process;
    }

}
