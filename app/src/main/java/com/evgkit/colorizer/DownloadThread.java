package com.evgkit.colorizer;

import android.os.Looper;

public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();

    public DownloadHandler downloadHandler;

    @Override
    public void run() {
        Looper.prepare();
        downloadHandler = new DownloadHandler();
        Looper.loop();
    }
}
