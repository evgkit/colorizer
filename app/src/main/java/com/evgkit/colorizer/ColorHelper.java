package com.evgkit.colorizer;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class ColorHelper {
    public static int[] imageResIds = {
            R.drawable.cuba1,
            R.drawable.cuba2,
            R.drawable.cuba3
    };
    public static int imageIndex = 0;

    public static boolean color = true;
    public static boolean red = true;
    public static boolean green = true;
    public static boolean blue = true;

    public static ColorMatrixColorFilter getSaturation() {
        ColorMatrix colorMatrix = new ColorMatrix();
        if (color) {
            red = green = blue = true;
            colorMatrix.setSaturation(1);
        } else {
            colorMatrix.setSaturation(0);
        }
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public static ColorMatrixColorFilter getColors() {
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
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public static void nextImage() {
        imageIndex++;
        if (imageIndex >= imageResIds.length) {
            imageIndex = 0;
        }
    }
}
