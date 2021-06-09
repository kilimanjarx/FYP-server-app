package com.example.escapadeserver3.Retrofit;

import com.example.escapadeserver3.Model.Category;
import com.example.escapadeserver3.Model.Longhouse;
import com.example.escapadeserver3.Model.Order;
import com.example.escapadeserver3.Model.Token;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IEscapadeApi {

    /*
    category management
     */


    @FormUrlEncoded
    @POST("server/category/add_category.php")
    Observable<String> addNewCategory(@Field("name") String name, @Field("imgPath") String imgPath);


    @Multipart
    @POST("server/category/upload_category_img.php")
    Call<String> uploadCategoryFile(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("server/category/update_category.php")
    Observable<String> updateCategory (@Field("id") String id,
                                       @Field("name") String name,
                                       @Field("imgPath") String imgPath);


    @FormUrlEncoded
    @POST("server/category/delete_category.php")
    Observable<String> deleteCategory (@Field("id") String id);

    /*

    listing management
     */

    @GET("getlisting.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("getsecondlisting.php")
    Observable<List<Longhouse>> getLonghouse(@Field("listingid") String listingID);

    @FormUrlEncoded
    @POST("server/product/add_listing.php")
    Observable<String> addNewListing(@Field("name") String name,
                                     @Field("imgPath") String imgPath,
                                     @Field("price") String price,
                                     @Field("listingId") String listingId,
                                     @Field("descriptions") String descriptions,
                                     @Field("packageOne") String packageOne,
                                     @Field("packageTwo") String packageTwo,
                                     @Field("moreDescriptions") String moreDescriptions,
                                     @Field("itinerary") String itinerary,
                                     @Field("imgPathTwo") String imgPathTwo,
                                     @Field("imgPathThree") String imgPathThree

    );

    @Multipart
    @POST("server/product/upload_product.php")
    Call<String> uploadListingFile(@Part MultipartBody.Part file);



    @FormUrlEncoded
    @POST("server/product/update_listing.php")
    Observable<String> updateListing (@Field("id") String id,
                                       @Field("name") String name,
                                       @Field("imgPath") String imgPath,
                                      @Field("price") String price,
                                      @Field("listingId") String listingId,
                                      @Field("descriptions") String descriptions,
                                      @Field("packageOne") String packageOne,
                                      @Field("packageTwo") String packageTwo,
                                      @Field("moreDescriptions") String moreDescriptions,
                                      @Field("itinerary") String itinerary,
                                      @Field("imgtwo") String imgtwo,
                                      @Field("imgthree") String imgthree
                                      );


    @FormUrlEncoded
    @POST("server/product/delete_listing.php")
    Observable<String> deleteListing (@Field("id") String id);

    @FormUrlEncoded
    @POST("server/order/getorder.php")
    Observable<List<Order>> getAllOrder(@Field("status") String status);

    //token update

    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token,
                             @Field("isServerToken") String isServerApp
                             );
    @FormUrlEncoded
    @POST("server/order/update_order_status.php")
    Observable<String> updateOrderStatus(@Field("phone") String phone,
                             @Field("order_id") long orderid,
                             @Field("status") long status
    );

    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("isServerToken") String isServerToken);







}
