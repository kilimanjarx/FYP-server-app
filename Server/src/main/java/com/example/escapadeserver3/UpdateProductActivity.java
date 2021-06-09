package com.example.escapadeserver3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.escapadeserver3.Retrofit.IEscapadeApi;
import com.example.escapadeserver3.Utils.Common;
import com.example.escapadeserver3.Utils.ProgressRequestBody;
import com.example.escapadeserver3.Utils.UploadCallBack;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductActivity extends AppCompatActivity implements UploadCallBack {

    private static final int PICK_FILE_REQUEST = 1111;
    private static final int PICK_SECOND_REQUEST = 1212;
    private static final int PICK_THIRD_REQUEST = 1313;

    ImageView img_browser,img_second,img_third;
    EditText edt_name,edt_price,edt_description,edt_package1,edt_package2,edt_more_description,edt_itinerary;
    Button btn_update,btn_delete;
    IEscapadeApi mService;
    CompositeDisposable compositeDisposable;

    Uri selectedUri=null,second_uri=null,third_uri=null;
    String uploaded_img_path = "",selected_category="",uploaded_second_path="",second_selected="",uploaded_third_path="",third_selected="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        if (Common.currentLonghouse !=null)
        {
            uploaded_img_path = Common.currentLonghouse.Link;
            selected_category = Common.currentLonghouse.ListingId;
            uploaded_second_path = Common.currentLonghouse.ImageTwo;
            uploaded_third_path = Common.currentLonghouse.ImageThree;

        }




        btn_delete = (Button)findViewById(R.id.delete_btn);
        btn_update = (Button)findViewById(R.id.btn_update);
        edt_name = (EditText)findViewById(R.id.edt_listing_name);
        edt_price = (EditText)findViewById(R.id.edt_listing_price);
        edt_description = (EditText)findViewById(R.id.edt_listing_descriptions);
        edt_package1 = (EditText)findViewById(R.id.edt_listing_package1);
        edt_package2 = (EditText)findViewById(R.id.edt_listing_package2);
        img_browser = (ImageView) findViewById(R.id.placeImageView);
        edt_more_description = (EditText)findViewById(R.id.edt_more_descriptions);
        edt_itinerary = (EditText)findViewById(R.id.edt_itinerary);

        img_second = (ImageView) findViewById(R.id.img_browser2);
        img_third = (ImageView) findViewById(R.id.img_browser3);

        //API
        mService = Common.getApi();
        compositeDisposable = new CompositeDisposable();
        setProducInfo();

        //event
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



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory();
            }
        });

    }

    private void setProducInfo() {

        if (Common.currentLonghouse != null)
        {
            edt_name.setText(Common.currentLonghouse.Name);
            edt_price.setText(Common.currentLonghouse.Price);
            edt_description.setText(Common.currentLonghouse.descriptions);
            edt_package1.setText(Common.currentLonghouse.PackageOne);
            edt_package2.setText(Common.currentLonghouse.PackageTwo);
            edt_more_description.setText(Common.currentLonghouse.MoreDescription);
            edt_itinerary.setText(Common.currentLonghouse.Itinerary);


            Picasso.with(this)
                    .load(Common.currentLonghouse.Link)
                    .into(img_browser);
            Picasso.with(this)
                    .load(Common.currentLonghouse.ImageTwo)
                    .into(img_second);
            Picasso.with(this)
                    .load(Common.currentLonghouse.ImageThree)
                    .into(img_third);

        }
    }

    private void deleteCategory() {


        compositeDisposable.add(mService.deleteListing(Common.currentLonghouse.ID)
        .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(UpdateProductActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(UpdateProductActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
        );


    }

    private void updateCategory() {

        if (!edt_name.getText().toString().isEmpty())
        {
            compositeDisposable.add(mService.updateListing(Common.currentLonghouse.ID,edt_name.getText().toString(),
                    uploaded_img_path,
                    edt_price.getText().toString(),
                    selected_category,edt_description.getText().toString(),
                    edt_package1.getText().toString(),
                    edt_package2.getText().toString(),edt_more_description.getText().toString(),edt_itinerary.getText().toString(),uploaded_second_path,uploaded_third_path
                    ).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(UpdateProductActivity.this, s, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(UpdateProductActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })

            );
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            if (requestCode == PICK_FILE_REQUEST)
            {
                if (data!=null)
                {
                    selectedUri = data.getData();
                    if (selectedUri != null && !selectedUri.getPath().isEmpty())
                    {
                        img_browser.setImageURI(selectedUri);
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

        if (selectedUri != null)
        {
            File file = FileUtils.getFile(this,selectedUri);
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
                                    Toast.makeText(UpdateProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(UpdateProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                    uploaded_third_path= new StringBuilder(Common.BASE_URL)
                                            .append("server/product/")
                                            .append(response.body().toString())
                                            .toString();

                                    Log.d("imgPath3",uploaded_third_path);

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(UpdateProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }).start();

        }





    }

    @Override
    public void onProgressUpdate(int percentage) {

    }
}
