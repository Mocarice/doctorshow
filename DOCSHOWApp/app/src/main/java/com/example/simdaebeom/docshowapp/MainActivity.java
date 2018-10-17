package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = getIntent();
       userID =intent.getExtras().getString("userID");
//        TextView idText = (TextView) findViewById(R.id.idText);
//        String userID = intent.getStringExtra("userID");

        Button reservationButton = (Button)findViewById(R.id.reservationButton);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

//        if (!userID.equals("admin")) {
//            managementButton.setVisibility(View.GONE);//button 비활성화
//        }
//        managementButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new BackgroundTask().execute();
//
//            }
//        });

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
    public void goInfor(View view) {
//        Intent MedicalInfoIntent = new Intent(MainActivity.this,MedicalInfoActivity.class);
//        MainActivity.this.startActivity(MedicalInfoIntent);
    }


}



