package com.tahn.novelgui.Retrofit_Config;

import android.database.Cursor;
import android.provider.MediaStore;

public class APIUtils {
    public static final String BaseURL = "http://192.168.56.1:8080/DB001/";
    //public static final String BaseURL = "http://192.168.56.1:8080/android/include/";
    public static DataClient getData(){
        /*gửi và nhận dữ liệu thông qua interface DataClient*/
        return RetrofitClient.gelClient(BaseURL).create(DataClient.class);
    }


}
