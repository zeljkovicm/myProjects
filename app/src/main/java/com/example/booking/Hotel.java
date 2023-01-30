package com.example.booking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class Hotel {

    private String id;
    private String title;
    private String count;
    private double rating;
    private String price;
    private LinkedList<Photo> photos; // Linkovi ka slikama

    public Hotel(){};

    public Hotel(String id, String title, String count,double rating, String price){

        this.id = id;
        this.title = title;
        this.rating = rating;
        this.count = count;
        this.price = price;
        this.photos = new LinkedList<>();
    }

    public String getId(){ return id; }
    public String getTitle(){ return title; }
    public double getRating(){ return rating; }
    public String getCount(){ return count; }
    public String getPrice(){ return price; }
    public LinkedList<Photo> getPhotos(){ return photos; }

    public void setId(String id){ this.id = id; }
    public void setTitle(String title){ this.title = title.replaceAll("[0-9]*\\.\\s",""); } // Sklanjaju se pocetni redni brojevi u nazivu
    public void setRating(double rating){ this.rating = rating; }
    public void setCount(String count){ this.count = count; }
    public void setPrice(String price){ this.price = price; }
    public void setPhotos(LinkedList<Photo> photos){ this.photos = photos; }

    // Parsira JSON i vraca objekat klase Hotel
    public static Hotel parseJSONObject(JSONObject o){

        Hotel hotel = new Hotel();

        try{
            if(o.has("id")){
                hotel.setId(o.getString("id"));
            }
            if(o.has("title")){
                hotel.setTitle(o.getString("title"));
            }
            if(o.has("bubbleRating")){
                JSONObject t = o.getJSONObject("bubbleRating");

                if(t.has("count")){
                    hotel.setCount(t.getString("count"));
                }
                if(t.has("rating")){
                    hotel.setRating(t.getDouble("rating"));
                }
            }
            if(o.has("priceForDisplay")){
                String price = (!o.getString("priceForDisplay").equals("null") )? o.getString("priceForDisplay") : "Contact property";
                hotel.setPrice(price);
            }
            if(o.has("cardPhotos")){
                JSONArray array = o.getJSONArray("cardPhotos");
                hotel.setPhotos(Photo.parseJSONArray(array));
            }
        }
        catch(Exception e){

        }
        return hotel;
    }

    // Metod koji parsira niz JSON objekata

    public static LinkedList<Hotel> parseJSONArray (JSONArray array){

        LinkedList<Hotel> hotels = new LinkedList<>();

        try{
            for(int i=0; i<array.length(); i++){
                Hotel hotel = parseJSONObject(array.getJSONObject(i));
                hotels.add(hotel);
            }
        }
        catch(Exception e){}

        return hotels;

    }

}
