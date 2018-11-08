/////////////////////
package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent payIntent = new Intent(PayActivity.this,Pay2Activity.class);
                payIntent.putExtra("userID", userID);
                payIntent.putExtra("doctorID", doctorID);
                payIntent.putExtra("reservationNumber",reservationNumber);
                payIntent.putExtra("time",time);
                payIntent.putExtra("date",date);
                PayActivity.this.startActivity(payIntent);

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

