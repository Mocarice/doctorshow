package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class ReservationActivity extends AppCompatActivity {
    private ListView listView;
    private List<Hospital> hospitalList;
    private List<Hospital> saveList;
    private HospitalListAdapter adapter;
    private String hospitalID;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser);
        Intent intent = getIntent();
        //toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID =intent.getExtras().getString("userID");

        listView = (ListView) findViewById(R.id.hospitalList);
        hospitalList = new ArrayList<Hospital>();
        saveList = new ArrayList<Hospital>();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("hospitalList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String hospitalID, hospitalName, Telephone, hospitalAddress;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                hospitalName = object.getString("hospitalName");
                hospitalAddress = object.getString("hospitalAddress");
                Telephone = object.getString("Telephone");
                hospitalID = object.getString("hospitalID");

                Hospital hospital = new Hospital(hospitalID, hospitalName, hospitalAddress, Telephone);

                hospitalList.add(hospital);
                saveList.add(hospital);
                count++;
            }

            adapter = new HospitalListAdapter(getApplicationContext(), hospitalList, saveList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // String hospitalName = String.valueOf(parent.getItemAtPosition(position))
                    setHospitalID(hospitalList.get(position).hospitalID);
                    new ReservationActivity.BackgroundTask().execute();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText Hospitalsearch = (EditText) findViewById(R.id.hospitalSearch);
        Hospitalsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchHospital(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    //의사 소환
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://debum93.cafe24.com/Doctor.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(ReservationActivity.this, Reservation2Activity.class);

            intent.putExtra("hospitalList", result);
            intent.putExtra("hospitalID", getHospitalID());
            intent.putExtra("userID", userID);

            ReservationActivity.this.startActivity(intent);

        }

    }

    public void searchHospital(String search) {
        hospitalList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getHospitalName().contains(search))
                hospitalList.add(saveList.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

}