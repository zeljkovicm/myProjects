package com.example.booking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class Photo {

    private String url;
    private int maxHight;
    private int maxWidth;

    // Podrazumevani konstruktor
    public Photo(){};

    public Photo( String url, int maxHight, int maxWidth){
        this.url = url;
        this.maxHight = maxHight;
        this.maxWidth = maxWidth;
    }

    // Getters and setters
    public String getUrl(){ return url; }
    public int getMaxHight(){ return maxHight; }
    public int getMaxWidth(){ return maxWidth; }

    public void setUrl( String url) { this.url = url; }
    public void setMaxHight( int maxHight ){ this.maxHight = maxHight; }
    public void setMaxWidth( int maxWidth ){ this.maxWidth =  maxWidth; }

    // Parsiramo JSON
    public static Photo parseJSONObject(JSONObject o){

        Photo photo = new Photo();

        try{
            if(o.has("sizes")) {
                JSONObject t = o.getJSONObject("sizes");

                if (t.has("maxHeight")) {
                    photo.setMaxHight(t.getInt("maxHeight"));
                }
                if (t.has("maxWidth")) {
                    photo.setMaxWidth(t.getInt("maxWidth"));
                }
                if (t.has("urlTemplate")) {
                    // U linkove dodajemo dimenzije
                    photo.setUrl(t.getString("urlTemplate").replaceAll("\\{width\\}|\\{height\\}","300"));
                }
            }
        }
        catch(Exception e){

        }
        return photo;
    }

    public static LinkedList<Photo> parseJSONArray (JSONArray array){

        LinkedList<Photo> photos = new LinkedList<>();

        try{
            for(int i=0; i<array.length(); i++){
                Photo photo = parseJSONObject(array.getJSONObject(i));
                photos.add(photo);
            }
        }
        catch(Exception e){}

        return photos;
    }

    public static void downloadPhoto( String url, final ReadBitmapHandler rbh){
        AsyncTask<String, Void, Bitmap> task = new AsyncTask<String, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    input.close();
                    return bitmap;

                } catch (Exception e) {
                    //e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                rbh.setBitmap(bitmap);
                rbh.sendEmptyMessage(0);
            }

        };
        task.execute(url);
    }
}
