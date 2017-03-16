package com.example.guillaume.isbn_codebarre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String url = "http://info-timide.iut.u-bordeaux1.fr/Cours/Exemples/JSON/LivresISBN.php?Type=Livres&ISBN="+result.getContents().toString();
                GetData gt = new GetData();
                try {
                    String jsonData = gt.execute(url).get();
                    JSONArray jo = new JSONArray(jsonData);
                    JSONObject item =jo.getJSONObject(0);
                    final TextView titre = (TextView) findViewById(R.id.textView_titreRes);
                    titre.setText(item.getString("Titre"));
                    final  TextView auteur = (TextView) findViewById(R.id.textView_auteurRes);
                    auteur.setText(item.getString("Auteur"));
                    final TextView année = (TextView) findViewById(R.id.textView_annéeRes);
                    année.setText(item.getString("An"));
                    final TextView langue = (TextView) findViewById(R.id.textView_langueRes);
                    langue.setText(item.getString("Langue"));
                    final TextView collection = (TextView) findViewById(R.id.textView_collectionRes);
                    collection.setText(item.getString("Collection"));
                    final TextView edition = (TextView) findViewById(R.id.textView_editeurRes);
                    edition.setText(item.getString("Editeur"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}