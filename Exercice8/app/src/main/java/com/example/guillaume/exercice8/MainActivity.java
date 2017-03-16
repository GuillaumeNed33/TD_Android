package com.example.guillaume.exercice8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE1 = "nom" ;
    public static final String MESSAGE2 = "prenom";
    public static final String MESSAGE3 = "phone";
    public static final String MESSAGE4 = "number";
    public static final String MESSAGE5 = "address";
    public static final String MESSAGE6 = "codepostal";
    public static final String MESSAGE7 = "city";

    private static final int INFOS_RESULT = 1;
    private static final int COORD_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void modifyInfos(View v)
    {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView surnameText = (TextView) findViewById(R.id.surname);
        TextView phoneText = (TextView) findViewById(R.id.phone);

        String message1 = nameText.getText().toString();
        String message2 = surnameText.getText().toString();
        String message3 = phoneText.getText().toString();

        intent.putExtra(MESSAGE1, message1);
        intent.putExtra(MESSAGE2, message2);
        intent.putExtra(MESSAGE3, message3);

        startActivityForResult(intent, INFOS_RESULT);
    }

    public void modifyCoord(View v) {
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);

        TextView streetText = (TextView) findViewById(R.id.StreetNumber);
        TextView addressText = (TextView) findViewById(R.id.address);
        TextView codePostalText = (TextView) findViewById(R.id.codePostal);
        TextView cityText = (TextView) findViewById(R.id.city);

        String message1 = streetText.getText().toString();
        String message2 = addressText.getText().toString();
        String message3 = codePostalText.getText().toString();
        String message4 = cityText.getText().toString();

        intent.putExtra(MESSAGE4, message1);
        intent.putExtra(MESSAGE5, message2);
        intent.putExtra(MESSAGE6, message3);
        intent.putExtra(MESSAGE7, message4);

        startActivityForResult(intent, COORD_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == INFOS_RESULT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String message1 = data.getStringExtra(Main2Activity.MESSAGE1);
                String message2 = data.getStringExtra(Main2Activity.MESSAGE2);
                String message3 = data.getStringExtra(Main2Activity.MESSAGE3);

                TextView name = (TextView)findViewById(R.id.name);
                TextView surname = (TextView)findViewById(R.id.surname);
                TextView phone = (TextView)findViewById(R.id.phone);

                name.setText(message1);
                surname.setText(message2);
                phone.setText(message3);
            }
        }
        else if(requestCode == COORD_RESULT) {
            if(resultCode == RESULT_OK) {
                String message1 = data.getStringExtra(Main3Activity.MESSAGE1);
                String message2 = data.getStringExtra(Main3Activity.MESSAGE2);
                String message3 = data.getStringExtra(Main3Activity.MESSAGE3);
                String message4 = data.getStringExtra(Main3Activity.MESSAGE4);

                TextView StreetNumber = (TextView)findViewById(R.id.StreetNumber);
                TextView address = (TextView)findViewById(R.id.address);
                TextView codePostal = (TextView)findViewById(R.id.codePostal);
                TextView city = (TextView)findViewById(R.id.city);

                StreetNumber.setText(message1);
                address.setText(message2);
                codePostal.setText(message3);
                city.setText(message4);
            }
        }
    }

}
