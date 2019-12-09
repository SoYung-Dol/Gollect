package com.example.gollect.utility;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.CallSuper;

public abstract class DeleteNetworkManager extends AsyncTask<String, String, JSONObject> {
    String URLPrefix = "http://106.10.54.174";
    String urlAddress = "";
    JSONObject requestJson, responseJson;

    public DeleteNetworkManager(String path, JSONObject requestJson) {
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

                con.setRequestMethod("DELETE");//POST방식으로 보냄
                con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                con.connect();

                //서버로 보내기위해서 스트림 만듬
                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(requestJson.toString());
                writer.flush();
                writer.close();//버퍼를 받아줌


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
