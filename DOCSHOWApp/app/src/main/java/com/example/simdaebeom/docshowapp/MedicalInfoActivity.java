package com.example.simdaebeom.docshowapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MedicalInfoActivity extends AppCompatActivity {


    private ArrayAdapter adapter;
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalinfo);
        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        saveList = new ArrayList<MedicalRecord>();
        medicalRecords = new ArrayList<MedicalRecord>();
        //Listview 구현
        ListView listview = (ListView)findViewById(R.id.medicalInfomationView);


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


        List<String> list = new ArrayList<>();

        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        listview.setAdapter(adapter);

        list.add("2015.06.11");
        list.add("2015.07.12");
        list.add("2017.01.01");
        list.add("2017.02.03");


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
            if (saveList.get(i).getDateOfExamination().contains(search))
                medicalRecords.add(saveList.get(i));
        }
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
