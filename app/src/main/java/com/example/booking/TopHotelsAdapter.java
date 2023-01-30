package com.example.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.LinkedList;

public class TopHotelsAdapter extends RecyclerView.Adapter<TopHotelsAdapter.RecyclerViewHolder>{

    public static final String APP_DATA_TITLE = "title";
    public static final String APP_DATA_PRICE = "price";
    public static final String APP_DATA_RATING = "rating";
    public static final String APP_DATA_COUNT = "count";
    public static final String APP_DATA_CHECK_IN = "checkIn";
    public static final String APP_DATA_CHECK_OUT = "checkOut";
    public static final String APP_DATA_PHOTO = "mainPhoto";
    public static final String APP_DATA_HOTEL = "hotel";

    Context context;
    LinkedList<Hotel> topHotelsDataList;
    String checkInDate, checkOutDate;

    public TopHotelsAdapter(Context context, LinkedList<Hotel> topHotelsDataList, String checkInDate, String checkOutDate) {
        this.context = context;
        this.topHotelsDataList = topHotelsDataList;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_hotels_row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.title.setText(topHotelsDataList.get(position).getTitle());
        holder.price.setText(topHotelsDataList.get(position).getPrice());
        holder.rating.setRating((float)topHotelsDataList.get(position).getRating());
        holder.ratingFloat.setText(String.valueOf(topHotelsDataList.get(position).getRating()));
        holder.count.setText(topHotelsDataList.get(position).getCount());

        holder.arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();
                extras.putString(APP_DATA_TITLE, holder.title.getText().toString());
                extras.putString(APP_DATA_PRICE, holder.price.getText().toString());
                extras.putString(APP_DATA_RATING, holder.ratingFloat.getText().toString());
                extras.putString(APP_DATA_COUNT, holder.count.getText().toString());
                extras.putString(APP_DATA_CHECK_IN, checkInDate);
                extras.putString(APP_DATA_CHECK_OUT, checkOutDate);
                extras.putParcelable(APP_DATA_PHOTO, ((BitmapDrawable)holder.hotelImage.getDrawable()).getBitmap());

                // Posto u dokumentaciji pise Parcerable ne bi trebalo da koristimo na seriasable objektima onda koristimo ovo:
                // Konvertujemo objekat klase Hotel u JSON, kako bismo ga prosledili u novu aktivnost
                Gson gson = new Gson();  // dependencie dodat u gradle
                String json = gson.toJson(topHotelsDataList.get(position));
                extras.putString(APP_DATA_HOTEL, json);

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtras(extras); // Postasvljamo bundle u intent
                context.startActivity(intent);
            }
        });

        // Sada  moramo da skinemo sliku preko AsyncTask-a
        Photo.downloadPhoto( topHotelsDataList.get(position).getPhotos().get(0).getUrl(), new ReadBitmapHandler() {

            public void handleMessage(Message msg) {
                holder.hotelImage.setImageBitmap(getBitmap());
            }

        });

    }

    @Override
    public int getItemCount() {
        return topHotelsDataList.size();
    }


    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView hotelImage, arrowButton;
        TextView title, count, ratingFloat, price;
        RatingBar rating;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.imageViewTopHotels);
            title = itemView.findViewById(R.id.textViewTopHotelsTitle);
            price = itemView.findViewById(R.id.textViewTopHotelsPrice);
            rating = itemView.findViewById(R.id.ratingBarRating);
            ratingFloat = itemView.findViewById(R.id.textViewRatingFloat);
            count = itemView.findViewById(R.id.textViewTopHotelsCount);
            arrowButton = itemView.findViewById(R.id.imageViewArrowButton);

        }
    }


}
