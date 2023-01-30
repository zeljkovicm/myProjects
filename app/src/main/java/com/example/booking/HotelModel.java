package com.example.booking;

import androidx.annotation.NonNull;

public class HotelModel {

    public static final String TABLE_NAME = "hotel";
    public static final String COLUMN_HOTEL_ID = "hotel_id";
    public static final String COLUMN_HOTEL_TITLE = "hotel_title";
    public static final String COLUMN_HOTEL_COUNT = "count";
    public static final String COLUMN_HOTEL_RATING = "rating";
    public static final String COLUMN_HOTEL_PRICE = "price";

    private int hotelId;
    private String hotelTitle, count, price;
    private double rating;

    public HotelModel(int hotelId, String hotelTitle, String count, double rating, String price) {
        this.hotelId = hotelId;
        this.hotelTitle = hotelTitle;
        this.count = count;
        this.price = price;
        this.rating = rating;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public String getCount() {
        return count;
    }

    public String getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setHotelTitle(String hotelTitle) {
        this.hotelTitle = hotelTitle;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "HotelModel{" +
                "hotelId=" + hotelId +
                ", hotel title'" + hotelTitle + '\'' +
                ", count'" + count + '\'' +
                ", rating'" + rating + '\'' +
                ", price'" + price + '\'' +
                '}';
    }

}
