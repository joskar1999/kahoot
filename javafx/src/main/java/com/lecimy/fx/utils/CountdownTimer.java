package com.lecimy.fx.utils;

import com.lecimy.fx.listener.OnCountdownFinishListener;
import com.lecimy.fx.listener.OnSecondElapseListener;

import java.util.Calendar;

public class CountdownTimer {

    private int seconds;
    private int elapsedSeconds;
    private OnSecondElapseListener onSecondElapseListener;
    private OnCountdownFinishListener onCountdownFinishListener;

    public CountdownTimer() {
    }

    public CountdownTimer(int seconds) {
        this.seconds = seconds;
    }

    private Thread thread = new Thread(() -> {
        elapsedSeconds = 0;
        long sleepPeriod = 1000;
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(sleepPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elapsedSeconds = i + 1;
            onSecondElapseListener.onElapse();
            long stop = Calendar.getInstance().getTimeInMillis();
            sleepPeriod = 1000 - (stop - start - (i + 1) * 1000);
        }
        onCountdownFinishListener.onFinish();
    });

    public void startTimer() {
        thread.start();
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

    public void setOnSecondElapseListener(OnSecondElapseListener onSecondElapseListener) {
        this.onSecondElapseListener = onSecondElapseListener;
    }

    public void setOnCountdownFinishListener(OnCountdownFinishListener onCountdownFinishListener) {
        this.onCountdownFinishListener = onCountdownFinishListener;
    }
}
