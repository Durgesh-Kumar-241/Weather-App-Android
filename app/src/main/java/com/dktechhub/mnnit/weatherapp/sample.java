package com.dktechhub.mnnit.weatherapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class sample {
    public static void main(String args[])
    {
        String mykey = "364460183a924ff9e379ab5cdb2ed7c9";
        String url="http://api.openweathermap.org/data/2.5/weather?q="+"ballia"+"&APPID="+mykey;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
               // this.process(connection.getInputStream());
                System.out.println(connection.getResponseMessage());
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                String json= sb.toString();
               // System.out.println(json);

                JSONObject jsonObject = new JSONObject(json);
                System.out.println(jsonObject.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
