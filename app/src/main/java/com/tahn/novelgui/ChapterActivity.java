package com.tahn.novelgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

//abc
public class ChapterActivity extends AppCompatActivity {

    static String key_book = "send03";
    static String key_chap = "send04";

    public ChapterAdapter chapterAdapter;
    ListView listViewChap;
    public static ArrayList<ChapterSimple> chapterSimpleArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        listViewChap = findViewById(R.id.listChapter);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("sendID");
        getAllChapter(String.valueOf(id));


//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", "1", "12-04-1998", "1234"));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));
//        chapterSimpleArrayList.add(new ChapterSimple("Chap.1", 1, "12-04-1998", 1234));


        Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_LONG).show();

        chapterAdapter = new ChapterAdapter(ChapterActivity.this, chapterSimpleArrayList);
        listViewChap.setAdapter(chapterAdapter);


        listViewChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_chapter = ChapterActivity.chapterSimpleArrayList.get(position).getChapId();
                String id_book = ChapterActivity.chapterSimpleArrayList.get(position).getBookId();

                goToActivity(position, id_book, id_chapter);
            }
        });
    }

    private void getAllChapter(final String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.Url_Base2
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                                new ChapterSimple(object.getString("chapter_id"),
                                        object.getString("name"),
                                        object.getString("book_id"),
                                        object.getString("upload_date"),
                                        object.getString("number_of_read")));
                        Log.d("hhihi", "onResponse: " + object.getString("number_of_read"));
                    }
                    chapterAdapter.notifyDataSetChanged();

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("false", "onResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("book_id", id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void goToActivity(int position, String idbook, String idchapter){
        Intent intent = new Intent(ChapterActivity.this, Content_novel_activity.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putString(key_book, idbook);
        dataBundle.putString(key_chap, idchapter);
        intent.putExtras(dataBundle);

        startActivity(intent);
    }
}

