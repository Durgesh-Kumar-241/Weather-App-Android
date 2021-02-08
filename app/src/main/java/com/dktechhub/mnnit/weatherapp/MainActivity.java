package com.dktechhub.mnnit.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button) findViewById(R.id.go);
        EditText input= (EditText) findViewById(R.id.cityName);
        TextView out= (TextView) findViewById(R.id.out);
        go.setOnClickListener(v -> {
            Fetch f1 = new Fetch();
            f1.execute(input.getText().toString());
        });

    }
    public class Fetch extends AsyncTask<String,Void,String>
    {
        TextView out= (TextView) findViewById(R.id.out);
        private String load(String city)
        {   String mykey = "364460183a924ff9e379ab5cdb2ed7c9";
             String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+mykey;
             try {
                 HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                 if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                 {
                     BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                     StringBuilder sb = new StringBuilder();
                     String line;
                     while ((line = br.readLine()) != null) {
                         sb.append(line+"\n");
                     }
                     br.close();
                     String json= sb.toString();
                     JSONObject jsonObject = new JSONObject(json);
                     JSONObject main = jsonObject.getJSONObject("main");
                     JSONObject coord = jsonObject.getJSONObject("coord");
                     String loc ="Location: Longitude:"+coord.get("lon")+" Latitude:"+coord.get("lat");
                     JSONArray weather = jsonObject.getJSONArray("weather");
                     JSONObject status = weather.getJSONObject(0);
                     JSONObject sys = jsonObject.getJSONObject("sys");
                     String syst= "\nSunrise:"+new Date(sys.getLong("sunrise"))+"\nSunset:"+new Date(sys.getLong("sunset"))+"\nCountry:"+sys.getString("country");
                     return "City:"+jsonObject.getString("name")+"\nStatus:"+status.get("description")+"\nTemperature:"+main.get("temp")+" F\nHumidity:"+main.get("humidity")+"\n"+loc;
                 }else {
                     return "City Name Not Valid";
                 }
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
                 return "Connection error";
             } catch (JSONException e) {
                 e.printStackTrace();
                 return "Parse error";
             }
            return "Request failed";
        }


        @Override
        protected String doInBackground(String... strings) {
            String city = strings[0];

            return  load(city);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            out.setText("Please wait");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            out.setText(s);
        }
    }
}