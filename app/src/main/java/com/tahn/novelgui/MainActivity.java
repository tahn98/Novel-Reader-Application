package com.tahn.novelgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NovelAdapter novelAdapter;
    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    MultiSnapRecyclerView firstRecyclerView, secondRecycleView;
    public static ArrayList<Novel> novelArrayList = new ArrayList<>();
    public static ArrayList<Novel> novelArrayListUpdate = new ArrayList<>();
    public static final String key = "send0";


    //public static ArrayList<Book_Volley> Book_arrayList = new ArrayList<>();
    //event click on recycle view

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            goToActivity(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.bookMark:
                                Intent intentBookmark = new Intent(MainActivity.this,BookmarkActivity.class);
                                startActivity(intentBookmark);
                                break;
                            case R.id.search:
                                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                                startActivity(intentSearch);
                                break;
                        }
                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        addRecycleViewController();

        GetSomeThing();

        novelArrayListUpdate.add(new Novel(1,"Sword Art Online", "", "5","",""));

        novelAdapter = new NovelAdapter(getApplicationContext(), novelArrayList);
        NovelAdapter novelAdapterUpdate = new NovelAdapter(getApplicationContext(), novelArrayListUpdate);
        novelAdapter.notifyDataSetChanged();
        firstRecyclerView.setAdapter(novelAdapter);
        novelAdapter.notifyDataSetChanged();
        //event on recycle view
        secondRecycleView.setAdapter(novelAdapterUpdate);

        novelAdapter.setOnItemClickListener(onItemClickListener);

        /////////////
    }

    public void addRecycleViewController(){
        firstRecyclerView = findViewById(R.id.first_recycler_view);
        secondRecycleView = findViewById(R.id.second_recycler_view);

        LinearLayoutManager firstManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager secondManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        firstRecyclerView.setLayoutManager(firstManager);
        secondRecycleView.setLayoutManager(secondManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void GetSomeThing(){
        String url = Volley_Constant.Url_Base+ "getAllBookInfor.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<Integer> book_IDs = new ArrayList<>();
                        if(novelArrayList.size() >0){
                            for(int i = 0 ; i < novelArrayList.size();i++)
                                book_IDs.add(novelArrayList.get(i).getId());
                        }

                        int k;
                        if (response.length() > 5) k = 5;
                        else k = response.length();

                        for (int i = 0; i < k; i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String name = object.getString("name");

                                int id = object.getInt("id");
                                if (book_IDs.contains(id)) continue;

                                if (name.length() > 15){
                                    name = name.substring(0, 15) + "...";
                                }

                                novelArrayList.add(
                                        new Novel(id,
                                                name,
                                                object.getString("description"),
                                                object.getString("author_name"),
                                                object.getString("cover"),
                                                object.getString("rating"))
                                );
                                novelAdapter.notifyDataSetChanged();

//                                Log.d("TAG", "onResponse: " + novelArrayList.get(i).toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "FALSE" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestHandler.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public void goToActivity(int value){
        Intent intent = new Intent(MainActivity.this, NovelActivity.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putInt(key, value);
        intent.putExtras(dataBundle);

        startActivity(intent);
    }
}
