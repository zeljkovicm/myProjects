package com.example.booking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class Destination {

    private String title;
    private String geoId;
    private String documentId;
    private String trackingItems;
    private String secondaryText;

    // Default constructor
    public Destination(){};

    // Constructor
    public Destination(String title, String geoId, String documentId, String trackingItems, String secondaryText) {
        this.title = title;
        this.geoId = geoId;
        this.documentId = documentId;
        this.trackingItems = trackingItems;
        this.secondaryText = secondaryText;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTrackingItems() {
        return trackingItems;
    }

    public void setTrackingItems(String trackingItems) {
        this.trackingItems = trackingItems;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    // Parsing JSON
    public static Destination parseJSONObject(JSONObject o){

        Destination destination = new Destination();

        try{
            if(o.has("title")){
                destination.setTitle(o.getString("title"));
            }
            if(o.has("geoId")){
                destination.setGeoId(o.getString("geoId"));
            }
            if(o.has("documentId")){
                destination.setDocumentId(o.getString("documentId"));
            }
            if(o.has("trackingItems")){
                destination.setTrackingItems(o.getString("trackingItems"));
            }
            if(o.has("secondaryText")){
                destination.setSecondaryText(o.getString("secondaryText"));
            }
        }
        catch(Exception e){

        }
        return destination;
    }

    // Metod koji parsira niz JSON objekata
    public static LinkedList<Destination> parseJSONArray (JSONArray array){

        LinkedList<Destination> destinations = new LinkedList<>();

        try{
            for(int i=0; i<array.length(); i++){
                Destination destination = parseJSONObject(array.getJSONObject(i));
                destinations.add(destination);
            }
        }
        catch(Exception e){

        }

        return destinations;
    }

}

