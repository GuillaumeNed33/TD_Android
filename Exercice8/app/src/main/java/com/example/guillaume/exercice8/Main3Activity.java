package com.example.guillaume.exercice8;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    public static final String MESSAGE1 = "street" ;
    public static final String MESSAGE2 = "address";
    public static final String MESSAGE3 = "code";
    public static final String MESSAGE4 = "city";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.MESSAGE4);
        String message2 = intent.getStringExtra(MainActivity.MESSAGE5);
        String message3 = intent.getStringExtra(MainActivity.MESSAGE6);
        String message4 = intent.getStringExtra(MainActivity.MESSAGE7);

        EditText streetEdit = (EditText)findViewById(R.id.streetResult);
        EditText addressEdit = (EditText)findViewById(R.id.addressResult);
        EditText codeEdit = (EditText)findViewById(R.id.codeResult);
        EditText cityEdit = (EditText)findViewById(R.id.cityResult);

        streetEdit.setText(message1);
        addressEdit.setText(message2);
        codeEdit.setText(message3);
        cityEdit.setText(message4);
    }

    public void modifyCoord(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MESSAGE1,((TextView) findViewById(R.id.streetResult)).getText().toString());
        returnIntent.putExtra(MESSAGE2,((TextView) findViewById(R.id.addressResult)).getText().toString());
        returnIntent.putExtra(MESSAGE3,((TextView) findViewById(R.id.codeResult)).getText().toString());
        returnIntent.putExtra(MESSAGE4,((TextView) findViewById(R.id.cityResult)).getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void  cancelCoord(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
