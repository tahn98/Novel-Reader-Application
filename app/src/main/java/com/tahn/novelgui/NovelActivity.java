package com.tahn.novelgui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.tahn.novelgui.CustomAdapter.NovelAdapter;
import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NovelActivity extends AppCompatActivity {

    int id;
    public Context context;
    Button btnRead;
    TextView txtRate;
    TextView txtComment;
    TextView txtName;
    TextView txtAuthor;
    ImageView imgNovel;
    TextView txtDesc;
    TextView txtRating;
    TextView txtLike;
    ArrayList<ChapterSimple> chapterSimples = new ArrayList<>();
    static String key = "sendID";
    static String keySendToCM = "sendCM";
    String key_fv = "favorite_key";
    public String num_rate = "";
    public String book_id = "";
    String user_name = "";
    public boolean flag_favor = false;
    public static String rate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        addControls();

//        Bundle extras = getIntent().getExtras();
//        int pos = extras.getInt(MainActivity.key);
//        id = MainActivity.novelArrayList.get(pos).getId();

        Novel novel = (Novel) getIntent().getSerializableExtra("send0");

        book_id = String.valueOf(novel.getId());
        user_name = LoginActivity.user_name_final;

        checkIfFavorite(book_id, user_name);

        //array list -> getid -> do data

//        String name = MainActivity.novelArrayList.get(pos).getName();
//        String author = MainActivity.novelArrayList.get(pos).getAuthor_name();
//        String desc = MainActivity.novelArrayList.get(pos).getDescription();
//        String rate = MainActivity.novelArrayList.get(pos).getRating();
//        String cover = MainActivity.novelArrayList.get(pos).getCover();

        String name = novel.getName();
        String author = novel.getAuthor_name();
        String desc = novel.getDescription();
        final String rate = novel.getRating();
        String cover = novel.getCover();


        String[] k = cover.split("\\/");
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

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(keySendToCM, id);
                intent.putExtras(dataBundle);

                startActivity(intent);
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChapterActivity.chapterSimpleArrayList.clear();
                Intent intent = new Intent(NovelActivity.this, ChapterActivity.class);

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(key, Integer.parseInt(book_id));
                intent.putExtras(dataBundle);

                startActivity(intent);
            }
        });

        txtRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRatting(user_name, book_id);
//                ShowDialog(rate);
            }
        });

        txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavorite(book_id, user_name);
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
        txtRate = findViewById(R.id.txtRate);
        txtLike = findViewById(R.id.txtFavor);
    }

    public void ShowDialog(String start) {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        final RatingBar rating = new RatingBar(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayout.setGravity(Gravity.CENTER);
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);
        rating.setRating(Float.parseFloat(start));

        //add ratingBar to linearLayout
        linearLayout.addView(rating);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("RATING NOVEL <3");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                System.out.println("Rated val:"+v);
                rating.setRating(v);
            }
        });



        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        textView.setText(String.valueOf(rating.getProgress()));
//                        Toast.makeText(NovelActivity.this, String.valueOf(rating.getProgress()), Toast.LENGTH_LONG).show();
                        num_rate = String.valueOf(rating.getProgress());
                        Toast.makeText(NovelActivity.this, book_id + " " + user_name + " " + num_rate, Toast.LENGTH_LONG).show();
                        if (rate != num_rate) {
                            insertRate(user_name, book_id, num_rate);
                        }
                        dialog.dismiss();
                    }
                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();
    }

    public void checkIfFavorite(final String book_id, final String user_name){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.if_favor_url
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

                        String result = object.getString("error");
                        if (result.equals("false")){
                            String isFavor = object.getString("IsFavorite");

                            if (isFavor.equals("true")){
//                                Toast.makeText(NovelActivity.this, "Thich", Toast.LENGTH_LONG).show();
                                flag_favor = true;
                            }else{
//                                Toast.makeText(NovelActivity.this, "ko thich", Toast.LENGTH_LONG).show();
                                flag_favor = false;
                            }
                            setColorFavorite(flag_favor);
                        }
                        else{
                            Toast.makeText(NovelActivity.this, "Can not connect", Toast.LENGTH_LONG).show();
                        }
                    }
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
                params.put("book_id", book_id);
                params.put("user_name", user_name);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void changeFavorite(final String book_id, final String user_name){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.change_favor_url
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

                        String result = object.getString("error");
                        if (result.equals("false")){
                            //doi mau hinh anh favor
//                            Toast.makeText(NovelActivity.this, "doi mau", Toast.LENGTH_LONG).show();
                            flag_favor = !flag_favor;
                            setColorFavorite(flag_favor);
                        }
                        else{
                            Toast.makeText(NovelActivity.this, "Can not connect", Toast.LENGTH_LONG).show();
                        }
                    }
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
                params.put("book_id", book_id);
                params.put("user_name", user_name);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void setColorFavorite(boolean flag){

        if(flag == true){
            txtLike.setBackgroundResource(R.drawable.ic_favorite_red_24dp_);
        }else{
            txtLike.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }

    }

    public void getRatting(final String user_name, final String book_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.getRateOfUser_url
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
                        String result = object.getString("isRating");
                        if (result.equals("true")){
//                            Toast.makeText(NovelActivity.this, "Not Rate" + object.getString("rate"), Toast.LENGTH_LONG).show();
                            rate = object.getString("rate");
                            Log.d("abd", "onResponse: " + rate);
                            ShowDialog(rate);
                        }
                    }
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
                params.put("book_id", book_id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void insertRate(final String user_name, final String book_id, final String _rate){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.insertRate_url
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
                        String result = object.getString("error");
                        if (result.equals("false")){
//                            Toast.makeText(NovelActivity.this, "Thanh Cong", Toast.LENGTH_LONG).show();
//                            rate = object.getString("rate");
                        }
                    }
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
                params.put("book_id", book_id);
                params.put("rate", _rate);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
