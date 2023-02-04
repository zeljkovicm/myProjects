package com.example.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView ReservationRecycler;
    private ReservationAdapter ReservationAdapter;

    private TextView textViewName, textViewFName, textViewLName, textViewEmail;
    private TextView planNext;
    private ImageView imageViewLogOutReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        initComponents();
    }

    private void setReservationRecycler(List<ReservationModel> reservations, int userId){
        ReservationRecycler = findViewById(R.id.recyclerViewReservations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        ReservationRecycler.setLayoutManager(layoutManager);
        ReservationAdapter = new ReservationAdapter(this, reservations, userId);
        ReservationRecycler.setAdapter(ReservationAdapter);
    }

    private void initComponents(){

        textViewName = findViewById(R.id.textViewUserName);
        textViewFName = findViewById(R.id.textViewFirstNameReservations);
        textViewLName = findViewById(R.id.textViewLastNameReservations);
        textViewEmail = findViewById(R.id.textViewEmailReservations);
        imageViewLogOutReservations = findViewById(R.id.imageViewLogOutReservations);
        imageViewLogOutReservations.setOnClickListener(this);
        planNext = findViewById(R.id.textViewBookAgain);
        planNext.setOnClickListener(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sp.getString("email", null);

        DataBase db = new DataBase(this);
        // Iscitavamo podatke o korisniku iz baze
        UserModel user = db.getUserByEmail(email);

        // Prikazujemo podatke
        textViewName.setText(user.getfName());
        textViewFName.setText(user.getfName());
        textViewLName.setText(user.getlName());
        textViewEmail.setText(user.getEmail());

        // Iscitavamo sve rezervacije koje je korisnik rezervisao
        List<ReservationModel> reservations= db.getAllReservationForUser(user.getUserId());

        // Prikazujemo rezervacije u RecycleView-u
        setReservationRecycler(reservations, user.getUserId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewBookAgain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            break;
            case R.id.imageViewLogOutReservations:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                sp.edit().putBoolean("isLogin", false).commit();
                finish();
                break;
        }
        }
    }
