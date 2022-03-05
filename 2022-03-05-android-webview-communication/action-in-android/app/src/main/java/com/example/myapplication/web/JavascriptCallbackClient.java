package com.example.myapplication.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class JavascriptCallbackClient {

    private Activity mContext;
    private WebView webView;

    public JavascriptCallbackClient(Activity activity, WebView webView) {
        this.mContext = activity;
        this.webView = webView;
    }

    @JavascriptInterface
    public void showToastMessage(final String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void callJavaScriptFunction() {
        webView.postDelayed(() -> {
            webView.evaluateJavascript(
                    "var event = new CustomEvent(\"javascriptFunction\", {\n" +
                            "    detail: {\n" +
                            "        data: 'KB-PIN'\n" +
                            "    }\n" +
                            "});\n" +
                            "window.dispatchEvent(event);\n"
                    , (result) -> {
                        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                    });
        }, 5000);
    }
}
