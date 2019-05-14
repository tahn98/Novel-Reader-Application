package com.tahn.novelgui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tahn.novelgui.CustomAdapter.NovelAdapterSimple;
import com.tahn.novelgui.DataObject.Comment;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookmarkActivity extends AppCompatActivity {

    ListView novelListView;
    public static ArrayList<Novel> novelArrayList;
    Toolbar toolbar;
    NovelAdapterSimple novelAdapterSimple;
    String user_name;
    String key = "send0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        user_name = LoginActivity.user_name_final;

        Toast.makeText(BookmarkActivity.this, user_name, Toast.LENGTH_LONG).show();

        novelArrayList = new ArrayList<>();
        //novelArrayList.add(new Novel("Avengers", "The End Game with Thanos", "26/04/2019", R.drawable.endgame));
        //int id, String name, String description, String author_name, String cover, String rating, String dateTime
//        novelArrayList.add(new Novel(1, "The End", "", "", "", "5", "12-09-1998"));
        novelListView = findViewById(R.id.lvBookMark);
        novelAdapterSimple = new NovelAdapterSimple(BookmarkActivity.this, novelArrayList);

        getAllFavortiteBook(user_name);

        novelListView.setAdapter(novelAdapterSimple);

        novelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToActivity(position);
            }
        });

        toolbar = findViewById(R.id.toolbarBookMark);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Favorite Novel");

    }

    public void getAllFavortiteBook(final String user_name){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.favorite_book_url
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

//                        commentArrayList.add(
//                                //String content, String id, String date_comment, String name_user
//                                new Comment(object.getString("comment_text"), "-1", object.getString("comment_date"), object.getString("name"))
                        novelArrayList.add(new Novel(
                                Integer.parseInt(object.getString("id")),
                                object.getString("name"),
                                object.getString("description"),
                                object.getString("author_name"),
                                object.getString("cover"),
                                object.getString("rating"))
                        );
                    }
                    novelAdapterSimple.notifyDataSetChanged();

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
                params.put("user_name", user_name);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void goToActivity(int pos){
        Intent intent = new Intent(BookmarkActivity.this, NovelActivity.class);
        Novel novel = novelArrayList.get(pos);

        intent.putExtra(key, novel);
        startActivity(intent);
    }
}
