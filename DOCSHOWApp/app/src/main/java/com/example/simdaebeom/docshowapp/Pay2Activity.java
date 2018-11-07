package com.example.simdaebeom.docshowapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Pay2Activity extends AppCompatActivity {
    WebView wb;
    private String userID;
    private String doctorID;
    private String time;
    private String reservationNumber;
    private String date;
    private boolean putReservation=false;
    private String str;

    private final Handler handler = new Handler();
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);
        Intent getintent = getIntent();
        userID =getintent.getExtras().getString("userID");
        doctorID = getintent.getExtras().getString("doctorID");
        reservationNumber = getintent.getExtras().getString("reservationNumber");
        time = getintent.getExtras().getString("time");
        date = getintent.getExtras().getString("date");

        wb = (WebView)findViewById(R.id.webview);
        wb.addJavascriptInterface(new AndroidWebBridge(wb), "android");


        webSetting(wb);




    }
    public void webSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webView.getSettings().setJavaScriptEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("enable();", null);
        } else {
            webView.loadUrl("javascript:enable();");
        }
        alertSetting(webView);

        String userAgent = wb.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(userAgent+"DocShow");
        webView.addJavascriptInterface(new AndroidWebBridge(webView), "DocShow");
        webView.loadUrl("file:///android_asset/Hello.html");
        webView.setWebViewClient(new WebViewClientClass());
        
    }

    public void alertSetting(WebView webView){
        final Context myApp = this;

        webView.setWebChromeClient(new WebChromeClient() {
            @Override

            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(myApp)
                        .setTitle("DocShow")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();

                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                finish();

                    if (str.equals("1")){
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {

                                        Intent payIntent = new Intent(Pay2Activity.this, MainActivity.class);
                                        payIntent.putExtra("userID", userID);
                                        Pay2Activity.this.startActivity(payIntent);


                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Pay2Activity.this);
                                        builder.setMessage("예약이 실패했습니다.")
                                                .setNegativeButton("다시 시도", null)
                                        ;
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
                                        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                    ReservationRequest reservationRequest = new ReservationRequest(reservationNumber, doctorID, userID, date, time, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Pay2Activity.this);
                    queue.add(reservationRequest);

                }

                else{

                    Intent intent = new Intent(Pay2Activity.this, MainActivity.class);
                    intent.putExtra("userID", userID);
                    Pay2Activity.this.startActivity(intent); }
                return true;
            }
        });
    }
    private class AndroidWebBridge{
        private WebView webView;

        public AndroidWebBridge(WebView webView) {
            this.webView = webView;
        }
        @JavascriptInterface
        public void setMessage(final String log) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("##", log);
                    str = log;

                }
            });
        }
        @JavascriptInterface
        public void finish(){

            Intent intent = new Intent(Pay2Activity.this, MainActivity.class);
            intent.putExtra("userID", userID);
            Pay2Activity.this.startActivity(intent);

        }

    }

    private class WebViewClientClass extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
