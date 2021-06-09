package com.example.escapadeserver3.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.escapadeserver3.Interface.ItemClickListener;
import com.example.escapadeserver3.R;

class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    TextView txt_order_id,order_longhouse_name,txt_order_package,txt_order_status,txt_order_price;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_order_id = (TextView)itemView.findViewById(R.id.txt_order_id);
        //  order_longhouse_name = (TextView)itemView.findViewById(R.id.txt_order_longhouse);
      //  txt_order_price = (TextView)itemView.findViewById(R.id.txt_order_price);
        //  txt_order_package = (TextView)itemView.findViewById(R.id.txt_order_package);
        txt_order_status = (TextView)itemView.findViewById(R.id.txt_order_status);

        itemView.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        itemClickListener.Onclick(v,false);
    }

}
