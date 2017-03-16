package com.example.guillaume.tacheasynchrone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Guillaume on 14/03/2017.
 */

public class ImageAsync extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    //constructor
    public ImageAsync(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    // laoding picture and put it into bitmap
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    //after downloading
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

