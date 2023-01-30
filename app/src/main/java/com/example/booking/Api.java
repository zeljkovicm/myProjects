package com.example.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static void getJSON(Context context, String url, String msg, final ReadDataHandler rdh){
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>(){

            private ProgressDialog waitingDialog;
            String response = "";

            @Override
            protected void onPreExecute() {
                waitingDialog = ProgressDialog.show(context, "Waiting", msg);

            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL link = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) link.openConnection();
                    connection.setRequestProperty("X-RapidAPI-Key", "ac2da42155msh70063b2d05390dap14dfc2jsnfdf90ffd71ed");
                    connection.setRequestProperty("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com");
                    connection.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String row;
                    while((row = br.readLine()) != null){
                        response += row + "\n";
                    }

                    br.close();
                    connection.disconnect();
                }catch(Exception e){
                    response = "[]";
                }

                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                rdh.setJson(response);
                rdh.sendEmptyMessage(0);
                waitingDialog.dismiss();
            }
        };
        task.execute(url);
    }


}
