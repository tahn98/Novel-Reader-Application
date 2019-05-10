package com.tahn.novelgui.Retrofit_Config;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {

    //gửi data lên server dưới dạng dũ liệu chuổi


    @GET("get_all_book.php")
    Call<ArrayList<BookRetrofit>> GetAllBook();

    @FormUrlEncoded
    @POST("loginUser.php")
    Call<String> Login(@Field("username")String taikhoan
            ,@Field("password")String matkhau);
}
