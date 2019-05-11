package com.tahn.novelgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tahn.novelgui.CustomAdapter.CommentAdapter;
import com.tahn.novelgui.DataObject.Comment;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> commentArrayList = new ArrayList<>();
    ListView listViewComment;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listViewComment = findViewById(R.id.listViewComment);
        //String content, String id, String date_comment, String name_user
        commentArrayList.add(new Comment("Good Novel! I Love this", "1", "12-8-1998", "Phuong"));
        commentArrayList.add(new Comment("Good Novel! I Love this", "1", "12-8-1998", "Phuong"));
        commentArrayList.add(new Comment("Good Novel! I Love this", "1", "12-8-1998", "Phuong"));
        commentAdapter = new CommentAdapter(CommentActivity.this, commentArrayList);


        listViewComment.setAdapter(commentAdapter);

    }
}
