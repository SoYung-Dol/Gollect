package com.example.gollect.utility;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.CallSuper;

public abstract class NetworkManager {
    String TAG = "NetworkManager";
    String urlAddress = "";
    JSONObject requestJson, responseJson;

    String URLPrefix = "https://106.10.52.109";
    private static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    boolean isError = false;
    int errorCode;

    public NetworkManager(String path, JSONObject requestJson) {
        this.urlAddress = URLPrefix + path;
        this.requestJson = requestJson;
    }

    public abstract void responseCallback(JSONObject responseJson);

    @CallSuper
    public void errorCallback(int status) {
        //if (BuildConfig.DEBUG) Log.e(TAG, "http status code: " + status);
    }

    public void sendJson() {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    URL url = new URL(urlAddress);

                    HttpsURLConnection conn = null;

                    OutputStream os = null;
                    InputStream is = null;
                    ByteArrayOutputStream baos = null;

                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    if(msCookieManager.getCookieStore().getCookies().size() > 0){
                        conn.setRequestProperty("Cookie",
                                TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
                    }
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setDefaultUseCaches(false);

                    if (requestJson != null) {
                        os = conn.getOutputStream();
                        os.write(requestJson.toString().getBytes());
                        os.flush();

                        //if (BuildConfig.DEBUG) Log.d(TAG, "json byte: "+requestJson.toString().getBytes());
                    }

                    String response;

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        is = conn.getInputStream();

                        Map<String, List<String>> headerFields = conn.getHeaderFields();
                        List<String> cookiesHeader = headerFields.get("Set-Cookie");
                        if(cookiesHeader != null){
                            for(String cookie : cookiesHeader){
                                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                            }
                        }

                        StringBuilder builder = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line + "\n");
                        }

                        response = builder.toString();

                        //if (BuildConfig.DEBUG) Log.d(TAG, "DATA response = \n" + response);

                        responseJson = new JSONObject(response);
                    } else {
                        //if (BuildConfig.DEBUG) Log.d(TAG, "response code(http status code): " + responseCode);

                        isError = true;
                        //errorCallback(responseCode);
                        errorCode = responseCode;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    isError = true;
                    errorCode = -1;
//                    errorCallback(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    isError = true;
                    errorCode = -2;
//                    errorCallback(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    isError = true;
                    errorCode = -3;
//                    errorCallback(2);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!isError)
                    responseCallback(responseJson);
                else
                    //responseCallback(null);
                    errorCallback(errorCode);
            }
        }.execute();
    }
}