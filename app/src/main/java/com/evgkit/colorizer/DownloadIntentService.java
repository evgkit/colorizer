package com.evgkit.colorizer;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


public class DownloadIntentService extends IntentService {
    private static final String TAG = DownloadIntentService.class.getSimpleName();

    public DownloadIntentService() {
        super("DownloadIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        downloadItem(intent != null ? intent.getStringExtra(MainActivity.KEY_ITEM) : null);
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
