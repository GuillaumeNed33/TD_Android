package com.example.guillaume.exercice8;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    public static final String MESSAGE1 = "nom" ;
    public static final String MESSAGE2 = "prenom";
    public static final String MESSAGE3 = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.MESSAGE1);
        String message2 = intent.getStringExtra(MainActivity.MESSAGE2);
        String message3 = intent.getStringExtra(MainActivity.MESSAGE3);

        EditText nameEdit = (EditText)findViewById(R.id.nameResult);
        EditText surnameEdit = (EditText)findViewById(R.id.surnameResult);
        EditText phoneEdit = (EditText)findViewById(R.id.phoneResult);

        nameEdit.setText(message1);
        surnameEdit.setText(message2);
        phoneEdit.setText(message3);
    }

    public void modify(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MESSAGE1,((TextView) findViewById(R.id.nameResult)).getText().toString());
        returnIntent.putExtra(MESSAGE2,((TextView) findViewById(R.id.surnameResult)).getText().toString());
        returnIntent.putExtra(MESSAGE3,((TextView) findViewById(R.id.phoneResult)).getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void  cancel(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
