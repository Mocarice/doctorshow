package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Base64;

import android.widget.Toast;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


public class MedicalInfoActivity extends AppCompatActivity  {



    private MedicalRecordAdapter adapter;
    private List<String> listDate = new ArrayList<>();
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;
    private ListView listview;
    private String userID;
    private String userPassword;
    private ArrayList doctorIDs = new ArrayList<String>();
    private ArrayList doctorNames = new ArrayList<String>();



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
        doctorIDs = getIntent.getStringArrayListExtra("doctorIDs");
        doctorNames = getIntent.getStringArrayListExtra("doctorNames");
        String key = userID;


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

                //추가 ...
                medicalRecordID = decrypt(medicalRecordID,key);
                disease = decrypt(disease,key);
                medicalContents=  decrypt(medicalContents,key);
                doctorID = decrypt(doctorID,key);
                date =  decrypt(date,key);
                for(int i=0;i<doctorIDs.size();i++){
                    if(doctorID.equals(doctorIDs.get(i).toString())){
                        doctorID = doctorNames.get(i).toString(); }
                    }

                MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, disease, medicalContents, patientID,doctorID,date);
                medicalRecords.add(medicalRecord);
                saveList.add(medicalRecord);
                adapterDate.add(date.substring(0,4)+"년"+date.substring(4,6)+"월"+date.substring(6,8)+"일");

                ////////////


                count++;

            }
            listview.setAdapter(adapterDate);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for(int i=0;i<medicalRecords.size();i++)
                    {
                        if(medicalRecords.get(i).getDate().equals(listDate.get(position).replace("년","").replace("월","").replace("일",""))){
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







    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static String decrypt(String ciphertext, String passphrase) {
        try {
            final int keySize = 256;
            final int ivSize = 128;

            // 텍스트를 BASE64 형식으로 디코드 한다.
            byte[] ctBytes = org.apache.commons.codec.binary.Base64.decodeBase64(ciphertext.getBytes("UTF-8"));

            // 솔트를 구한다. (생략된 8비트는 Salted__ 시작되는 문자열이다.)
            byte[] saltBytes = Arrays.copyOfRange(ctBytes, 8, 16);
//        System.out.println( Hex.encodeHexString(saltBytes) );

            // 암호화된 테스트를 구한다.( 솔트값 이후가 암호화된 텍스트 값이다.)
            byte[] ciphertextBytes = Arrays.copyOfRange(ctBytes, 16, ctBytes.length);

            // 비밀번호와 솔트에서 키와 IV값을 가져온다.
            byte[] key = new byte[keySize / 8];
            byte[] iv = new byte[ivSize / 8];
            EvpKDF(passphrase.getBytes("UTF-8"), keySize, ivSize, saltBytes, key, iv);

            // 복호화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] recoveredPlaintextBytes = cipher.doFinal(ciphertextBytes);

            return new String(recoveredPlaintextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        return EvpKDF(password, keySize, ivSize, salt, 1, "MD5", resultKey, resultIv);
    }

    private static byte[] EvpKDF(byte[] password, int keySize, int ivSize, byte[] salt, int iterations, String hashAlgorithm, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        keySize = keySize / 32;
        ivSize = ivSize / 32;
        int targetKeySize = keySize + ivSize;
        byte[] derivedBytes = new byte[targetKeySize * 4];
        int numberOfDerivedWords = 0;
        byte[] block = null;
        MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
        while (numberOfDerivedWords < targetKeySize) {
            if (block != null) {
                hasher.update(block);
            }
            hasher.update(password);
            // Salting
            block = hasher.digest(salt);
            hasher.reset();
            // Iterations : 키 스트레칭(key stretching)
            for (int i = 1; i < iterations; i++) {
                block = hasher.digest(block);
                hasher.reset();
            }
            System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4, Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));
            numberOfDerivedWords += block.length / 4;
        }
        System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
        System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);
        return derivedBytes; // key + iv
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