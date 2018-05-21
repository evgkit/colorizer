package com.evgkit.colorizer;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ImageView imageView;
    int[] imageResIds = {
        R.drawable.cuba1,
        R.drawable.cuba2,
        R.drawable.cuba3
    };
    int imageIndex = 0;
    boolean color = true;
    boolean red = true;
    boolean green = true;
    boolean blue = true;

    DownloadThread downloadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        loadImage();

        downloadThread = new DownloadThread();
        downloadThread.setName("Download Thread");
        downloadThread.start();
    }

    private void loadImage() {
        Glide.with(this).load(imageResIds[imageIndex]).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        (menu.findItem(R.id.nextImage).getIcon())
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        menu.findItem(R.id.red).setChecked(red);
        menu.findItem(R.id.green).setChecked(green);
        menu.findItem(R.id.blue).setChecked(blue);

        menu.setGroupVisible(R.id.colorGroup, color);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextImage:
                imageIndex++;
                if (imageIndex >= imageResIds.length) {
                    imageIndex = 0;
                }
                loadImage();
                break;

            case R.id.downloadImage:
                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                for (String name : Arrays.asList("One", "Two", "Three")) {
                    Message message = Message.obtain();
                    message.obj = name;
                    downloadThread.downloadHandler.sendMessage(message);
                }

                break;

            case R.id.color:
                color = !color;
                updateSaturation();
                invalidateOptionsMenu();
                break;

            case R.id.red:
                red = !red;
                updateColors();
                item.setChecked(red);
                break;

            case R.id.green:
                green = !green;
                updateColors();
                item.setChecked(green);
                break;

            case R.id.blue:
                blue = !blue;
                updateColors();
                item.setChecked(blue);
                break;

            case R.id.reset:
                red = green = blue = color = true;
                imageView.clearColorFilter();
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSaturation() {
        ColorMatrix colorMatrix = new ColorMatrix();
        if (color) {
            red = green = blue = true;
            colorMatrix.setSaturation(1);
        } else {
            colorMatrix.setSaturation(0);
        }
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }

    private void updateColors() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] matrix = {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,
        };
        if (!red) matrix[0] = 0;
        if (!green) matrix[6] = 0;
        if (!blue) matrix[12] = 0;
        colorMatrix.set(matrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }
}
