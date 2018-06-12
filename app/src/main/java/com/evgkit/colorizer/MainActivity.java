package com.evgkit.colorizer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_ITEM = "item";

    private ImageView imageView;
    private Menu menu;

    private PlayerService playerService;
    private boolean isBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBound = true;
            PlayerService.PlayerServiceBinder binder = (PlayerService.PlayerServiceBinder) iBinder;
            playerService = binder.getService();

            if (playerService.isPlaying()) {
                MenuItem playMenuItem = menu.findItem(R.id.playerImage);
                playMenuItem.setIcon(R.drawable.ic_media_pause);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        loadImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    private void loadImage() {
        Glide.with(this).load(
                ColorHelper.imageResIds[ColorHelper.imageIndex]
        ).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        (menu.findItem(R.id.nextImage).getIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        menu.findItem(R.id.red).setChecked(ColorHelper.red);
        menu.findItem(R.id.green).setChecked(ColorHelper.green);
        menu.findItem(R.id.blue).setChecked(ColorHelper.blue);

        menu.setGroupVisible(R.id.colorGroup, ColorHelper.color);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextImage:
                ColorHelper.nextImage();
                loadImage();
                break;

            case R.id.playerImage:
                if (isBound) {
                    if (playerService.isPlaying()) {
                        playerService.pause();
                        item.setIcon(R.drawable.ic_media_play);
                    } else {
                        Intent intent = new Intent(MainActivity.this, PlayerService.class);
                        startService(intent);
                        playerService.play();
                        item.setIcon(R.drawable.ic_media_pause);
                    }
                }
                break;

            case R.id.downloadImage:
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                for (String name : Arrays.asList("One", "Two", "Three")) {
                    Intent intent = new Intent(this, DownloadIntentService.class);
                    intent.putExtra(KEY_ITEM, name);
                    startService(intent);
                }

                break;

            case R.id.color:
                ColorHelper.color = !ColorHelper.color;
                imageView.setColorFilter(ColorHelper.getSaturation());
                invalidateOptionsMenu();
                break;

            case R.id.red:
                ColorHelper.red = !ColorHelper.red;
                imageView.setColorFilter(ColorHelper.getColors());
                item.setChecked(ColorHelper.red);
                break;

            case R.id.green:
                ColorHelper.green = !ColorHelper.green;
                imageView.setColorFilter(ColorHelper.getColors());
                item.setChecked(ColorHelper.green);
                break;

            case R.id.blue:
                ColorHelper.blue = !ColorHelper.blue;
                imageView.setColorFilter(ColorHelper.getColors());
                item.setChecked(ColorHelper.blue);
                break;

            case R.id.reset:
                ColorHelper.red = ColorHelper.green = ColorHelper.blue = ColorHelper.color = true;
                imageView.clearColorFilter();
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
