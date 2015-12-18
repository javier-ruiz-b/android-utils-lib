package com.javier.util;

import android.os.Handler;

/**
 * Extends handler post and postDelayed functions. Remembers last called runnable and allows to
 * cancel it.
 *
 * Created by javier on 11.12.15.
 */
public class Timer {

    private Handler mHandler = new Handler();
    private Runnable mLastRunnable;

    /**
     * Executes runnable in the current thread
     * @param runnable
     */
    public void execute(Runnable runnable) {
        mLastRunnable = runnable;
        mHandler.post(runnable);
    }

    /**
     * Executes runnable in the current thread with a delay
     * @param runnable
     * @param delayInMs
     */
    public void executeDelayed(Runnable runnable, long delayInMs) {
        mLastRunnable = runnable;
        mHandler.postDelayed(runnable, delayInMs);
    }

    /**
     * Cancels previously set runnable
     */
    public void clear() {
        if (mLastRunnable != null) {
            mHandler.removeCallbacks(mLastRunnable);
        }
        mLastRunnable = null;
    }

}
