package com.tahn.novelgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tahn.novelgui.Volley_config.RequestHandler;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText edtName;
    EditText edtPass;
    Button btnReg;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnReg = findViewById(R.id.btnSignUp);
        btnLog = findViewById(R.id.btnLogin);
        edtName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPassword);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser(edtName.getText().toString(), edtPass.getText().toString());

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void LoginUser(final String user_name, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.login_url
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
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "False to Connect :(", Toast.LENGTH_LONG).show();
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
                params.put("username", user_name);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
