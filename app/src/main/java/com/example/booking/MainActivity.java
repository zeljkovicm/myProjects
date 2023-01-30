package com.example.booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Message;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView topHotelsRecycler;
    private TopHotelsAdapter topHotelsAdapter;
    private ImageView imageViewProfile, imageViewLogIn, imageViewLogOut;

    private EditText searchDestination;
    private Button buttonSearch;

    private String stringCheckIn, stringCheckOut;
    LinkedList<Hotel> hotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {

        toggleButtons();

        searchDestination = findViewById(R.id.editTextSearchDestinations);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        MyDatePicker checkIn = new MyDatePicker(this, R.id.editTextCheckIn);
        MyDatePicker checkOut = new MyDatePicker(this, R.id.editTextCheckOut);

    }

    private void setTopHotelsRecycler(LinkedList<Hotel> topHotelsDataList, String checkInDate, String checkOutDate){
        topHotelsRecycler = findViewById(R.id.recyclerViewTopHotels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topHotelsRecycler.setLayoutManager(layoutManager);
        topHotelsAdapter = new TopHotelsAdapter(this, topHotelsDataList, checkInDate, checkOutDate);
        topHotelsRecycler.setAdapter(topHotelsAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        toggleButtons();
    }

    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.buttonSearch:

                stringCheckIn = ((EditText)findViewById(R.id.editTextCheckIn)).getText().toString();
                stringCheckOut = ((EditText)findViewById(R.id.editTextCheckOut)).getText().toString();

                if(stringCheckIn != null && stringCheckOut != null){

                    String destination = searchDestination.getText().toString();

                    // Iz API-ja izcitavamo geo lokaciju na osnovu koje cemo u sledecem koraku iscitati ponudu hotela
                    Api.getJSON(MainActivity.this,"https://tripadvisor16.p.rapidapi.com/api/v1/hotels/searchLocation?query="+destination, "Destination is loading",new ReadDataHandler(){

                        String geoId;

                        public void handleMessage(Message msg){
                            String reply = getJson();
                            try{
                                JSONObject json = new JSONObject(reply); // reply iz string-a konvertujemo u json
                                JSONArray array = json.getJSONArray("data");
                                LinkedList<Destination> destinations = Destination.parseJSONArray(array);

                                geoId = destinations.get(0).getGeoId().toString(); // Uzimamo samo prvu glavnu lokaciju u tom gradu

                                Api.getJSON(MainActivity.this,"https://tripadvisor16.p.rapidapi.com/api/v1/hotels/searchHotels?geoId="+geoId+"&checkIn="+stringCheckIn+"&checkOut="+stringCheckOut+"&pageNumber=1&currencyCode=USD", "Hotels are loading", new ReadDataHandler(){

                                    LinkedList<Bitmap> bitmaps = new LinkedList<>();

                                    public void handleMessage(Message msg){
                                        String reply = getJson();
                                        try{
                                            JSONObject json = new JSONObject(reply); // reply iz string-a konvertujemo u json
                                            JSONObject data = json.getJSONObject("data"); // izdvajamo JSON objekat pod nazivom data

                                            JSONArray array = data.getJSONArray("data"); // izdvajamo niz JSON objekata pod nazivom data

                                            // Ovo je lista hotela koje treba da prikazemo
                                            hotels = Hotel.parseJSONArray(array);

                                            setTopHotelsRecycler(hotels, stringCheckIn, stringCheckOut);

                                        }
                                        catch(Exception e){
                                            Toast.makeText(MainActivity.this, "Exception hotels", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });
                            }
                            catch(Exception e){
                                Toast.makeText(MainActivity.this, "Exception thrown", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

                }
                else{
                    Toast.makeText(MainActivity.this, "Please choose dates!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imageViewLogIn:
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.imageViewProfile:
                Intent intent2 = new Intent(this, ReservationActivity.class);
                startActivity(intent2);
                break;

            case R.id.imageViewLogOut:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                editor.commit();

                Intent intent3 = new Intent(this, LoginActivity.class);
                startActivity(intent3);
                break;

        }

    }

    private void toggleButtons(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin = sp.getBoolean("isLogin", false);

        imageViewProfile = findViewById(R.id.imageViewProfile);
        imageViewLogIn = findViewById(R.id.imageViewLogIn);
        imageViewLogOut = findViewById(R.id.imageViewLogOut);

        if(isLogin){
            imageViewProfile.setVisibility(View.VISIBLE);
            imageViewProfile.setOnClickListener(this);
            imageViewLogIn.setVisibility(View.INVISIBLE);
            imageViewLogOut.setVisibility(View.VISIBLE);
            imageViewLogOut.setOnClickListener(this);
        }
        else{
            imageViewProfile.setVisibility(View.INVISIBLE);
            imageViewLogOut.setVisibility(View.INVISIBLE);
            imageViewLogIn.setVisibility(View.VISIBLE);
            imageViewLogIn.setOnClickListener(this);

        }
    }

}