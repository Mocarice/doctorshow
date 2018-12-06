package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private List<Doctor> doctorList;

    private  String doctors;
    private ArrayList doctorIDs = new ArrayList<String>();
    private ArrayList doctorNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doctorList = new ArrayList<Doctor>();
        Intent intent = getIntent();
        userID =intent.getExtras().getString("userID");
        userPassword = intent.getExtras().getString("userPassword");



        new BackgroundTask3().execute();



        final Button reservationButton = (Button)findViewById(R.id.reservationButton);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        Button qrButton = (Button)findViewById(R.id.qrButton);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qrIntent = new Intent(MainActivity.this,QRActivity.class);
                qrIntent.putExtra("userPassword", userPassword);
                MainActivity.this.startActivity(qrIntent);
            }
        });

        Button showReservation = (Button)findViewById(R.id.showReservation);
        showReservation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new BackgroundTask1().execute();
            }
        });
        Button medicalInfo = (Button)findViewById(R.id.informationButton);
        medicalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask2().execute();


            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://debum93.cafe24.com/Hospital.php";

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
            Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
            intent.putExtra("hospitalList", result);
            intent.putExtra("userID", userID);
            MainActivity.this.startActivity(intent);
        }


    }
    class BackgroundTask1 extends AsyncTask<Void, Void, String> {
        String target;




        @Override
        protected void onPreExecute() {
            target = "http://debum93.cafe24.com/getReservation.php";
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
            Intent intent = new Intent(MainActivity.this, MyReservationActivity.class);
            intent.putStringArrayListExtra("doctorIDs",doctorIDs);
            intent.putStringArrayListExtra("doctorNames",doctorNames);
            intent.putExtra("reservationList", result);
            intent.putExtra("parentID", userID);
            MainActivity.this.startActivity(intent);
        }


    }

    class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //blockchain 서버..
            target = "http://62862835.ngrok.io/api/queries/getMedicalRecordOfPatient?patientID="+userID;


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
            Intent intent = new Intent(MainActivity.this, MedicalInfoActivity.class);
            result = "{"+"\"response\""+":"+result+"}";
            intent.putExtra("medicalList", result);
            intent.putExtra("userID", userID);
            intent.putExtra("userPassword",userPassword);
            intent.putStringArrayListExtra("doctorIDs",doctorIDs);
            intent.putStringArrayListExtra("doctorNames",doctorNames);
            MainActivity.this.startActivity(intent);






        }


    }

    class BackgroundTask3 extends AsyncTask<Void, Void, String> {
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

            doctors = result;

            try {
                JSONObject jsonObject = new JSONObject(doctors);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String doctorID,doctorName,hospitalID,department,telephone,dayOfWork;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    doctorID = object.getString("doctorID");
                    doctorName = object.getString("doctorName");
                    hospitalID = object.getString("hospitalID");
                    department = object.getString("department");
                    telephone = object.getString("telephone");
                    dayOfWork = object.getString("dayOfWork");


                    Doctor doctor = new Doctor(doctorID,doctorName,hospitalID,department,telephone,dayOfWork);

                    doctorList.add(doctor);

                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            for(int i=0;i<doctorList.size();i++){
                doctorIDs.add(doctorList.get(i).doctorID);
                doctorNames.add(doctorList.get(i).doctorName);

            }
        }


    }








}