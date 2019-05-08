package com.tahn.novelgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class NovelActivity extends AppCompatActivity {

    TextView txtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        addControls();
        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovelActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addControls(){
        txtComment = findViewById(R.id.txtComment);
    }
}
