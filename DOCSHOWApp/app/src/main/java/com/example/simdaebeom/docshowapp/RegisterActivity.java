package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
///회원등록관련 서버///
public class  RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //각각 변수에 값이 담겨진다.
       final EditText idText = (EditText) findViewById(R.id.idText);
       final EditText passwordText = (EditText) findViewById(R.id.passwordText);
       final EditText nameText = (EditText) findViewById(R.id.nameText);
       final EditText ageText = (EditText) findViewById(R.id.ageText);


        Button registerButton = (Button)findViewById(R.id.registerButton);
       registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String userID = idText.getText().toString();
               String userPassword = passwordText.getText().toString();
               String userName = nameText.getText().toString();
               int userAge = Integer.parseInt(ageText.getText().toString());

               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try{
                            JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");
                           if(success) {
                               AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                               builder.setMessage("회원 등록이 성공 했습니다.").setPositiveButton("확인",null).create().show();
                               finish();

                           }
                           else{
                               AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                               builder.setMessage("회원 등록이 실패 했습니다.").setNegativeButton("다시 시도",null).create().show();

                           }
                       }
                       catch (JSONException e)
                       {
                           e.printStackTrace();

                       }
                   }

               };
               RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userName,userAge,responseListener);
               RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
               queue.add(registerRequest);


           }
       });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
