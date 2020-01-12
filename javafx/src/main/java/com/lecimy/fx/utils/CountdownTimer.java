package main.java.com.lecimy.fx.utils;

import java.util.Calendar;
import java.util.Timer;

public class CountdownTimer {

    private int seconds;
    private int elapsedSeconds;
    private Timer timer;

    public CountdownTimer(int seconds) {
        this.seconds = seconds;
        new Thread(() -> {
            long start = Calendar.getInstance().getTimeInMillis();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long stop = Calendar.getInstance().getTimeInMillis();
            System.out.println(stop - start);
        }).start();
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }
}
