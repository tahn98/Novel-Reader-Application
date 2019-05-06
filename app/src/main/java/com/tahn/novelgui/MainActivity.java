package com.tahn.novelgui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
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

import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.Novel;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.takusemba.multisnaprecyclerview.OnSnapListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    MultiSnapRecyclerView firstRecyclerView, secondRecycleView;
    ArrayList<Novel> novelArrayList = new ArrayList<>();
    ArrayList<Novel> novelArrayListUpdate = new ArrayList<>();

    //event click on recycle view
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            switch (position){
                case 0:
                    Toast.makeText(MainActivity.this, "0", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "1", Toast.LENGTH_LONG).show();
                    break;
            }
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
                                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                                startActivity(intent);
                        }
                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addRecycleViewController();

        novelArrayList.add(new Novel("End Game", R.drawable.endgame, "5"));
        novelArrayList.add(new Novel("Infinity War", R.drawable.infinity, "5"));
        novelArrayListUpdate.add(new Novel("Sword Art Online", R.drawable.sword, "5"));

        NovelAdapter novelAdapter = new NovelAdapter(getApplicationContext(), novelArrayList);
        NovelAdapter novelAdapterUpdate = new NovelAdapter(getApplicationContext(), novelArrayListUpdate);
        firstRecyclerView.setAdapter(novelAdapter);
        //event on recycle view
        secondRecycleView.setAdapter(novelAdapterUpdate);

        novelAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void addRecycleViewController(){
        firstRecyclerView = findViewById(R.id.first_recycler_view);
        secondRecycleView = findViewById(R.id.second_recycler_view);

        LinearLayoutManager firstManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager secondManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        firstRecyclerView.setLayoutManager(firstManager);
        secondRecycleView.setLayoutManager(secondManager);
    }
}
