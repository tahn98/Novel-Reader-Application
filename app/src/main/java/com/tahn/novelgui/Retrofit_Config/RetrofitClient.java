package com.tahn.novelgui.Retrofit_Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    /*khởi tạo và cấu hình lại một số thuộc tính cho Retrofit
    (nếu muốn mặc định thì ko cần gọi phương thức này)*/
    private  static Retrofit retrofit = null;
    public static Retrofit gelClient(String baseURL){
        OkHttpClient builder = new OkHttpClient.Builder()
                                .readTimeout(5000, TimeUnit.MICROSECONDS)
                                .writeTimeout(5000, TimeUnit.MICROSECONDS)
                                .connectTimeout(10000,TimeUnit.MICROSECONDS)
                                .retryOnConnectionFailure(true)
                                .build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .client(builder)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
        return retrofit;
    }
}
