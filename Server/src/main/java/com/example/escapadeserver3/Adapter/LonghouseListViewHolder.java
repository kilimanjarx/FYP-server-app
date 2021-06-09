package com.example.escapadeserver3.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.escapadeserver3.Interface.ItemClickListener;
import com.example.escapadeserver3.R;

public class LonghouseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

   public ImageView img_longhouse;
   public TextView txt_longhouse_name,txt_price;
    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public LonghouseListViewHolder(@NonNull View itemView) {
        super(itemView);

        img_longhouse = (ImageView)itemView.findViewById(R.id.img_listing_list);
        txt_longhouse_name = (TextView)itemView.findViewById(R.id.txt_longhouse_name);
        txt_price = (TextView)itemView.findViewById(R.id.txt_longhouse_price);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.Onclick(v,false);

    }


}
