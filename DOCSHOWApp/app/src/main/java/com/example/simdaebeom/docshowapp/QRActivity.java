package com.example.simdaebeom.docshowapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRActivity extends AppCompatActivity {

    String TAG = "GenerateQRCode";
    EditText edtValue;
    ImageView qrImage;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        ///toolbar 구현
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        qrCode =intent.getExtras().getString("userPassword");
        qrImage = (ImageView) findViewById(R.id.QR_Image);

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
//        smallerDimension = smallerDimension * 3 / 4;
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
