package com.example.booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper{

    private static final String DATABASE_FILE_NAME = "booking_db";

    public DataBase(@NonNull Context context){ super(context, DATABASE_FILE_NAME, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)",
                UserModel.TABLE_NAME,
                UserModel.COLUMN_USER_ID,
                UserModel.COLUMN_USER_FIRST_NAME,
                UserModel.COLUMN_USER_LAST_NAME,
                UserModel.COLUMN_USER_EMAIL,
                UserModel.COLUMN_USER_PASSWORD
        );
        db.execSQL(query);

         query = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s REAL,%s TEXT)",

                 HotelModel.TABLE_NAME,
                 HotelModel.COLUMN_HOTEL_ID,
                 HotelModel.COLUMN_HOTEL_TITLE,
                 HotelModel.COLUMN_HOTEL_COUNT,
                 HotelModel.COLUMN_HOTEL_RATING,
                 HotelModel.COLUMN_HOTEL_PRICE
        );
        db.execSQL(query);

        query = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s INTEGER,%s INTEGER," +
                        "FOREIGN KEY(%s) REFERENCES %s(%s)," +
                        "FOREIGN KEY(%s) REFERENCES %s(%s))",

                ReservationModel.TABLE_NAME,
                ReservationModel.COLUMN_RESERVATION_ID,
                ReservationModel.COLUMN_RESERVATION_CHECK_IN,
                ReservationModel.COLUMN_RESERVATION_CHECK_OUT,
                ReservationModel.COLUMN_RESERVATION_HOTEL_ID,
                ReservationModel.COLUMN_RESERVATION_USER_ID,
                ReservationModel.COLUMN_RESERVATION_HOTEL_ID,
                HotelModel.TABLE_NAME,
                HotelModel.COLUMN_HOTEL_ID,
                ReservationModel.COLUMN_RESERVATION_USER_ID,
                UserModel.TABLE_NAME,
                UserModel.COLUMN_USER_ID
        );
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DROP TABLE IF EXISTS %s", UserModel.TABLE_NAME);
        db.execSQL(query);

        query = String.format("DROP TABLE IF EXISTS %s", HotelModel.TABLE_NAME);
        db.execSQL(query);

        query = String.format("DROP TABLE IF EXISTS %s", ReservationModel.TABLE_NAME);
        db.execSQL(query);

        onCreate(db);

    }

    // User
    public void addUser(String fName, String lName, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserModel.COLUMN_USER_FIRST_NAME, fName);
        cv.put(UserModel.COLUMN_USER_LAST_NAME, lName);
        cv.put(UserModel.COLUMN_USER_EMAIL, email);
        cv.put(UserModel.COLUMN_USER_PASSWORD, password);

        db.insert((UserModel.TABLE_NAME), null, cv);
    }

    public void editUser(int userId, String fName, String lName, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UserModel.COLUMN_USER_FIRST_NAME, fName);
        cv.put(UserModel.COLUMN_USER_LAST_NAME, lName);
        cv.put(UserModel.COLUMN_USER_EMAIL, email);
        cv.put(UserModel.COLUMN_USER_PASSWORD, password);

        db.update(UserModel.TABLE_NAME, cv,UserModel.COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    public int deleteUser(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserModel.TABLE_NAME, UserModel.COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    public UserModel getUserById(int userId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", UserModel.TABLE_NAME, UserModel.COLUMN_USER_ID);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if(result.moveToFirst()){
            String fName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_FIRST_NAME));
            String lName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_LAST_NAME));
            String email = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_EMAIL));
            String password = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_PASSWORD));

            return new UserModel(userId, fName, lName, email, password);
        }
        else{
            return null;
        }
    }

    public UserModel getUserByEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", UserModel.TABLE_NAME, UserModel.COLUMN_USER_EMAIL);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(email)});

        if(result.moveToFirst()){
            int userId = result.getInt(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_ID));
            String fName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_FIRST_NAME));
            String lName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_LAST_NAME));
            String password = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_PASSWORD));

            return new UserModel(userId, fName, lName, email, password);
        }
        else{
            return null;
        }
    }

    public List<UserModel> getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s", UserModel.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);

        result.moveToFirst();

        List<UserModel> users = new ArrayList<>(result.getCount());

        while(!result.isAfterLast()){

            int userId = result.getInt(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_ID));
            String fName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_FIRST_NAME));
            String lName = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_LAST_NAME));
            String email = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_EMAIL));
            String password = result.getString(result.getColumnIndexOrThrow(UserModel.COLUMN_USER_PASSWORD));

            users.add(new UserModel(userId, fName, lName, email, password));
            result.moveToNext();
        }

        return users;

    }

    // Hotel
    public void addHotel(String hotelTitle, String count, double rating, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HotelModel.COLUMN_HOTEL_TITLE, hotelTitle);
        cv.put(HotelModel.COLUMN_HOTEL_COUNT, count);
        cv.put(HotelModel.COLUMN_HOTEL_RATING, rating);
        cv.put(HotelModel.COLUMN_HOTEL_PRICE, price);

        db.insert((HotelModel.TABLE_NAME), null, cv);
    }

    public int deleteHotel(int hotelId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HotelModel.TABLE_NAME, HotelModel.COLUMN_HOTEL_ID + "=?", new String[]{String.valueOf(hotelId)});
    }

    public HotelModel getHotelById(int hotelId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", HotelModel.TABLE_NAME, HotelModel.COLUMN_HOTEL_ID);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(hotelId)});

        if(result.moveToFirst()){
            String hotelTitle = result.getString(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_TITLE));
            String count = result.getString(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_COUNT));
            double rating = result.getDouble(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_RATING));
            String price = result.getString(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_PRICE));

            return new HotelModel(hotelId, hotelTitle, count, rating, price);
        }
        else{
            return null;
        }
    }

    public HotelModel getHotelByTitle(String hotelTitle){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", HotelModel.TABLE_NAME, HotelModel.COLUMN_HOTEL_TITLE);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(hotelTitle)});

        if(result.moveToFirst()){
            int hotelId = result.getInt(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_ID));
            String count = result.getString(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_COUNT));
            double rating = result.getDouble(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_RATING));
            String price = result.getString(result.getColumnIndexOrThrow(HotelModel.COLUMN_HOTEL_PRICE));

            return new HotelModel(hotelId, hotelTitle, count, rating, price);
        }
        else{
            return null;
        }
    }


    // Reservation
    public void addReservation(String checkIn, String checkOut, int hotelId, int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ReservationModel.COLUMN_RESERVATION_CHECK_IN, checkIn);
        cv.put(ReservationModel.COLUMN_RESERVATION_CHECK_OUT, checkOut);
        cv.put(ReservationModel.COLUMN_RESERVATION_HOTEL_ID, hotelId);
        cv.put(ReservationModel.COLUMN_RESERVATION_USER_ID, userId);

        db.insert((ReservationModel.TABLE_NAME), null, cv);
    }

    public int deleteReservation(int reservationId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ReservationModel.TABLE_NAME, ReservationModel.COLUMN_RESERVATION_ID + "=?", new String[]{String.valueOf(reservationId)});
    }

    public ReservationModel getReservationById(int reservationId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", ReservationModel.TABLE_NAME, ReservationModel.COLUMN_RESERVATION_ID);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(reservationId)});

        if(result.moveToFirst()){
            String checkIn = result.getString(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_CHECK_IN));
            String checkOut = result.getString(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_CHECK_OUT));
            int hotelId = result.getInt(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_HOTEL_ID));
            int userId = result.getInt(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_USER_ID));

            return new ReservationModel(reservationId, checkIn, checkOut, hotelId, userId);
        }
        else{
            return null;
        }
    }

    public List<ReservationModel> getAllReservationForUser(int userId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s =?", ReservationModel.TABLE_NAME, ReservationModel.COLUMN_RESERVATION_USER_ID);
        Cursor result = db.rawQuery(query, new String[]{String.valueOf(userId)});

        result.moveToFirst();

        List<ReservationModel> reservations = new ArrayList<>(result.getCount());

        while(!result.isAfterLast()){

            int reservationId = result.getInt(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_ID));
            String checkIn = result.getString(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_CHECK_IN));
            String checkOut = result.getString(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_CHECK_OUT));
            int hotelId = result.getInt(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_HOTEL_ID));
            //int userId = result.getInt(result.getColumnIndexOrThrow(ReservationModel.COLUMN_RESERVATION_USER_ID)));

            reservations.add(new ReservationModel(reservationId, checkIn, checkOut, hotelId, userId));
            result.moveToNext();
        }

        return reservations;
    }
}
