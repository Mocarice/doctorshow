package com.example.simdaebeom.docshowapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MedicalInfoActivity extends AppCompatActivity {


    private ArrayAdapter adapter;
    private Spinner spinner;
    private ArrayAdapter adapter1;
    private Spinner spinner1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalinfo);
        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //spinner 구현
        spinner = (Spinner)findViewById(R.id.yearSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1 = (Spinner)findViewById(R.id.monthSpinner);
        adapter1 = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        //Listview 구현
        ListView listview = (ListView)findViewById(R.id.medicalInfomationView);

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



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
