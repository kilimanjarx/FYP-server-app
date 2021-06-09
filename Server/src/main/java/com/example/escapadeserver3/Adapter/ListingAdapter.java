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
import com.example.escapadeserver3.Model.Category;
import com.example.escapadeserver3.R;
import com.example.escapadeserver3.UpdateCategoryActivity;
import com.example.escapadeserver3.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingViewHolder>{

    Context context;
    List<Category> categoryList;


    public ListingAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,viewGroup,false);
        return new ListingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder listingViewHolder, final int i) {

        Picasso.with(context)
                .load(categoryList.get(i).Link)
                .into(listingViewHolder.img_listing);


        listingViewHolder.txt_listing.setText(categoryList.get(i).Name);


        //implement itemclick
        listingViewHolder.setItemClickListener(new ItemClickListener() {

            @Override
            public void Onclick(View view, Boolean isLongClick) {
                if (isLongClick)
                {
                    Common.currentCategory = categoryList.get(i);
                    context.startActivity(new Intent(context,UpdateCategoryActivity.class));

                }else {

                    Common.currentCategory = categoryList.get(i);

                    context.startActivity(new Intent(context, ListingList.class));

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

}
