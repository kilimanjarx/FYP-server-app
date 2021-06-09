package com.example.escapadeserver3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.escapadeserver3.Adapter.LonghouseListAdapter;
import com.example.escapadeserver3.Model.Longhouse;
import com.example.escapadeserver3.Retrofit.IEscapadeApi;
import com.example.escapadeserver3.Utils.Common;
import com.example.escapadeserver3.Utils.ProgressRequestBody;
import com.example.escapadeserver3.Utils.UploadCallBack;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingList extends AppCompatActivity implements UploadCallBack {

    private static final int PICK_FILE_REQUEST = 1111;
    private static final int PICK_SECOND_REQUEST = 1212;
    private static final int PICK_THIRD_REQUEST = 1313;


    IEscapadeApi mService;
    RecyclerView recycler_longhouse;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    FloatingActionButton btn_add;

    ImageView img_browser,img_second,img_third;
    EditText  edt_listing_name, edt_listing_price,edt_listing_descriptions,edt_listing_packageOne,edt_listing_packageTwo,edt_more_descriptions,edt_itinerary;
    Uri selected_uri,second_uri,third_uri;
    String uploaded_img_path = "",uploaded_second_path="",upload_third_path="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_list);

        mService = Common.getApi();

        recycler_longhouse =(RecyclerView)findViewById(R.id.recycler_listing_list);
        recycler_longhouse.setLayoutManager(new GridLayoutManager(this,2));
        recycler_longhouse.setHasFixedSize(true);

        btn_add = (FloatingActionButton)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListingDialog();
            }
        });

        loadLonghouseList(Common.currentCategory.ID);

    }

    private void showAddListingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Listing");

        View view = LayoutInflater.from(this).inflate(R.layout.add_new_listing_layout,null);

        edt_listing_name = (EditText)view.findViewById(R.id.edt_listing_name);
        edt_listing_price = (EditText)view.findViewById(R.id.edt_listing_price);
        edt_listing_descriptions = (EditText)view.findViewById(R.id.edt_listing_descriptions);
        edt_listing_packageOne = (EditText)view.findViewById(R.id.edt_listing_package1);
        edt_listing_packageTwo = (EditText)view.findViewById(R.id.edt_listing_package2);
        img_browser = (ImageView)view.findViewById(R.id.img_browser);
        img_second = (ImageView)view.findViewById(R.id.img_browser2);
        img_third = (ImageView)view.findViewById(R.id.img_browser3);


        img_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file"),PICK_FILE_REQUEST);

            }
        });

        img_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file"),PICK_SECOND_REQUEST);

            }
        });

        img_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file"),PICK_THIRD_REQUEST);

            }
        });

        edt_more_descriptions = (EditText)view.findViewById(R.id.edt_more_descriptions);
        edt_itinerary = (EditText)view.findViewById(R.id.edt_itinerary);






        builder.setView(view);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                uploaded_img_path = "";
                selected_uri=null;
                uploaded_second_path = "";
                second_uri = null;
                upload_third_path = "";
                third_uri = null;
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edt_listing_name.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE ENTER NAME OF CATEGORY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_listing_price.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE ENTER NAME OF CATEGORY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_listing_descriptions.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE ENTER NAME OF CATEGORY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_listing_packageOne.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE ENTER NAME OF CATEGORY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_listing_packageTwo.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE ENTER NAME OF CATEGORY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uploaded_img_path.isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE SELECT IMAGE", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_more_descriptions.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE SELECT IMAGE", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_itinerary.getText().toString().isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE SELECT IMAGE", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uploaded_second_path.isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE SELECT SECOND IMAGE", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (upload_third_path.isEmpty())
                {
                    Toast.makeText(ListingList.this, "PLEASE SELECT THIRD IMAGE", Toast.LENGTH_SHORT).show();
                    return;
                }

                compositeDisposable.add(mService.addNewListing(edt_listing_name.getText().toString(),uploaded_img_path,edt_listing_price.getText().toString(),Common.currentCategory.ID,edt_listing_descriptions.getText().toString(),edt_listing_packageOne.getText().toString(),edt_listing_packageTwo.getText().toString(),edt_more_descriptions.getText().toString()
                        ,edt_itinerary.getText().toString(),
                        uploaded_second_path,upload_third_path)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                                Toast.makeText(ListingList.this, s, Toast.LENGTH_SHORT).show();
                                loadLonghouseList(Common.currentCategory.getID());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(ListingList.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));

            }
        }).show();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_FILE_REQUEST)
            {
                if (data!=null)
                {
                    selected_uri = data.getData();
                    if (selected_uri != null && !selected_uri.getPath().isEmpty())
                    {
                        img_browser.setImageURI(selected_uri);
                        uploadFileToServer();
                    }

                    else Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }
            }
        }

        //do next img pick
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_SECOND_REQUEST)
            {
                if (data!=null)
                {
                    second_uri = data.getData();
                    if (second_uri != null && !second_uri.getPath().isEmpty())
                    {
                        img_second.setImageURI(second_uri);
                        uploadFileToServer();
                    }

                    else Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PICK_THIRD_REQUEST)
            {
                if (data!=null)
                {
                    third_uri = data.getData();
                    if (third_uri != null && !third_uri.getPath().isEmpty())
                    {
                        img_third.setImageURI(third_uri);
                        uploadFileToServer();
                    }

                    else Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                }
            }
        }



    }
    private void uploadFileToServer() {

        if (selected_uri != null)
        {
            File file = FileUtils.getFile(this,selected_uri);
            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString())).toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",fileName,requestFile);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadListingFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    uploaded_img_path= new StringBuilder(Common.BASE_URL)
                                            .append("server/product/")
                                            .append(response.body().toString())
                                            .toString();

                                    Log.d("imgPath",uploaded_img_path);

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(ListingList.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }).start();

        }

        if (second_uri != null)
        {
            File file = FileUtils.getFile(this,second_uri);
            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString())).toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",fileName,requestFile);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadListingFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    uploaded_second_path= new StringBuilder(Common.BASE_URL)
                                            .append("server/product/")
                                            .append(response.body().toString())
                                            .toString();

                                    Log.d("imgPath2",uploaded_second_path);

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(ListingList.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }).start();

        }

        if (third_uri != null)
        {
            File file = FileUtils.getFile(this,third_uri);
            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString())).toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",fileName,requestFile);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadListingFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    upload_third_path= new StringBuilder(Common.BASE_URL)
                                            .append("server/product/")
                                            .append(response.body().toString())
                                            .toString();

                                    Log.d("imgPath3",upload_third_path);

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(ListingList.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }).start();

        }


    }

    private void loadLonghouseList(String id) {
        compositeDisposable.add(mService.getLonghouse(id).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Longhouse>>() {
            @Override
            public void accept(List<Longhouse> longhouses) throws Exception {
                displayLonghouseList(longhouses);
            }
        }));
    }

    private void displayLonghouseList(List<Longhouse> longhouses) {
        LonghouseListAdapter adapter = new LonghouseListAdapter(this,longhouses);
        recycler_longhouse.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        loadLonghouseList(Common.currentCategory.getID());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
     compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }
}
