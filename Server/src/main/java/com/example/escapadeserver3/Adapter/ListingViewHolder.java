package com.example.escapadeserver3.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.escapadeserver3.Interface.ItemClickListener;
import com.example.escapadeserver3.R;

public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public ImageView img_listing;
    public TextView txt_listing;


    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }

    public ListingViewHolder(@NonNull View itemView) {
        super(itemView);

        img_listing = (ImageView) itemView.findViewById(R.id.img_listing);
        txt_listing = (TextView) itemView.findViewById(R.id.txt_listing_name);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.Onclick(v,false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.Onclick(v,true);
        return true;
    }
}
