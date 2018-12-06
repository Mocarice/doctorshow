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
import android.widget.ListView;
//import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

//import org.apache.commons.codec.binary.Base64;
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



        //복호화 시도..................
        //복호화 decrypt제대로 되면 listview에 추가되고  제대로 안되면 toast발생합니다.
//        try {
//            String outputString = decrypt("PnlYXvMCuR2EqZvslukOow=="/*복호화할스트링*/,"9394");
//            adapterDate.add(outputString);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "adhadaj", Toast.LENGTH_LONG).show();
//
//        }


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
//
//    private String encrypt(String Data,String password)throws Exception{
//     SecretKeySpec key = generateKey(password);
//     Cipher c = Cipher.getInstance(AES);
//     c.init(Cipher.ENCRYPT_MODE,key);
//     byte[] encVal = c.doFinal(Data.getBytes());
//     String encrytedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
//     return encrytedValue;
//    }
//
//    private SecretKeySpec generateKey(String password) throws Exception {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = password.getBytes("UTF-8");
//        digest.update(bytes,0,bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
//         return secretKeySpec;
//
//    }
//    private String decrypt(String outputString ,String password)throws  Exception{
//        SecretKeySpec key = generateKey(password);
//        Cipher c = Cipher.getInstance(AES);
//        c.init(Cipher.DECRYPT_MODE,key);
//        byte[] decodedValue = Base64.decode(outputString,Base64.DEFAULT);
//        byte[] decValue =c.doFinal(decodedValue);
//        String decryptedValue = new String(decValue);
//        return decryptedValue;
//
//
//    }
    void decryption() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String secret = "René Über";
        String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

        byte[] cipherData = Base64.decode(cipherText,Base64.DEFAULT);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

        byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedData = aesCBC.doFinal(encrypted);
        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

        System.out.println(decryptedText);
    }

    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0)
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

            return result;

        } catch (DigestException e) {
            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte)0);
        }
    }

}
