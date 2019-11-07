package com.example.gollect.utility;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.CallSuper;

public abstract class NetworkManager extends AsyncTask<String, String, JSONObject> {
    String URLPrefix = "http://106.10.33.149";
    String urlAddress = "";
    JSONObject requestJson, responseJson;

    public NetworkManager(String path, JSONObject requestJson) {
        this.urlAddress = URLPrefix + path;
        this.requestJson = requestJson;
    }

    public abstract void responseCallback(JSONObject response);

    @CallSuper
    public void errorCallback(int status) {
    }

    @Override
    protected JSONObject doInBackground(String... urls){
        try{
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(urlAddress);
                con = (HttpURLConnection)url.openConnection();
                con.connect();

                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                String response;

                while((line=reader.readLine()) != null){
                    buffer.append(line);
                }

                response = buffer.toString();
                responseJson = new JSONObject(response);
                return responseJson;

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(con != null){
                    con.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result){
        super.onPostExecute(result);
        responseCallback(result);
    }
}