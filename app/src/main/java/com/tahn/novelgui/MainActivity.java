package com.tahn.novelgui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Retrofit_Config.APIUtils;
import com.tahn.novelgui.Retrofit_Config.BookRetrofit;
import com.tahn.novelgui.Retrofit_Config.DataClient;

import com.tahn.novelgui.Volley_config.Book_Volley;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.takusemba.multisnaprecyclerview.OnSnapListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
//            switch (position){
//                case 0:
////                    Intent intent = new Intent(MainActivity.this, NovelActivity.class);
////                    startActivity(intent);
////                    break;
//                    ;
//                    break;
//                case 1:
//                    goToActivity(position);
//                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_LONG).show();
//                    break;
//                case 2:
//                    goToActivity(position);
//                    break;
//                case 3:
//
//            }
        }
    };


//    @Override
//    protected void onResume() {
//        novelArrayList.clear();
//        GetSomeThing();
//        super.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        novelArrayList.clear();
//
//        super.onStop();
//    }

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
        //////


        GetSomeThing();

        //novelArrayList.add(new Novel(1,"End Game", "", "5","",""));
//        novelArrayList.add(new Novel(1,"Infinity War", "", "5","",""));
        novelArrayListUpdate.add(new Novel(1,"Sword Art Online", "", "5","",""));

        NovelAdapter novelAdapter = new NovelAdapter(getApplicationContext(), novelArrayList);
        NovelAdapter novelAdapterUpdate = new NovelAdapter(getApplicationContext(), novelArrayListUpdate);
        firstRecyclerView.setAdapter(novelAdapter);
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
//        novelArrayList.add(new Novel(-1,"End Game", "", "5","",""));
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
    public String getRealPathFromURI (Uri contentUri) {
        /* lấy địa chỉ thật thừ điện thoại*/
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void goToActivity(int value){
        Intent intent = new Intent(MainActivity.this, NovelActivity.class);

        Bundle dataBundle = new Bundle();
        dataBundle.putInt(key, value);
        intent.putExtras(dataBundle);

        startActivity(intent);
    }
}
