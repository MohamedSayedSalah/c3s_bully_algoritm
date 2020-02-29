package com.c3s;

import java.sql.Time;
import java.util.concurrent.*;

public class TimeOut  {
    private static TimeOut timeOut  ;
    private TimeOut(){} ;


    public static  TimeOut getInstance(){
        if (timeOut == null)
         return  new TimeOut() ;
        return timeOut ;
    }

    public void Wait () throws InterruptedException {
        Thread.sleep(1000);
    }
}
