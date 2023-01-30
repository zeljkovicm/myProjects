package com.example.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.RecyclerViewHolder>{

    Context context;
    List<ReservationModel> reservations;
    int userId;

    public ReservationAdapter(Context context, List<ReservationModel> reservations, int userId) {
        this.context = context;
        this.reservations = reservations;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ReservationAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_row_item, parent, false);
        return new ReservationAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // Iscitavamo podatke o hotelu za datu rezervaciju iz baze
        DataBase db = new DataBase(context);
        HotelModel hotel = db.getHotelById(reservations.get(position).getHotelId());

        holder.name.setText(hotel.getHotelTitle());
        holder.price.setText(hotel.getPrice());
        holder.checkIn.setText(reservations.get(position).getCheckIn());
        holder.checkOut.setText(reservations.get(position).getCheckOut());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase db = new DataBase(context);
                db.deleteReservation(reservations.get(position).getReservationId());

                int removeIndex = position;
                reservations.remove(removeIndex);
                notifyItemRemoved(removeIndex);
                notifyItemRangeChanged(position, reservations.size());

            }
        });

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }


    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView deleteButton;
        TextView name, price, checkIn, checkOut;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewReservationHotelName);
            price = itemView.findViewById(R.id.textViewReservationPrice);
            checkIn = itemView.findViewById(R.id.textViewReservationCheckIn);
            checkOut = itemView.findViewById(R.id.textViewReservationCheckOut);
            deleteButton = itemView.findViewById(R.id.cancel_button);

        }
    }

}
