package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MedicalInfo2Activity extends AppCompatActivity  {


    private ArrayAdapter adapter;
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;
    private List<String> list = new ArrayList<>();
    private ListView listview;
    private String date;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info2);
        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        saveList = new ArrayList<MedicalRecord>();
        medicalRecords = new ArrayList<MedicalRecord>();
        //Listview 구현
        Intent getintent = getIntent();
        date = getintent.getExtras().getString("date");
        temp =getintent.getExtras().getString("temp");
        listview = (ListView)findViewById(R.id.medicalInfomationView);



//        try {
//            JSONObject jsonObject = new JSONObject(intent.getStringExtra("hospitalList"));
//            JSONArray jsonArray = jsonObject.getJSONArray("response");
//            int count = 0;
//            String hospitalID, hospitalName, Telephone, hospitalAddress;
//            while (count < jsonArray.length()) {
//                JSONObject object = jsonArray.getJSONObject(count);
//                hospitalName = object.getString("hospitalName");
//                hospitalAddress = object.getString("hospitalAddress");
//                Telephone = object.getString("Telephone");
//                hospitalID = object.getString("hospitalID");
//
//                Hospital hospital = new Hospital(hospitalID, hospitalName, hospitalAddress, Telephone);
//
//                hospitalList.add(hospital);
//                saveList.add(hospital);
//                count++;
//            }
//
//            adapter = new HospitalListAdapter(getApplicationContext(), hospitalList, saveList);
//            listView.setAdapter(adapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    setHospitalID(hospitalList.get(position).hospitalID);
//                    new ReservationActivity.BackgroundTask().execute();
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        list.add(getintent.getStringExtra("result"));
        list.add("recordID : "+"11");
        list.add("medicalHistory : "+"nojZGvpbiAbiuGJlE1O5");
        list.add("disease : "+"73vzXtpOuayG2pGoRbX7");
        list.add("currentmedication : "+"DP9eBoWdAB7V4gj1H9Fx");
        list.add("흡연여부 : "+"true");
        list.add("환자ID : "+"admin");
        list.add(temp);
        list.add("진료날짜 : "+date);



        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);



        //리스트뷰의 어댑터를 지정해준다.
        listview.setAdapter(adapter);







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}
