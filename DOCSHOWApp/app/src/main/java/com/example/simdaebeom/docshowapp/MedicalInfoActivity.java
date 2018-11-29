package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MedicalInfoActivity extends AppCompatActivity  {



    private MedicalRecordAdapter adapter;
    private List<String> listDate = new ArrayList<>();
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;
    private ListView listview;
    private String userID;
    private String userPassword;
    String AES = "AES";
    public static String key ="qwer";
    String value="U2FsdGVkX18sUt+d1Ao9s+kz7gOH+3dOXJaiIB7ic8A=";

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
        userPassword = getIntent.getExtras().getString("userPassword");


        try {
            String outputString = decrypt("PnlYXvMCuR2EqZvslukOow==","9394");
            adapterDate.add(outputString);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "adhadaj", Toast.LENGTH_LONG).show();

        }


        //서버 매번 수정되니까  MainActivity에서 변경하면 됨.
        try {
            JSONObject jsonObject = new JSONObject(getIntent.getStringExtra("medicalList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String medicalRecordID, disease, medicalContents, patientID,doctorID,date;
            while (count < jsonArray.length()) {

                JSONObject object = jsonArray.getJSONObject(count);

                medicalRecordID = object.getString("medicalRecordID");
                disease = object.getString("disease");
                medicalContents = object.getString("medicalContents");
                patientID = object.getString("patientID");
                doctorID = object.getString("doctorID");
                date = object.getString("date");


                // 수정
                MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, disease, medicalContents, patientID,doctorID,date);
                medicalRecords.add(medicalRecord);
                saveList.add(medicalRecord);

                count++;
                adapterDate.add(date);

            }
                listview.setAdapter(adapterDate);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0;i<medicalRecords.size();i++)
                    {
                        if(medicalRecords.get(i).getDate().equals(listDate.get(position))){
                            Intent intent = new Intent(MedicalInfoActivity.this, MedicalInfo2Activity.class);
                            intent.putExtra("disease",medicalRecords.get(i).getDisease());
                            intent.putExtra("medicalContents",medicalRecords.get(i).getMedicalContents());
                            intent.putExtra("patientID",medicalRecords.get(i).getPatientID());
                            intent.putExtra("doctorID",medicalRecords.get(i).getDoctorID());
                            intent.putExtra("date",medicalRecords.get(i).getDate());

                            MedicalInfoActivity.this.startActivity(intent);

                        }
                    }

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

    private String encrypt(String Data,String password)throws Exception{
     SecretKeySpec key = generateKey(password);
     Cipher c = Cipher.getInstance(AES);
     c.init(Cipher.ENCRYPT_MODE,key);
     byte[] encVal = c.doFinal(Data.getBytes());
     String encrytedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
     return encrytedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
         return secretKeySpec;

    }
    private String decrypt(String outputString ,String password)throws  Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue =c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;


    }


}
