package com.example.booking;

import androidx.annotation.NonNull;

public class ReservationModel {

    public static final String TABLE_NAME = "reservation";
    public static final String COLUMN_RESERVATION_ID = "reservation_id";
    public static final String COLUMN_RESERVATION_CHECK_IN = "check_in";
    public static final String COLUMN_RESERVATION_CHECK_OUT = "check_out";
    public static final String COLUMN_RESERVATION_HOTEL_ID = "hotel_id";
    public static final String COLUMN_RESERVATION_USER_ID = "user_id";

    private int reservationId, userId, hotelId;
    private String checkIn, checkOut;

    public ReservationModel(int reservationId, String checkIn, String checkOut, int hotelId, int userId) {
        this.reservationId = reservationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hotelId = hotelId;
        this.userId = userId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReservationModel{" +
                "reservationId=" + reservationId +
                ", checkIn'" + checkIn + '\'' +
                ", checkOut'" + checkOut + '\'' +
                ", userId'" + userId + '\'' +
                ", hotelId'" + hotelId + '\'' +
                '}';
    }
}
