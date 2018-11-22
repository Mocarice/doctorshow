package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRActivity extends AppCompatActivity {

    private String TAG = "GenerateQRCode";
    private ImageView qrImage;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String qrCode;
   private String currentTime;
   private TimerTask second;
   private  long now;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);



        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showQR();
//        //현재시간
//        long now = System.currentTimeMillis();
//        Date date = new Date(now);
//        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmm");
//        currentTime = sdfNow.format(date);
//
//        SecurityUtil securityUtil = new SecurityUtil();
//        currentTime= securityUtil.encryptSHA256(currentTime);
//
//        Intent intent = getIntent();
//        //qrCode는 현재yyyymmddhhmm+userPassword의 해쉬화된 값.
//        qrCode =currentTime+intent.getExtras().getString("userPassword");
//        qrImage = (ImageView) findViewById(R.id.QR_Image);
//
//        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//        int width = point.x;
//        int height = point.y;
//        int smallerDimension = width < height ? width : height;
//        ////QR 코드화
//        qrgEncoder = new QRGEncoder(
//                qrCode, null,
//                QRGContents.Type.TEXT,
//                smallerDimension);
//        try {
//            bitmap = qrgEncoder.encodeAsBitmap();
//            qrImage.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            Log.v(TAG, e.toString());
//        }


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
    public void showQR() {
        qrImage = (ImageView) findViewById(R.id.QR_Image);


        TimerTask second = new TimerTask() {

            @Override
            public void run() {
//                Log.i("Test", "Timer start");
                Update();

            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 1000, 3000);
    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMddHHmm");
                currentTime = sdfNow.format(date);
                int a=0;

                SecurityUtil securityUtil = new SecurityUtil();
                currentTime= securityUtil.encryptSHA256(currentTime);

                Intent intent = getIntent();
                //qrCode는 현재yyyymmddhhmm+userPassword의 해쉬화된 값.
                qrCode =currentTime+"//"+intent.getExtras().getString("userPassword");
                qrImage = (ImageView) findViewById(R.id.QR_Image);

                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                ////QR 코드화
                qrgEncoder = new QRGEncoder(
                        qrCode, null,
                        QRGContents.Type.TEXT,
                        smallerDimension);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v(TAG, e.toString());
                }


            }
        };
        handler.post(updater);
    }

}
