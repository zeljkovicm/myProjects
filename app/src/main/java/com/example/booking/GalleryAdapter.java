package com.example.booking;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.RecyclerViewHolder> {


    Context context;
    ImageView mainPhoto;
    LinkedList<Photo> galleryDataList;

    public GalleryAdapter(Context context, ImageView mainPhoto, LinkedList<Photo> galleryDataList) {
        this.context = context;
        this.mainPhoto = mainPhoto;
        this.galleryDataList = galleryDataList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        // Sada  moramo da skinemo slike preko AsyncTask-a
        Photo.downloadPhoto( galleryDataList.get(position).getUrl(), new ReadBitmapHandler() {

            public void handleMessage(Message msg) {
                holder.galleryImage.setImageBitmap(getBitmap());
            }

        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPhoto.setImageDrawable(holder.galleryImage.getDrawable());
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryDataList.size();
    }

    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView galleryImage;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImage = itemView.findViewById(R.id.imageViewGalleryItem);

        }
    }


}
