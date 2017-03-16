package com.example.guillaume.exercice9_10;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    public static final String ACTION = "logitude";
    public static final String ACTION2 = "latitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    public void confirm(View v) {
        Intent returnIntent = new Intent();
        if(((TextView) findViewById(R.id.longitude)).getText().toString() != "")
            returnIntent.putExtra(ACTION,((TextView) findViewById(R.id.longitude)).getText().toString());
        else
            returnIntent.putExtra(ACTION,"0");

        if(((TextView) findViewById(R.id.latitude)).getText().toString() != "")
            returnIntent.putExtra(ACTION2,((TextView) findViewById(R.id.latitude)).getText().toString());
        else
            returnIntent.putExtra(ACTION2,"0");

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void cancel(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
