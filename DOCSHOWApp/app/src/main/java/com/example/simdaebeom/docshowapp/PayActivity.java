package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class PayActivity extends AppCompatActivity {

    private String userID;

    private String doctorID;
    private String doctorName;
    private String hospitalID;
    private String date;
    private String reservationNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent getintent = getIntent();
        TextView doctorNameTextView = (TextView)findViewById(R.id.doctorName);
        TextView dateTextView = (TextView)findViewById(R.id.date);

        doctorID = getintent.getExtras().getString("doctorID");
        doctorName = getintent.getExtras().getString("doctorName");
        date = getintent.getExtras().getString("date");
        userID =getintent.getExtras().getString("userID");
        reservationNumber = doctorID+date;
        hospitalID = getintent.getExtras().getString("hospitalID");


        doctorNameTextView.setText(getintent.getExtras().getString("doctorName")+" 선생님");
        dateTextView.setText(getintent.getExtras().getString("date"));

        Button payButton = (Button)findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                     Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                                builder.setMessage("예약이 완료되었습니다.")
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                                builder.setMessage("예약이 실패했습니다..")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();

                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                };
                    ReservationRequest reservationRequest = new ReservationRequest(reservationNumber,doctorID,userID,date,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PayActivity.this);
                      queue.add(reservationRequest);
            }
        });

    }
}
