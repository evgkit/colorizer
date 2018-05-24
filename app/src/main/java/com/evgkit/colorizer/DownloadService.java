package com.evgkit.colorizer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadHandler downloadHandler;

    @Override
    public void onCreate() {
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.setName("Download Thread");
        downloadThread.start();

        while (downloadThread.downloadHandler == null) {
            // waiting...
        }
        downloadHandler = downloadThread.downloadHandler;
        downloadHandler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String item = intent.getStringExtra(MainActivity.KEY_ITEM);

        Message message = Message.obtain();
        message.obj = item;
        message.arg1 = startId;
        downloadHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
