package com.example.escapadeserver3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.escapadeserver3.Model.Cart;
import com.example.escapadeserver3.R;
import com.example.escapadeserver3.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    Context context;
    List<Cart> itemList;

    public OrderDetailAdapter(Context context) {
        this.context = context;
        this.itemList = new Gson().fromJson(Common.currentOrder.getOrderDetail(), new TypeToken<List<Cart>>(){}.getType());
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_detail_layout,viewGroup,false);
        return new OrderDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder orderDetailViewHolder, int i) {

        orderDetailViewHolder.longhouse.setText(itemList.get(i).getLonghouse());
        orderDetailViewHolder.price.setText(new StringBuilder("RM").append(itemList.get(i).getPrice()));
        orderDetailViewHolder.packageType.setText(itemList.get(i).getPackageType());
        orderDetailViewHolder.date.setText(new StringBuilder("Date chosen: ").append(itemList.get(i).getDate()));

        Picasso.with(context).load(itemList.get(i).getLink()).into(orderDetailViewHolder.img_order);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        ImageView img_order;
        TextView date,longhouse,packageType,price;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            img_order = (ImageView)itemView.findViewById(R.id.image_order_item);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            longhouse = (TextView) itemView.findViewById(R.id.txt_longhouse_name);
            packageType = (TextView) itemView.findViewById(R.id.txt_package_type);
            price = (TextView) itemView.findViewById(R.id.txt_price);



        }
    }
}
