package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Reservation2Activity extends AppCompatActivity {

    private ListView listView;
    private List<Doctor> doctorList;
    private List<Doctor> saveList;
    private DoctorAdapter adapter;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser2);


        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        userID =intent.getExtras().getString("userID");
        listView = (ListView) findViewById(R.id.doctorList);

        saveList = new ArrayList<Doctor>();
        doctorList = new ArrayList<Doctor>();


        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("hospitalList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String doctorID,doctorName,doctorHospitalID,department,telephone;
            while(count<jsonArray.length())
            {
                String hospitalID = intent.getExtras().getString("hospitalID");
                JSONObject object = jsonArray.getJSONObject(count);
                doctorID =  object.getString("doctorID");
                doctorName = object.getString("doctorName");
                doctorHospitalID = object.getString("hospitalID");
                department = object.getString("department");
                telephone = object.getString("telephone");
                Doctor doctor = new Doctor(doctorID,doctorName,doctorHospitalID,department,telephone);
                if(hospitalID.equals(doctorHospitalID)){
                    doctorList.add(doctor);
                    saveList.add(doctor);
                }
                count++;

            }

            adapter= new DoctorAdapter(this,doctorList,saveList);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Reservation2Activity.this, Reservation3Activity.class);
                    intent.putExtra("doctorID", doctorList.get(position).doctorID);
                    intent.putExtra("doctorName",doctorList.get(position).doctorName);
                    intent.putExtra("hospitalID",doctorList.get(position).hospitalID);
                    intent.putExtra("userID",userID);


                    Reservation2Activity.this.startActivity(intent);
                }
            });


        }catch (Exception e) {
            e.printStackTrace();
        }
        EditText DoctorSearch = (EditText) findViewById(R.id.doctorNameSearch);
        DoctorSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDoctor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void goCalendar(View view) {
        Intent Reservation3Intent = new Intent(Reservation2Activity.this,Reservation3Activity.class);
//        intent.putExtra("hospitalList", result);
        Reservation3Intent.putExtra("userID", userID);

        Reservation2Activity.this.startActivity(Reservation3Intent);
    }

    public void searchDoctor(String search) {
        doctorList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getDoctorName().contains(search))
                doctorList.add(saveList.get(i));
        }
        adapter.notifyDataSetChanged();

    }



}
