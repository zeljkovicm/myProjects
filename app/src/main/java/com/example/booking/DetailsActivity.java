package com.example.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.LinkedList;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView galleryRecycler;
    private GalleryAdapter galleryAdapter;

    private ImageView mainHotelImage, imageViewArrowBack ;
    private TextView hotelTitle, hotelPrice, hotelRating, reservationDates;
    private Hotel hotel;
    private Button bookNow;
    private String count, checkIn, checkOut, title, price, rating;
    private Bitmap mainPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initComponents();
    }

    private void initComponents() {

        Bundle extras = getIntent().getExtras();

        if(extras != null ){
            title = extras.getString(TopHotelsAdapter.APP_DATA_TITLE);
            price = extras.getString (TopHotelsAdapter.APP_DATA_PRICE);
            rating = extras.getString(TopHotelsAdapter.APP_DATA_RATING);
            mainPhoto = extras.getParcelable(TopHotelsAdapter.APP_DATA_PHOTO);
            count = extras.getString(TopHotelsAdapter.APP_DATA_COUNT);
            checkIn = extras.getString(TopHotelsAdapter.APP_DATA_CHECK_IN);
            checkOut = extras.getString(TopHotelsAdapter.APP_DATA_CHECK_OUT);

            // Hotel koji smo prosredili u obliku JSON-a sada konvertujemo ponovo u objekat klase hotel
            Gson gson = new Gson();
            hotel = gson.fromJson(extras.getString(TopHotelsAdapter.APP_DATA_HOTEL), Hotel.class);

            imageViewArrowBack = findViewById(R.id.imageViewDetailsArrowBack);
            imageViewArrowBack.setOnClickListener(this);

            mainHotelImage = findViewById(R.id.imageViewMainPhoto);
            mainHotelImage.setImageBitmap(mainPhoto);

            hotelTitle = findViewById(R.id.textViewHotelName);
            hotelTitle.setText(title);

            hotelPrice = findViewById(R.id.textViewDetailHotelPrice);
            hotelPrice.setText(price);

            hotelRating = findViewById(R.id.textViewRating);
            hotelRating.setText(rating);

            reservationDates = findViewById(R.id.textViewReservationDates);
            reservationDates.setText(checkIn + " - " + checkOut);

            bookNow = findViewById(R.id.buttonStartBooking);
            bookNow.setOnClickListener(this);

            // Ostale slike hotela moraju da se skinu
            LinkedList<Photo> photos = hotel.getPhotos();
            setRecentRecycler(photos);

        }
        else{
            Log.d("Budnle", "Bundle je null");
        }
    }


    private void setRecentRecycler(LinkedList<Photo> galleryDataList){
        galleryRecycler = findViewById(R.id.recycleViewGallery);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        galleryRecycler.setLayoutManager(layoutManager);
        galleryAdapter = new GalleryAdapter(this, this.findViewById(R.id.imageViewMainPhoto), galleryDataList);
        galleryRecycler.setAdapter(galleryAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageViewDetailsArrowBack:
                finish();
                break;

            case R.id.buttonStartBooking:

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                boolean isLogin = sp.getBoolean("isLogin", false);

                if(isLogin){

                    DataBase db = new DataBase(this);

                    // Dodajemo hotel u bazu ako vec nije dodat
                    HotelModel hotel = db.getHotelByTitle(title);
                    if(hotel == null){
                        db.addHotel(title, count, Double.parseDouble(rating), price);
                        hotel = db.getHotelByTitle(title);
                    }

                    String email = sp.getString("email", null);

                    UserModel user = db.getUserByEmail(email);
                    int userId = user.getUserId();
                    int hotelId = hotel.getHotelId();

                    db.addReservation(checkIn, checkOut, hotelId, userId);
                    Toast.makeText(this, "Your reservation is successful", Toast.LENGTH_LONG).show();

                    finish();
                }
                else{
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}