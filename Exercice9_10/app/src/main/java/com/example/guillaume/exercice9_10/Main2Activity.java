package com.example.guillaume.exercice9_10;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    public static final String ACTION ="call";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void calling(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ACTION,((TextView) findViewById(R.id.phone)).getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void cancel(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
