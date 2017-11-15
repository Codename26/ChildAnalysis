package com.codename26.childanalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    private int mSex;
    private int mAge;
    private int mCategoryId;
    private Category mCategory;
    private ProgressDialog mDialog;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_analysis);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        mSex = intent.getIntExtra(MainActivity.EXTRA_SEX, 1);
        mAge = intent.getIntExtra(MainActivity.EXTRA_AGE, 1);
        mCategoryId = intent.getIntExtra(MainActivity.EXTRA_CATEGORY_ID, 1);
        mCategory = intent.getParcelableExtra(MainActivity.EXTRA_CATEGORY);

        DataBaseHelper helper = new DataBaseHelper(this);
        ArrayList<Analysis> mAnalyses = helper.getAnalysis(1,5, mCategoryId);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAnalysisActivity);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AnalysisAdapter adapter = new AnalysisAdapter(this, R.layout.analysis_list_item, mAnalyses);
        mRecyclerView.setAdapter(adapter);
        initToolBar();

       // initProgressDialog();



        //String content = arrayToString(mAnalyses);

       /* WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadDataWithBaseURL(null, content,"text/html", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mDialog.dismiss();
            }
        });*/

    }

    private void initProgressDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Wait...");
        mDialog.setCancelable(false);
        mDialog.show();
    }
    /*private String arrayToString(ArrayList<Analysis> analyses) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body><style>\n" +
                "   table { \n" +
                "    width: 100%;\n" +
                "    border-collapse: collapse;\n" +
                "   }\n" +
                "   th { \n" +
                "    text-align: left;\n" +
                "    background: #ccc;\n" +
                "    padding: 5px;\n" +
                "    border: 1px solid black;\n" +
                "   }\n" +
                "   td { \n" +
                "    padding: 5px;\n" +
                "    border: 1px solid black;\n" +
                "   }\n" +
                "  </style><table border=\"1\"><tbody> <col width=\"60%\"><col>" +
                "   <col width=\"250\" valign=\"top\"><tr>" +
                "<th>Показатель</th><th>Норма</th>" +
                "</tr>");
        for (int i = 0; i < analyses.size(); i++) {
            builder.append("<tr><td>" + analyses.get(i).getAnalysisName() + "</td>"
             + "<td nowrap>" + analyses.get(i).getAnalysisValue()
                    + " " + analyses.get(i).getAnalysisUnits() + "</td> </tr>"
            );
        }
        builder.append("</tbody></table></body></html>");
        System.out.println(builder.toString());
        return builder.toString();
    }*/

    private void initToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
        myToolbar.setTitle(mCategory.getCategoryName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
