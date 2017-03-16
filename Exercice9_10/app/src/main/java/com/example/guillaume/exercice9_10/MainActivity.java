package com.example.guillaume.exercice9_10;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void afficherConsole(View v) {
        switch (v.getId()) {
            case R.id.sms:
                startActivityForResult(new Intent(MainActivity.this, Main2Activity.class), 1);
                break;
            case R.id.mms:
                startActivityForResult(new Intent(MainActivity.this, Main2Activity.class), 2);
                break;
            case R.id.appel:
                startActivityForResult(new Intent(MainActivity.this, Main2Activity.class), 3);
                break;
            case R.id.web:
                startActivityForResult(new Intent(MainActivity.this, Main3Activity.class), 4);
                break;
            case R.id.map:
                startActivityForResult(new Intent(MainActivity.this, Main4Activity.class), 5);
                break;
            default:
        }
    }
    private void goOnMap(String longitude, String latitude) {
        System.out.println(latitude);
        System.out.println(longitude);
        String url = "geo:"+latitude+","+longitude+"?z=13";
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }

    private void goOnWeb(String url) {
        //String url = "http://info-timide.iut.u-bordeaux.fr/perso/2017/gnedelec001/portfolio/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void call(String number) {
        Uri uri = Uri.parse("tel:" + number);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, uri));
    }

    private void sendMMS(String number) {
        Uri uri = Uri.parse("mmsto:"+ number);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Voila mon Super SMS");
        startActivity(it);
    }

    private void sendSMS(String number) {
        Uri uri = Uri.parse("smsto:"+number);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Voila mon Super SMS");
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String number = data.getStringExtra(Main2Activity.ACTION);
                sendSMS(number);
            }
        }
        else if(requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String number = data.getStringExtra(Main2Activity.ACTION);
                sendMMS(number);
            }
        }
        else if(requestCode == 3) {
            if (resultCode == RESULT_OK) {
                String number = data.getStringExtra(Main2Activity.ACTION);
                call(number);
            }
        }
        else if(requestCode == 4) {
            if (resultCode == RESULT_OK) {
                String url = data.getStringExtra(Main3Activity.ACTION);
                goOnWeb(url);
            }
        }
        else if(requestCode == 5) {
            if (resultCode == RESULT_OK) {
                String longitude = data.getStringExtra(Main4Activity.ACTION);
                String latitude = data.getStringExtra(Main4Activity.ACTION2);

                goOnMap(latitude, longitude);
            }
        }
    }
}
