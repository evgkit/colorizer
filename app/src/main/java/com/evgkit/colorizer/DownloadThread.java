package com.evgkit.colorizer;

import android.util.Log;

import java.util.Arrays;

public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();

    @Override
    public void run() {
        for (String name : Arrays.asList("One", "Two", "Three")) {
            downloadItem(name);
        }
    }

    private void downloadItem(String name) {
        try {
            Log.i(TAG, String.format("Download %s start", name));
            Thread.sleep(5 * 1000);
            Log.i(TAG, String.format("Download %s end", name));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
