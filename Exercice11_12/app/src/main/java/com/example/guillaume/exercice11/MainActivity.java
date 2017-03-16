package com.example.guillaume.exercice11;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.example.guillaume.exercice11.R.id.default_activity_button;
import static com.example.guillaume.exercice11.R.id.imageView;
import static com.example.guillaume.exercice11.R.id.option1;

public class MainActivity extends AppCompatActivity {

    private ImageView image = null ;
    private Button btn1 = null ;
    private Button btn2 = null ;
    private Button btn3 = null ;
    private Button btn4 = null ;
    private Button btn5 = null ;
    ArrayList<Button> buttons = new ArrayList<Button>();

    private boolean imageload = false;
    private boolean imageInverted = false;
    private boolean imageGrey = false;
    private Bitmap img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.verticalButton);
        btn2 = (Button)findViewById(R.id.horizontalButton);
        btn3 = (Button)findViewById(R.id.InvertColor);
        btn4 = (Button)findViewById(R.id.GreyButton);
        btn5 = (Button)findViewById(R.id.loadButton);
        buttons.add(btn1);buttons.add(btn2);buttons.add(btn3);buttons.add(btn4);buttons.add(btn5);
        image = (ImageView) findViewById(imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(v);
            }
        });
        registerForContextMenu(image);
    }

    public void loadImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void verticalMirror(View v) {
        if(!imageload)
            loadImage(v);
        else {
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            Bitmap src = img;
            Bitmap dst = Bitmap.createBitmap(src, 0, 0, 800, 600, m, false);
            dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            dst=Bitmap.createScaledBitmap(dst, 800,600, true);
            img = dst;
            image.setImageBitmap(img);
        }
    }

    public void horizontalMirror(View v) {
        if(!imageload)
            loadImage(v);
        else {
            Matrix m = new Matrix();
            m.preScale(1, -1);
            Bitmap src = img;
            Bitmap dst = Bitmap.createBitmap(src, 0, 0, 800, 600, m, false);
            dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            dst=Bitmap.createScaledBitmap(dst, 800,600, true);
            img = dst;
            image.setImageBitmap(img);
        }
    }

    public void inverserCouleur(View v) {
        if(!imageload)
            loadImage(v);
        else {
            float invert [] = {
                    -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f, 1.0f, 0.0f,
                    0.0f, 0.0f, -1.0f, 1.0f, 0.0f,
                    1.0f, 1.0f, 1.0f, 1.0f, 0.0f
            };
            imageInverted = true;
            ColorMatrix cm = new ColorMatrix(invert);
            image.setColorFilter(new ColorMatrixColorFilter(cm));
        }
    }

    public void blackAndWhite(View v) {
        if(!imageload)
            loadImage(v);
        else {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
            image.setColorFilter(filter);
            imageGrey = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if(data == null)
                return;
            img = getPicture(data.getData());
            img=Bitmap.createScaledBitmap(img, 800,600, true);
            image.setImageBitmap(img);
            if(imageInverted) {
                image.clearColorFilter();
                imageInverted = false;
            }
            if(imageGrey){
                image.clearColorFilter();
                imageGrey = false;
            }
            imageload = true;
        }
    }

    public Bitmap getPicture(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        String result;
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor == null) {
            result = selectedImage.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return BitmapFactory.decodeFile(result);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        if(imageload) {
            getMenuInflater().inflate(R.menu.contextmenu, menu);
            menu.setHeaderTitle("Choisir une option");
            invisibleButtons();
        }
    }

    private void invisibleButtons() {
        for(Button b : buttons) {
            b.setVisibility(View.INVISIBLE);
        }
    }
    private void visisibleButtons() {
        for(Button b : buttons) {
            b.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onContextMenuClosed(Menu menu){
        visisibleButtons();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        View v= null;
        switch (item.getItemId()) {
            case R.id.option1:
                verticalMirror(v);
                return true;
            case R.id.option2:
                horizontalMirror(v);
                return true;
            case R.id.option3:
                inverserCouleur(v);
                return true;
            case R.id.option4:
                blackAndWhite(v);
                return true;
            case R.id.option5:
                loadImage(v);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
