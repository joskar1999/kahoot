package com.lecimy.fx.utils;

import com.lecimy.fx.listener.OnCountdownFinishListener;
import com.lecimy.fx.listener.OnSecondElapseListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class CountdownTimer {

    private int seconds;
    private int elapsedSeconds;
    private OnSecondElapseListener onSecondElapseListener;
    private OnCountdownFinishListener onCountdownFinishListener;

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
}
