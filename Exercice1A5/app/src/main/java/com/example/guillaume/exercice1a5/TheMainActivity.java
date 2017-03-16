package com.example.guillaume.exercice1a5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TheMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("START");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("STOP");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PAUSE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RESUME");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("RESTART");
    }
}
