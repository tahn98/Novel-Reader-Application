package com.tahn.novelgui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.tahn.novelgui.CustomAdapter.CommentAdapter;
import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.DataObject.Comment;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> commentArrayList = new ArrayList<>();
    ListView listViewComment;
    CommentAdapter commentAdapter;
    static String keySendToCM = "sendCM";
    private String idBook;
    private String comment_content;
    private String date_comment;
    Button btnComment;
    EditText edtComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listViewComment = findViewById(R.id.listViewComment);
        btnComment = findViewById(R.id.btnEnterComment);
        edtComment = findViewById(R.id.edtEnterComment);

        commentAdapter = new CommentAdapter(CommentActivity.this, commentArrayList);

        Bundle extras = getIntent().getExtras();
        idBook = String.valueOf(extras.getInt(keySendToCM));
        GetSomeThing(idBook);

        listViewComment.setAdapter(commentAdapter);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                comment_content = edtComment.getText().toString();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Log.d("TAG", "onClick: " + dtf.format(now));
                date_comment = dtf.format(now);

                if(comment_content.trim().equals("")) {
                    //final String book_id,final String user_id, final String comment_content,final String date_comment
                    Toast.makeText(CommentActivity.this, "deo co comment", Toast.LENGTH_LONG).show();

                }else{
                    InsertCommand(idBook, "1", comment_content, date_comment);
                    Toast.makeText(CommentActivity.this, "", Toast.LENGTH_LONG).show();
                }

//                Toast.makeText(CommentActivity.this, comment_content + " " + date_comment, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GetSomeThing(final String id){
        commentArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.Url_base4
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

                        commentArrayList.add(
                                //String content, String id, String date_comment, String name_user
                                new Comment(object.getString("comment_text"), "-1", object.getString("comment_date"), object.getString("name"))
                        );
                    }
                    commentAdapter.notifyDataSetChanged();

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
                params.put("book_id", id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void InsertCommand(final String book_id,final String user_id, final String comment_content,final String date_comment){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.insert_comment_url
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

//                        commentArrayList.add(
//                                //String content, String id, String date_comment, String name_user
//                                new Comment(object.getString("comment_text"), "-1", object.getString("comment_date"), object.getString("name"))
//                        );
                        String result = object.getString("error");
                        if (result.equals("false")){
                            Toast.makeText(CommentActivity.this, "Successfully", Toast.LENGTH_LONG).show();
                            GetSomeThing(idBook);
                        }
                        else{
                            Toast.makeText(CommentActivity.this, "Can not comment", Toast.LENGTH_LONG).show();
                        }
                    }
                    commentAdapter.notifyDataSetChanged();

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
                params.put("user_id", user_id);
                params.put("comment_text", comment_content);
                params.put("comment_date", date_comment);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
