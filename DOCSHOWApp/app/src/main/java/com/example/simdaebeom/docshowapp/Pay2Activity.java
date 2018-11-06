/////////////////////
package com.example.simdaebeom.docshowapp;
import android.content.Context;
import android.content.DialogInterface;
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

public class Pay2Activity extends AppCompatActivity {
    WebView wb;
    Button btn1,btn2;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay2);

        wb = (WebView)findViewById(R.id.webview);

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
        public void androidLogWrite(final String log) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("##", log);
                }
            });
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
