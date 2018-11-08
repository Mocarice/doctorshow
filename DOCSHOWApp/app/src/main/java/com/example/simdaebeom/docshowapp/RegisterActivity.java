package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class  RegisterActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate =false;



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
       final EditText confirm = (EditText) findViewById(R.id.confirm);
       final EditText nameText = (EditText) findViewById(R.id.nameText);
       final EditText birthText = (EditText) findViewById(R.id.birthText);
       final Button validateButton = (Button)findViewById(R.id.validateButton);

        confirm.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if((passwordText.getText().toString()).equals(confirm.getText().toString())){


               }else{
                   confirm.setError("비밀번호가 다릅니다.");

               }
           }

           @Override
           public void afterTextChanged(Editable s) {
//               if((passwordText.getText().toString()).equals(confirm.getText().toString())){
//                   confirm.setError("비밀번호가 같습니다.");
//               }else{
//                   confirm.setError("비밀번호가 다릅니다.");
//
//               }

           }
       });


       validateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String userID = idText.getText().toString();
               if(validate){
                   return;
               }
               if(userID.equals("")){
                   AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                   dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                           .setPositiveButton("확인",null)
                           .create();
                   dialog.show();
                   Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                   positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
                   positiveButton.setBackgroundColor(Color.parseColor("#000000"));
                   return;

               }
               Response.Listener<String> responseLister = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try{

                       JSONObject jsonResponse = new JSONObject(response);
                       boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용 할 수 있는 아이디 입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
                                positiveButton.setBackgroundColor(Color.parseColor("#000000"));

                                idText.setBackgroundColor(Color.parseColor("#c0c0c0"));
                                validateButton.setBackgroundColor(Color.parseColor("#c0c0c0"));


                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용 할 수 없는 아이디 입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                                Button NegativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                NegativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                                NegativeButton.setBackgroundColor(Color.parseColor("#000000"));


                            }
                       }
                       catch (Exception e)
                       {
                          e.printStackTrace();
                       }
                   }
               };
               ValidateRequest validateRequest = new ValidateRequest(userID,responseLister);
               RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
               queue.add(validateRequest);

           }
       });

        Button registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String userID = idText.getText().toString();
               String userPassword = passwordText.getText().toString();
               String passwordconfirm = confirm.getText().toString();
               String userName = nameText.getText().toString();

                if(!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                    negativeButton.setBackgroundColor(Color.parseColor("#000000"));
                    return;

                }



               if(userPassword.equals("")||passwordconfirm.equals("")||userName.equals("")) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                   dialog = builder.setMessage("빈칸 없이 입력해주세요.")
                           .setNegativeButton("확인", null)
                           .create();
                   dialog.show();
                   Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                   negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                   negativeButton.setBackgroundColor(Color.parseColor("#000000"));
                   return;
               }
               if(!passwordconfirm.equals(userPassword)){
                   AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                   dialog = builder.setMessage("비밀번호와 비밀번호 확인은 같아야 합니다.")
                           .setNegativeButton("확인", null)
                           .create();
                   dialog.show();
                   Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                   negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                   negativeButton.setBackgroundColor(Color.parseColor("#000000"));

                   return;
               }

               SecurityUtil securityUtil = new SecurityUtil();
               userPassword = securityUtil.encryptSHA256(userPassword);
               passwordconfirm = securityUtil.encryptSHA256(passwordconfirm);
               int userBirth =Integer.parseInt(birthText.getText().toString());


               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try{
                            JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");
                           if(success) {
                               AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                               builder.setMessage("회원 등록이 성공 했습니다.").setPositiveButton("확인",null);
                               AlertDialog dialog = builder.create();
                               dialog.show();
                               Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                               positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
                               positiveButton.setBackgroundColor(Color.parseColor("#000000"));
                               finish();

                           }
                           else{
                               AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                               builder.setMessage("회원 등록이 실패 했습니다.").setNegativeButton("다시 시도",null);
                               AlertDialog dialog = builder.create();
                               dialog.show();
                               Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                               negativeButton.setTextColor(Color.parseColor("#FFFFFF"));

                           }
                       }
                       catch (JSONException e)
                       {
                           e.printStackTrace();

                       }
                   }

               };

               RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userName,userBirth,responseListener);
               RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
               queue.add(registerRequest);


           }
       });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog !=null){
            dialog.dismiss();
            dialog = null;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
