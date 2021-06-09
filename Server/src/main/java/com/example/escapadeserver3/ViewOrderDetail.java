package com.example.escapadeserver3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.escapadeserver3.Adapter.OrderDetailAdapter;
import com.example.escapadeserver3.Model.DataMessage;
import com.example.escapadeserver3.Model.MyResponse;
import com.example.escapadeserver3.Model.Order;
import com.example.escapadeserver3.Model.Token;
import com.example.escapadeserver3.Retrofit.IEscapadeApi;
import com.example.escapadeserver3.Retrofit.IFCMService;
import com.example.escapadeserver3.Utils.Common;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetail extends AppCompatActivity {

    TextView txt_order_id,txt_order_price,txt_username;
    Spinner spinner_order_status;
    RecyclerView recycler_order_detail;
    String[] spinner_source = new String[]
            {
              "Cancel","New", "Processed","Approved"
            };

    IEscapadeApi mService;
    IFCMService ifcmService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail);

        mService = Common.getApi();
        ifcmService = Common.getFCMAPI();

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        txt_order_id = (TextView)findViewById(R.id.txt_order_id);
       // txt_order_price = (TextView)findViewById(R.id.txt_order_price);
        txt_username = (TextView)findViewById(R.id.txt_username);


        spinner_order_status = (Spinner)findViewById(R.id.spinner_order_status);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinner_source);
        spinner_order_status.setAdapter(arrayAdapter);



        recycler_order_detail = (RecyclerView)findViewById(R.id.recycler_order_detail);

        recycler_order_detail.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_detail.setAdapter(new OrderDetailAdapter(this));

        txt_order_id.setText(new StringBuilder("#")
        .append(Common.currentOrder.getOrderId())
        );

        //txt_order_price.setText(new StringBuilder("Order Status: ").append(Common.currentOrder.getOrderStatus()));
        txt_username.setText(new StringBuilder("Order by: ").append(Common.currentOrder.getUserphone()));



        setSpinnerSelected();






    }

    private void setSpinnerSelected() {
        switch (Common.currentOrder.getOrderStatus())
        {
            case -1:
                spinner_order_status.setSelection(0);
                break;
            case 0:
                spinner_order_status.setSelection(1);
                break;
            case 1:
                spinner_order_status.setSelection(2);
                break;
            case 2:
                spinner_order_status.setSelection(3);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_order_detail)
            saveUpdateOrder();
        return true;
    }

    private void saveUpdateOrder() {

        final int order_status = spinner_order_status.getSelectedItemPosition()-1;

        compositeDisposable.add(mService.updateOrderStatus(Common.currentOrder.getUserphone(),Common.currentOrder.getOrderId(),order_status)
        .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        sendOrderUpdateNoti(Common.currentOrder,order_status);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("error",""+throwable.getMessage());
                    }
                })
        );

    }

    private void sendOrderUpdateNoti(final Order currentOrder, final int order_status) {
        Toast.makeText(this, "Order updated!", Toast.LENGTH_SHORT).show();
        finish();

        mService.getToken(currentOrder.getUserphone(),"0")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Token userToken = response.body();
                        DataMessage dataMessage = new DataMessage();
                        dataMessage.to = userToken.getToken();
                        Map<String,String> dataSend = new HashMap<>();
                        dataMessage.setData(dataSend);
                        dataSend.put("title","Your order has been updated!");
                        dataSend.put("body","Order #"+currentOrder.getOrderId()+" "+" has been updated: "+""+Common.convertCodeToStatus(order_status));


                        ifcmService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.body().success == 1)
                                        {
                                            Toast.makeText(ViewOrderDetail.this, "Order Updated", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(ViewOrderDetail.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(ViewOrderDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


}
