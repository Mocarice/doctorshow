/////////////////////
package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.net.Uri;
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
    private String time;
    private String year;
    private String month;
    private String day;
    private String reservationNumber;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent getintent = getIntent();
        TextView doctorNameTextView = (TextView)findViewById(R.id.doctorName);
        TextView dateTextView = (TextView)findViewById(R.id.date);

        doctorID = getintent.getExtras().getString("doctorID");
        doctorName = getintent.getExtras().getString("doctorName");
        time = getintent.getExtras().getString("time");
        year = getintent.getExtras().getString("year");
        month = getintent.getExtras().getString("month");
        day = getintent.getExtras().getString("day");
        userID =getintent.getExtras().getString("userID");
        hospitalID = getintent.getExtras().getString("hospitalID");
        date = year+"-"+month+"-"+day;

        String[] splitTime = time.split(":");
        reservationNumber = doctorID+year+month+day+splitTime[0]+splitTime[1];

        doctorNameTextView.setText(doctorName+" 선생님");
        dateTextView.setText(date+" "+time);

        Button payButton = (Button)findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                Intent payIntent = new Intent(PayActivity.this,Pay2Activity.class);
                                PayActivity.this.startActivity(payIntent);


                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                                builder.setMessage("예약이 실패했습니다.")
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

                //  Intent intent = new Intent(PayActivity.this, MainActivity.class);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
//                PayActivity.this.startActivity(intent);
                ReservationRequest reservationRequest = new ReservationRequest(reservationNumber,doctorID,userID,date,time,responseListener);
                RequestQueue queue = Volley.newRequestQueue(PayActivity.this);
                queue.add(reservationRequest);

            }
        });

    }
}

