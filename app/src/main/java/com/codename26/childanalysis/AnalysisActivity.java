package com.codename26.childanalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    private String mSex;
    private String mAge;
    private int mCategoryId;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Intent intent = getIntent();
        DataBaseHelper helper = new DataBaseHelper(this);
        mSex = intent.getStringExtra(MainActivity.EXTRA_SEX);
        mAge = intent.getStringExtra(MainActivity.EXTRA_AGE);
        mCategoryId = intent.getIntExtra(MainActivity.EXTRA_CATEGORY_ID, 1);
        initProgressDialog();

        ArrayList<Analysis> mAnalyses = helper.getAnalysis(1,5, mCategoryId);

        String content = arrayToString(mAnalyses);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadDataWithBaseURL(null, content,"text/html", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mDialog.dismiss();
            }
        });

    }

    private void initProgressDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Wait...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    private String arrayToString(ArrayList<Analysis> analyses) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body><table border=\"1\"><tbody> <col width=\"20%\"><col width=\"80%\">" +
                "   <col width=\"250\" valign=\"top\"><tr>" +
                "<th>Показатель</th><th>Норма</th>" +
                "</tr>");
        for (int i = 0; i < analyses.size(); i++) {
            builder.append("<tr><td>" + analyses.get(i).getAnalysisName() + "</td>"
             + "<td>" + analyses.get(i).getAnalysisValue()
                    + " " + analyses.get(i).getAnalysisUnits() + "</td> </tr>"
            );
        }
        builder.append("</tbody></table></body></html>");

        return builder.toString();
    }
}
