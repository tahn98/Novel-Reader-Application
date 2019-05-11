package com.tahn.novelgui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Content_novel_activity extends AppCompatActivity {

    TextView txtContent;
    static String key_book = "send03";
    static String key_chap = "send04";
    static String data_content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_novel_activity);

        Bundle extras = getIntent().getExtras();
        String id_book = extras.getString(key_book);
        String id_chap = extras.getString(key_chap);

        Toast.makeText(Content_novel_activity.this, "" + id_book + " " + id_chap, Toast.LENGTH_LONG).show();

        getContentChapter(id_book, id_chap);

        txtContent = findViewById(R.id.txtContent);

        txtContent.setText(data_content);

    }

    private void getContentChapter(final String book_id, final String chap_id ){
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Volley_Constant.Url_Base3
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Load Ok", "onResponse: " + response);
                JSONArray jsonArray = null;
                try{
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        data_content = object.getString("data");
                        txtContent.setText(data_content);
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
                params.put("chapter_id", chap_id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest1);
    }
}
