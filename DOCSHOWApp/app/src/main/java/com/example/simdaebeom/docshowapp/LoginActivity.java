package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private String doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button)findViewById(R.id.loginButton);
        final TextView registerButton   =(TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new BackgroundTask().execute();
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                SecurityUtil securityUtil = new SecurityUtil();
                userPassword= securityUtil.encryptSHA256(userPassword);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                String userID =jsonResponse.getString("userID");
                                String userPassword =jsonResponse.getString("userPassword");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPassword",userPassword);
//                                intent.putExtra("doctors",doctors);
                                LoginActivity.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.").setNegativeButton("다시 시도",null)
                                ;
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                                negativeButton.setBackgroundColor(Color.parseColor("#000000"));

                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
    }

}