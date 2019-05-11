package com.tahn.novelgui;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.tahn.novelgui.CustomAdapter.NovelAdapterSimple;
import com.tahn.novelgui.DataObject.Novel;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    ListView novelListView;
    ArrayList<Novel> novelArrayList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        novelArrayList = new ArrayList<>();
        //novelArrayList.add(new Novel("Avengers", "The End Game with Thanos", "26/04/2019", R.drawable.endgame));
        //int id, String name, String description, String author_name, String cover, String rating, String dateTime
        novelArrayList.add(new Novel(1, "The End", "", "", "", "5", "12-09-1998"));
        novelListView = findViewById(R.id.lvBookMark);
        NovelAdapterSimple novelAdapterSimple = new NovelAdapterSimple(BookmarkActivity.this, novelArrayList);
        novelListView.setAdapter(novelAdapterSimple);

        toolbar = findViewById(R.id.toolbarBookMark);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) BookmarkActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(BookmarkActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }
}
