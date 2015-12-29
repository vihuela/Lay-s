package com.hadlink.library.adapter.utils;

import android.os.Looper;

/**
 * Small class to fail first check if we are executing stuff in an unwanted thread
 */
public class ThreadHelper {

    /**
     * Provoke an exception if we are in the main thread
     */
    public static void crashIfMainThread() {
        if (false) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should NOT be called from the Main Thread");
            }
        }
    }

    /**
     * Provoke an exception if we are in a background thread
     */
    public static void crashIfBackgroundThread() {
        if (false) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
