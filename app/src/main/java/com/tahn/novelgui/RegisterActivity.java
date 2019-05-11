package com.tahn.novelgui;

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

public class RegisterActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassWord;
    EditText edtMail;
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControls();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertCommand(edtUserName.getText().toString(), edtMail.getText().toString(), edtPassWord.getText().toString());
            }
        });

    }

    public void addControls(){
        edtUserName = findViewById(R.id.edtRegUserName);
        edtPassWord = findViewById(R.id.edtRegPassword);
        edtMail = findViewById(R.id.edtRegGmail);
        btnReg = findViewById(R.id.btnRegister);
    }

    public void InsertCommand(final String user_name, final String email, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Volley_Constant.register_url
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
                            Toast.makeText(RegisterActivity.this, "Successfully Register", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
