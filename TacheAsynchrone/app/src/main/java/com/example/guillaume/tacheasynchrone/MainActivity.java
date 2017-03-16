package com.example.guillaume.tacheasynchrone;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView textViewData;
    private SharedPreferences prefs;
    private Spinner spinner;
    private final static String CITIES = "cities";
    private ArrayList<String>cities = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText city;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewData = (TextView) findViewById(R.id.info);
        spinner = (Spinner) findViewById(R.id.spinner);
        city = (EditText)findViewById(R.id.cityPlain);
        imgView = (ImageView) findViewById(R.id.imageView);
        updateSpinner();
    }

    private void recupCities() {
        cities.clear();
        prefs = getSharedPreferences(CITIES, Context.MODE_PRIVATE);
        for(int i=0; i<10; i++) {
            String textData = prefs.getString(Integer.toString(i), "null");
            if(!textData.equals("null")) {
                cities.add(textData);
            }
        }
    }

    public void exoPart1() throws ExecutionException, InterruptedException, JSONException {
        String s = "http://www.labri.fr/perso/acasteig/teaching/android/geo.php?city=Talence";
        GetData data = new GetData();
        String jsonInfo = data.execute(s).get();

        JSONObject parser = new JSONObject(jsonInfo);
        String infoContent = new String();
        infoContent += ("Pays : " + parser.getString("pays") + "\n");
        infoContent += ("Région : " + parser.getString("région") + "\n");
        infoContent += ("Superficie : " + parser.getString("superficie") + "\n");
        infoContent += ("Nombre d'habitants : " + parser.getString("population") + "\n");
        infoContent += ("Maire: " + parser.getString("maire") + "\n");
        textViewData.setText(infoContent);
    }

    public void exoPart2() throws ExecutionException, InterruptedException, JSONException {

        String key = "a426559388fd425fb09155330171402";
        String cityData = city.getText().toString();
        String s = "https://api.worldweatheronline.com/premium/v1/weather.ashx?key="+key+"&format=json&q="+cityData;

        GetData data = new GetData();
        String jsonInfo = data.execute(s).get();
        String infoContent = new String();

        if(jsonInfo.contains("request")) {
            saveCity();
            updateSpinner();

            JSONObject parser = new JSONObject(jsonInfo);
            JSONObject parser_data = new JSONObject(parser.getString("data"));
            //info ville
            JSONArray parser_city = parser_data.getJSONArray("request");
            JSONObject query_parser = new JSONObject(parser_city.getString(0));

            //info meteo
            JSONArray parser_condition = parser_data.getJSONArray("current_condition");
            JSONObject dataReal = new JSONObject(parser_condition.getString(0));
            JSONArray tmp_desc = dataReal.getJSONArray("weatherDesc");
            JSONObject desc = new JSONObject(tmp_desc.getString(0));

            //info image
            JSONArray tmp_url = dataReal.getJSONArray("weatherIconUrl");
            JSONObject url = new JSONObject(tmp_url.getString(0));

            infoContent += ("Lieu : " + query_parser.getString("query") + "\n");
            infoContent += ("Date d'observation : " + dataReal.getString("observation_time") + "\n");
            infoContent += ("Temperature : " + dataReal.getString("temp_C") + " °C\n");
            infoContent += ("Description : " + desc.getString("value") + "\n");
            String iconURL = url.getString("value");
            if(iconURL != null) {
                imgView.setVisibility(View.VISIBLE);
                new ImageAsync(imgView).execute(iconURL);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(300,300));
            }
        }
        else {
            JSONObject parser = new JSONObject(jsonInfo);
            JSONObject parser_data = new JSONObject(parser.getString("data"));
            JSONArray parser_error = parser_data.getJSONArray("error");
            JSONObject error = new JSONObject(parser_error.getString(0));
            infoContent = error.getString("msg");
            imgView.setVisibility(View.INVISIBLE);
        }
        textViewData.setText(infoContent);
    }

    private void updateSpinner() {
        recupCities();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String items = spinner.getSelectedItem().toString();
                city.setText(items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    public void loadInformation(View v) throws ExecutionException, InterruptedException, JSONException {
        //exoPart1();
        exoPart2();
    }

    private void saveCity() {
        prefs = getSharedPreferences(CITIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        EditText city = (EditText)findViewById(R.id.cityPlain);
        CharSequence textData = city.getText();

        if (textData != null && !textData.equals("")) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(textData.toString());
            for(String d : cities) {
                if(tmp.size() <= 10 && !textData.toString().equals(d)) {
                    tmp.add(d);
                }
            }

            int i=0;
            for(String s : tmp) {
                editor.putString(Integer.toString(i), s);
                i++;
            }
            editor.commit();
        }
    }
}
