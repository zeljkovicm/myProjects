package com.example.booking;

import android.graphics.Bitmap;
import android.os.Handler;

public class ReadBitmapHandler extends Handler {

    private Bitmap bitmap;

    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

}

