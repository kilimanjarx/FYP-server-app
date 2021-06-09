package com.example.escapadeserver3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.escapadeserver3.Interface.ItemClickListener;
import com.example.escapadeserver3.ListingList;
import com.example.escapadeserver3.Model.Longhouse;
import com.example.escapadeserver3.R;
import com.example.escapadeserver3.UpdateProductActivity;
import com.example.escapadeserver3.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LonghouseListAdapter extends RecyclerView.Adapter<LonghouseListViewHolder>  {

    Context context;
    List<Longhouse> longhouseList;

    public LonghouseListAdapter(Context context, List<Longhouse> longhouseList) {
        this.context = context;
        this.longhouseList = longhouseList;
    }

    @NonNull
    @Override
    public LonghouseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.listing_item_layout,viewGroup,false);

        return new LonghouseListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull LonghouseListViewHolder longhouseListViewHolder, final int i) {

        Picasso.with(context).load(longhouseList.get(i).Link).into(longhouseListViewHolder.img_longhouse);
        longhouseListViewHolder.txt_price.setText(new StringBuilder("RM").append(longhouseList.get(i).Price).toString());
        longhouseListViewHolder.txt_longhouse_name.setText(longhouseList.get(i).Name);

        longhouseListViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void Onclick(View view, Boolean isLongClick) {

                Common.currentLonghouse = longhouseList.get(i);

                context.startActivity(new Intent(context, UpdateProductActivity.class));


            }
        });

    }

    @Override
    public int getItemCount() {
        return longhouseList.size();
    }


}
