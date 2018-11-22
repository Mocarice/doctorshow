package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Reservation3Activity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private AlertDialog dialog;
    ArrayList<String> mItems;
    private ArrayAdapter<String> adapter;
    TextView textYear;
    TextView textMon;
    private String doctorID;
    private String doctorName;
    private String hospitalID;
    private String userID;
    private String dayOfWork;
    private GridView grid;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reser3);

        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent getintent = getIntent();
        hospitalID = getintent.getExtras().getString("hospitalID");
        doctorID = getintent.getExtras().getString("doctorID");
        doctorName = getintent.getExtras().getString("doctorName");
        userID = getintent.getExtras().getString("userID");
        dayOfWork = getintent.getExtras().getString("dayOfWork");


        TextView workDay = (TextView) findViewById(R.id.workDay);
        if (dayOfWork.equals("1")) {
            workDay.setText("(월,수,금)");
        } else if (dayOfWork.equals("2")) {
            workDay.setText("(화,목,토)");
        } else {
            workDay.setText("근무하지않음.");
        }
        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);

        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);
                int currentDate = Integer.parseInt(new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
                String currentMonth = new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());


                String year = textYear.getText().toString();
                String month = textMon.getText().toString();
                String day = mItems.get(position);
                if (mItems.get(position).equals("") || mItems.get(position).equals("월") || mItems.get(position).equals("화") || mItems.get(position).equals("수") || mItems.get(position).equals("목") || mItems.get(position).equals("금") || mItems.get(position).equals("토") || mItems.get(position).equals("일")) {
                    ;
                } else {

                    int ItemDate = Integer.parseInt(year + month + day);
                    int currentMon = Integer.parseInt(currentMonth) * 100;
                    //해당 달에서 해당 날짜 이후에만 예약가능
                    if (currentMonth.equals(year + month) && currentMon + Integer.parseInt(mItems.get(position)) > currentDate) {
                        if (dayOfWork.equals("1")) {
                            if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        } else if (dayOfWork.equals("2")) {
                            if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        }
                    }
                    //12월일 경우에는 다음해 1월까지 예약가능
                    else if (Integer.parseInt(year) > Integer.parseInt(currentMonth) / 100 && Integer.parseInt(month) == Integer.parseInt(currentMonth) % 100 - 11 && currentDate % 100 >= Integer.parseInt(day)) {
                        if (dayOfWork.equals("1")) {
                            if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        } else if (dayOfWork.equals("2")) {
                            if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        }
                    }
                    //현재 날짜에서 한달 이후의 날짜까지만 예약가능
                    else if (Integer.parseInt(year) == Integer.parseInt(currentMonth) / 100 && Integer.parseInt(month) == Integer.parseInt(currentMonth) % 100 + 1 && currentDate % 100 >= Integer.parseInt(day)) {
                        if (dayOfWork.equals("1")) {
                            if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        } else if (dayOfWork.equals("2")) {
                            if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                                view.setBackgroundColor(Color.parseColor("#54d482"));
                            }
                        }
                    }


                }

                return view;
            }

        };

        grid = (GridView) this.findViewById(R.id.grid1);
        grid.setAdapter(adapter);


        grid.setOnItemClickListener(this);


        Date date = new Date();// 오늘에 날짜를 세팅 해준다.
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;
        textYear.setText(year + "");
        textMon.setText(mon + "");


        fillDate(year, mon);

        Button btnmove = (Button) this.findViewById(R.id.bt1);
        btnmove.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void fillDate(int year, int mon) {
        mItems.clear();

        mItems.add("일");
        mItems.add("월");
        mItems.add("화");
        mItems.add("수");
        mItems.add("목");
        mItems.add("금");
        mItems.add("토");


        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.

        for (int i = 0; i < day; i++) {
            mItems.add("");
        }

        current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
        int last = 32 - current.getDate();

        for (int i = 1; i <= last; i++) {
            mItems.add(i + "");
        }


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bt1) {
            int year = Integer.parseInt(textYear.getText().toString());
            int mon = Integer.parseInt(textMon.getText().toString());
            fillDate(year, mon);
            grid.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mItems.get(position).equals("") || mItems.get(position).equals("월") || mItems.get(position).equals("화") || mItems.get(position).equals("수") || mItems.get(position).equals("목") || mItems.get(position).equals("금") || mItems.get(position).equals("토") || mItems.get(position).equals("일")) {
            ;
        } else {
            int currentDate = Integer.parseInt(new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
            String year = textYear.getText().toString();
            String month = textMon.getText().toString();
            String day = mItems.get(position);
            String currentMonth = new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());

            int currentMon = Integer.parseInt(currentMonth) * 100;
            int clickDate =
                    Integer.parseInt(year + month + day);
            if (currentMonth.equals(year + month) && currentMon + Integer.parseInt(mItems.get(position)) > currentDate) {

                if (dayOfWork.equals("1")) {

                    if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                        setReservation(position, year, month, day);

                    } else {
                        dialogNotDoctorDay();
                    }
                } else if (dayOfWork.equals("2")) {

                    if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                        setReservation(position, year, month, day);
                    } else {
                        dialogNotDoctorDay();
                    }

                } else {
                    dialogCannotReservation();
                }

            } else if (Integer.parseInt(year) > Integer.parseInt(currentMonth) / 100 && Integer.parseInt(month) == Integer.parseInt(currentMonth) % 100 - 11 && currentDate % 100 >= Integer.parseInt(day)) {
                if (dayOfWork.equals("1")) {

                    if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                        setReservation(position, year, month, day);

                    } else {
                        dialogNotDoctorDay();
                    }
                } else if (dayOfWork.equals("2")) {

                    if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                        setReservation(position, year, month, day);

                    } else {
                        dialogNotDoctorDay();
                    }

                } else {
                    dialogCannotReservation();
                }
            } else if (Integer.parseInt(year) == Integer.parseInt(currentMonth) / 100 && Integer.parseInt(month) == Integer.parseInt(currentMonth) % 100 + 1 && currentDate % 100 >= Integer.parseInt(day)) {
                if (dayOfWork.equals("1")) {

                    if (position % 7 == 1 || position % 7 == 3 || position % 7 == 5) {
                        setReservation(position, year, month, day);

                    } else {
                        dialogNotDoctorDay();
                    }
                } else if (dayOfWork.equals("2")) {

                    if (position % 7 == 2 || position % 7 == 4 || position % 7 == 6) {
                        setReservation(position, year, month, day);
                    } else {
                        dialogNotDoctorDay();
                    }

                } else {
                    dialogCannotReservation();
                }
            } else {
                dialogCannotReservation();

            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogCannotReservation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Reservation3Activity.this);
        dialog = builder.setMessage("해당 날짜는 예약할 수 없습니다.")
                .setNegativeButton("확인", null)
                .create();
        dialog.show();
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));
    }

    private void dialogNotDoctorDay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Reservation3Activity.this);
        dialog = builder.setMessage("해당 요일은 선생님의 근무요일이 아닙니다.")
                .setNegativeButton("확인", null)
                .create();
        dialog.show();
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));
    }

    private void setReservation(int position, String year, String month, String day) {
        intent = new Intent(this, Reservation4Activity.class);//해당 일을 눌렸을때
        intent.putExtra("date", textYear.getText().toString() + "-"
                + textMon.getText().toString() + "-" + mItems.get(position));
        intent.putExtra("doctorID", doctorID);
        intent.putExtra("doctorName", doctorName);
        intent.putExtra("hospitalID", hospitalID);
        intent.putExtra("userID", userID);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);
        new BackgroundTask().execute();



    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
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
            intent.putExtra("reservationList", result);
            startActivity(intent);


        }


    }

}
