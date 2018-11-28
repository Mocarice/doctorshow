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
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MedicalInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private MedicalRecordAdapter adapter;
    private List<String> listDate = new ArrayList<>();
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;
    private ListView listview;

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalinfo);
        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listview = (ListView)findViewById(R.id.medicalInfomationView);


        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listDate);

        medicalRecords = new ArrayList<MedicalRecord>();
        saveList = new ArrayList<MedicalRecord>();


        Intent getIntent = getIntent();

        userID = getIntent.getExtras().getString("userID");


        //medicalList에서 userID와 transaction patientID 대조해서 같은 것만 medicalRecords에 추가.
        //Transaction 구성 바뀌면     JSONObject object = jsonArray.getJSONObject(count); 밑에 부분 수정
        //date만 listview에 띄우고, date(listview) click시 해당 transation은  MediInfo2에서 띄운다.
        //서버 매번 수정되니까  MainActivity에서 변경하면 됨.
        try {
            JSONObject jsonObject = new JSONObject(getIntent.getStringExtra("medicalList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String medicalRecordID, disease, medicalContents, patientID,doctorID,date;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                medicalRecordID = object.getString("$class");
                disease = object.getString("recordId");
                medicalContents = object.getString("medicalHistory");
                patientID = object.getString("currentMedication");
                doctorID = object.getString("owner");
                date = "2016-06-24";

                // 수정
                MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, disease, medicalContents, patientID,doctorID,date);

                medicalRecords.add(medicalRecord);
                saveList.add(medicalRecord);
                count++;
                adapterDate.add(date);

            }
//            adapter = new MedicalRecordAdapter(getApplicationContext(), medicalRecords, saveList);

            listview.setAdapter(adapterDate);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    for(int i=0;i<medicalRecords.size();i++)
//                    {
//                        if(medicalRecords.get(i).getDate().equals(listDate.get(position))){
//                            Intent intent = new Intent(MedicalInfoActivity.this, MedicalInfo2Activity.class);
//                            intent.putExtra(medicalRecords.get(i).getMedicalRecordID(),"medicalRecordID");
//                            intent.putExtra(medicalRecords.get(i).getDisease(),"disease");
//                            intent.putExtra(medicalRecords.get(i).getMedicalContents(),"medicalContents");
//                            intent.putExtra(medicalRecords.get(i).getPatientID(),"patientID");
//                            intent.putExtra(medicalRecords.get(i).getDoctorID(),"doctorID");
//                            intent.putExtra(medicalRecords.get(i).getDate(),"date");
//
//                            MedicalInfoActivity.this.startActivity(intent);
//
//                        }
//                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }















        EditText searchDay = (EditText) findViewById(R.id.searchDay);
        searchDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                searchInfo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    public void searchInfo(String search) {
        medicalRecords.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getDate().contains(search))
                medicalRecords.add(saveList.get(i));
        }
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//         intent = new Intent(MedicalInfoActivity.this, MedicalInfo2Activity.class);
//            intent.putExtra("day",list.get(position) );
////            intent.putExtra("userID", userID);
//        intent.putExtra("temp",temp);
//
//        MedicalInfoActivity.this.startActivity(intent);

    }


}
