package com.example.rh.core.utils.timer;

import java.util.TimerTask;

/**
 * @author RH
 * @date 2018/10/17
 */
public class BaseTimerTask extends TimerTask {
    private ITimerListener timerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.timerListener = timerListener;
    }

    @Override
    public void run() {
        if (timerListener != null) {
            timerListener.onTimer();
        }
    }
}
