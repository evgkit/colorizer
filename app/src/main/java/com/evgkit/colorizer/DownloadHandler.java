package com.evgkit.colorizer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService service;

    @Override
    public void handleMessage(Message msg) {
        downloadItem(msg.obj.toString());
        service.stopSelf(msg.arg1);
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

    public void setService(DownloadService service) {
        this.service = service;
    }
}
