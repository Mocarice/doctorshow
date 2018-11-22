package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation4Activity extends AppCompatActivity {

    private String doctorID;
    private String doctorName;
    private String hospitalID;
    private String date;
    private String userID;
    private String year;
    private String month;
    private String day;
    private List<Reservation> reservationList;
    private AlertDialog dialog;




    private  Intent getintent;
    private final ArrayList<String> timeList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser4);
        getintent = getIntent();

        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reservationList = new ArrayList<Reservation>();


        doctorID = getintent.getExtras().getString("doctorID");
        doctorName = getintent.getExtras().getString("doctorName");
        hospitalID = getintent.getExtras().getString("hospitalID");
        date = getintent.getExtras().getString("date");
        userID = getintent.getExtras().getString("userID");
        year = getintent.getExtras().getString("year");
        month = getintent.getExtras().getString("month");
        day = getintent.getExtras().getString("day");
        if(Integer.parseInt(getintent.getExtras().getString("day"))<10) {
            day = "0"+ getintent.getExtras().getString("day");
        }
        if(Integer.parseInt(getintent.getExtras().getString("month"))<10) {
            month = "0"+ getintent.getExtras().getString("month");
        }

        final ListView listView = (ListView)findViewById(R.id.timeList);

        timeset(timeList);

        ListAdapter adapter = new TimeAdapter(this,timeList){

            @Override
            public View getView(int position,  View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
               String reservationNum = doctorID+year+month+day+timeList.get(position).replace(":","");

                try {
                    JSONObject jsonObject = new JSONObject(getintent.getStringExtra("reservationList"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String reservationNumber,doctorID,userID, date,time;
                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        reservationNumber = object.getString("reservationNumber");
                        doctorID = object.getString("doctorID");
                        userID = object.getString("userID");
                        date = object.getString("date");
                        time = object.getString("time");


                        if(reservationNum.equals(reservationNumber)){
                            view.setBackgroundColor(Color.parseColor("#808080"));
                            reservationList.add(new Reservation(reservationNumber,doctorID,userID,date,time));
                        }



                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                return view;
            }
        };
        listView.setAdapter(adapter);
        TextView doctorNameTextview = (TextView)findViewById(R.id.doctorName);
        TextView dateTextview = (TextView)findViewById(R.id.date);

        doctorNameTextview.setText(getintent.getExtras().getString("doctorName")+" 선생님");
        dateTextview.setText(year+"-"+month+"-"+day);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isExist=true;
                String reservationNum = doctorID+year+month+day+timeList.get(position).replace(":","");
                    for(int indexReservation=0;indexReservation<reservationList.size();indexReservation++){
                        if(reservationList.get(indexReservation).getReservationNumber().equals(reservationNum)){
                            isExist=false;
                        }

                    }
                    if(isExist){
                        Intent intent = new Intent(Reservation4Activity.this, PayActivity.class);
                        intent.putExtra("doctorID", doctorID);
                        intent.putExtra("doctorName",doctorName);
                        intent.putExtra("hospitalID",hospitalID);
                        intent.putExtra("time",timeList.get(position));
                        intent.putExtra("year",year);
                        intent.putExtra("month",month);
                        intent.putExtra("day",day);
                        intent.putExtra("userID", userID);

                        Reservation4Activity.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Reservation4Activity.this);
                        dialog = builder.setMessage("해당 시간은 이미 예약되었습니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                        negativeButton.setBackgroundColor(Color.parseColor("#000000"));
                    }
            }
        });



    }

    public void timeset(ArrayList<String> timeList){
        timeList.add("09:00");
        timeList.add("10:00");
        timeList.add("11:00");
        timeList.add("13:00");
        timeList.add("14:00");
        timeList.add("15:00");
;

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
