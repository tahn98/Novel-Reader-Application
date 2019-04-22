package com.tahn.novelgui;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.Novel;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    MultiSnapRecyclerView firstRecyclerView, secondRecycleView;
    ArrayList<Novel> novelArrayList = new ArrayList<>();
    ArrayList<Novel> novelArrayListUpdate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

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
        secondRecycleView.setAdapter(novelAdapterUpdate);
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
