package com.tahn.novelgui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tahn.novelgui.CustomAdapter.NovelAdapterSimple;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    NovelAdapterSimple novelAdapterSimple;
    ListView novelListView;
    ArrayList<Novel> novelArrayList;
    NovelAdapterSimple adapter_temp;
    SearchView mSearchView;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GetAllBook();
        novelArrayList = new ArrayList<>();
        //novelArrayList.add(new Novel("Avengers", "The End Game with Thanos", "26/04/2019", R.drawable.endgame));
        //int id, String name, String description, String author_name, String cover, String rating, String dateTime
//        novelArrayList.add(new Novel(1, "The End", "", "", "", "5", "12-09-1998"));
        novelListView = findViewById(R.id.listViewAllNovel);
        mSearchView = findViewById(R.id.searchView1);
        novelAdapterSimple = new NovelAdapterSimple(SearchActivity.this, novelArrayList);
        adapter_temp = novelAdapterSimple;
        novelListView.setAdapter(novelAdapterSimple);

        novelListView.setTextFilterEnabled(true);
        setupSearch();

        novelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToActivity(position);
            }
        });
    }

    private void setupSearch() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here By Name/Author...");
    }

    public void GetAllBook(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Volley_Constant.get_all_book, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<Integer> book_IDs = new ArrayList<>();
                        if(novelArrayList.size() > 0){
                            for(int i = 0 ; i < novelArrayList.size(); i++)
                                book_IDs.add(novelArrayList.get(i).getId());
                        }

                        int k = response.length();

                        for (int i = 0; i < k; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String name = object.getString("name");

                                int id = object.getInt("id");
                                if (book_IDs.contains(id)) continue;

                                if (name.length() > 30){
                                    name = name.substring(0, 30) + "...";
                                }

                                novelArrayList.add(
                                        new Novel(id,
                                                name,
                                                object.getString("description"),
                                                object.getString("author_name"),
                                                object.getString("cover"),
                                                object.getString("rating"))
                                );
                                novelAdapterSimple.notifyDataSetChanged();
                                adapter_temp.notifyDataSetChanged();

//                                Log.d("TAG", "onResponse: " + novelArrayList.get(i).toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "FALSE" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestHandler.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        novelAdapterSimple.getFilter().filter(s);
        NovelAdapterSimple novelAdapterSimple1 = new NovelAdapterSimple(SearchActivity.this, novelAdapterSimple.getSearchList());
        novelListView.setAdapter(novelAdapterSimple1);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public void goToActivity(int value){
        Intent intent = new Intent(SearchActivity.this, NovelActivity.class);
        Novel novel = novelArrayList.get(value);

        intent.putExtra("send0", novel);
        startActivity(intent);
    }
}
