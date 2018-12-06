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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
        Intent getIntent = getIntent();


        String disease = getIntent.getExtras().getString("disease");

        String medicalContents= getIntent.getExtras().getString("medicalContents");
        String patientID= getIntent.getExtras().getString("patientID");
        String doctorID= getIntent.getExtras().getString("doctorID");
        String date = getIntent.getExtras().getString("date");
        TextView diseaseTextView,medicalContentsTextView,patientIDTextView,doctorIDTextView,dateTextView;

        diseaseTextView = (TextView)findViewById(R.id.disease);
        medicalContentsTextView = (TextView)findViewById(R.id.medicalContents);
        patientIDTextView = (TextView)findViewById(R.id.patientID);
        doctorIDTextView = (TextView)findViewById(R.id.doctorID);
        dateTextView = (TextView)findViewById(R.id.date);

        diseaseTextView.setText(disease);
        medicalContentsTextView.setText(medicalContents);
        patientIDTextView.setText(patientID);
        doctorIDTextView.setText(doctorID);
        dateTextView.setText(date);







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
