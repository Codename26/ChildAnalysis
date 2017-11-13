package com.codename26.childanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Intent intent = getIntent();
        ArrayList<Analysis> mAnalyses = intent.getParcelableArrayListExtra(MainActivity.ANALYSIS_ARRAY);

        String content = arrayToString(mAnalyses);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadDataWithBaseURL(null, content,"text/html", "UTF-8", null);
    }

    private String arrayToString(ArrayList<Analysis> analyses) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body><table><tbody>");
        for (int i = 0; i < analyses.size(); i++) {
            builder.append("<tr><td>" + analyses.get(i).getAnalysisName() + "</td>"
             + "<td>" + analyses.get(i).getAnalysisValue() + "</td>"
                    + "<td>" + analyses.get(i).getAnalysisUnits() + "</td> </tr>"
            );
        }
        builder.append("</tbody></table></body></html>");

        return builder.toString();
    }
}
