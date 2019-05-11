package com.tahn.novelgui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import java.util.ArrayList;

public class NovelActivity extends AppCompatActivity {

    int id;
    public Context context;
    Button btnRead;
    TextView txtComment;
    TextView txtName;
    TextView txtAuthor;
    ImageView imgNovel;
    TextView txtDesc;
    TextView txtRating;
    ArrayList<ChapterSimple> chapterSimples = new ArrayList<>();
    static String key = "sendID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        addControls();

        Bundle extras = getIntent().getExtras();
        int pos = extras.getInt(MainActivity.key);
        id = MainActivity.novelArrayList.get(pos).getId();

        String name = MainActivity.novelArrayList.get(pos).getName();
        String author = MainActivity.novelArrayList.get(pos).getAuthor_name();
        String desc = MainActivity.novelArrayList.get(pos).getDescription();
        String rate = MainActivity.novelArrayList.get(pos).getRating();
        String cover = MainActivity.novelArrayList.get(pos).getCover();

        String[] k = cover.toString().split("\\/");
        String l = k[k.length-1];

        txtName.setText(name);
        txtAuthor.setText(author);
        txtDesc.setText(desc);
        txtRating.setText(rate);
        Picasso.with(getApplicationContext()).load(Volley_Constant.Url_Base1 +l)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.endgame)
                .into(imgNovel);

        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovelActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog dialog = new Dialog(NovelActivity.this);
//                dialog.setContentView(R.layout.chapter_dialog);
//                dialog.setTitle("Chapter List");
//                ListView list = dialog.findViewById(R.id.listChapter);
//                NovelAdapter novelAdapter = new NovelAdapter(NovelActivity.this, chapterSimples);
//                list.setAdapter((ListAdapter) novelAdapter);
//                dialog.show();


                Intent intent = new Intent(NovelActivity.this, ChapterActivity.class);

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(key, id);
                intent.putExtras(dataBundle);

                startActivity(intent);
            }
        });


    }

    public void addControls(){
        txtName = findViewById(R.id.txtNovelName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtDesc = findViewById(R.id.txtDesc);
        txtRating = findViewById(R.id.txtRating);
        txtComment = findViewById(R.id.txtComment);
        imgNovel = findViewById(R.id.imgNovel);
        btnRead = findViewById(R.id.btnRead);
    }
}
