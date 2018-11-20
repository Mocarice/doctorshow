package com.example.simdaebeom.docshowapp;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyReservationActivity extends AppCompatActivity {

    private ListView listView;
    private List<Reservation> reservationList;
    private List<Reservation> saveList;
    private ReservationListAdapter adapter;
    private String userID;
    private Intent intent;
    private ArrayList doctorIDs = new ArrayList<String>();
    private ArrayList doctorNames = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();

        setContentView(R.layout.activity_my_reservation);
        //toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID =intent.getExtras().getString("parentID");
        listView = (ListView) findViewById(R.id.reservationList);
        reservationList = new ArrayList<Reservation>();
        saveList = new ArrayList<Reservation>();

        doctorIDs = intent.getStringArrayListExtra("doctorIDs");
        doctorNames = intent.getStringArrayListExtra("doctorNames");

        int currentDate = Integer.parseInt( new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
        int currentDay = currentDate%100;
        int currentMonth = (currentDate%10000)/100;
        int currentYear = currentDate/10000;




        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("reservationList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String reservationNumber,doctorID,userID, date,time;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                String parentID = intent.getExtras().getString("parentID");
                reservationNumber = object.getString("reservationNumber");
                doctorID = object.getString("doctorID");
                userID = object.getString("userID");
                date = object.getString("date");
                time = object.getString("time");

                for(int i=0;i<doctorIDs.size();i++){
                    if(doctorID.equals(doctorIDs.get(i).toString())){
                        doctorID = doctorNames.get(i).toString();
                    }
                }

                Reservation reservation = new Reservation(reservationNumber, "의사이름:  "+doctorID, "아이디:  "+userID,"날짜:  "+ date,"시간:   "+time);
                String datetoString = date.replace("-","");

                if(parentID.equals(userID)&&currentDate<Integer.parseInt(datetoString)){
                    reservationList.add(reservation);
                    saveList.add(reservation);
                     }
                count++;
            }
            adapter = new ReservationListAdapter(getApplicationContext(), reservationList, saveList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }



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
