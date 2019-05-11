package com.tahn.novelgui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.tahn.novelgui.CustomAdapter.ChapterAdapter;
import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChapterActivity extends AppCompatActivity {

    ListView listViewChap;
    public static ArrayList<ChapterSimple> chapterSimpleArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
//
        listViewChap = findViewById(R.id.listChapter);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("sendID");
        getAllChapter(String.valueOf(id));

        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", "1", "12-04-1998", "1234"));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));


        Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_LONG).show();

        ChapterAdapter chapterAdapter = new ChapterAdapter(ChapterActivity.this, chapterSimpleArrayList);
        listViewChap.setAdapter(chapterAdapter);
    }

    private void getAllChapter(final String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.Url_Base2
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int k;
                if (response.length() > 5) k = 5;
                else k = response.length();

                JSONArray jsonArray = null;
                try{
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    for (int i = 0 ; i < jsonArray.length(); i++){
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        chapterSimpleArrayList.add(
                                new ChapterSimple(
                                        object.getString("name"),
                                        object.getString("book_id"),
                                        "abc",
                                        "abc"));
                        Log.d("hhihi", "onResponse: " + object.getString("number_of_read"));
                    }

                } catch (JSONException e) {


                }

//                for (int i = 0; i < k; i++) {
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//
//                        chapterSimpleArrayList.add(
//                                new ChapterSimple(
//                                        object.getString("name"),
//                                        Integer.parseInt(object.getString("book_id")),
//                                        object.getString("upload_date"),
//                                        Integer.parseInt(object.getString("number_of_read")))
//                        );
//
//                        Log.d("haha", "onResponse: " + object.getString("upload_date"));
//                                Log.d("TAG", "onResponse: " + novelArrayList.get(i).toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("huhu", "onResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("book_id", "1");
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}

