package com.codename26.childanalysis.Analysis;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.codename26.childanalysis.Category;
import com.codename26.childanalysis.DB.DataBaseHelper;
import com.codename26.childanalysis.MainActivity;
import com.codename26.childanalysis.MultipleTypeAdapter.AnalysisModel;
import com.codename26.childanalysis.MultipleTypeAdapter.MultipleTypeAdapter;
import com.codename26.childanalysis.R;

import java.util.ArrayList;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity {
    private static final String TAG = "AnalysisActivity";
    private int mSex;
    private int mAge;
    private int mCategoryId;
    private Category mCategory;
    private ProgressDialog mDialog;
  //  private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar myToolbar;
    private ArrayList<Analysis> mAnalyses;
    private ArrayList<ComplexAnalysis> mComplexAnalyses;
    private ImageView mInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_search_results);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<AnalysisModel> list = new ArrayList<>();
        list.add(new AnalysisModel(AnalysisModel.TITLE_TYPE, "Test", "test text", "test Value", "test units"));
        list.add(new AnalysisModel(AnalysisModel.MALE_TYPE, "Test", "test text", "test Value", "test units"));
        list.add(new AnalysisModel(AnalysisModel.FEMALE_TYPE, "Test", "test text", "test Value", "test units"));
        list.add(new AnalysisModel(AnalysisModel.NEUTRAL_TYPE, "Test", "test text", "test Value", "test units"));

        MultipleTypeAdapter adapter = new MultipleTypeAdapter(list, this);
        LinearLayoutManager llManager = new LinearLayoutManager(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewAnalysisActivity);
        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

       /* Intent intent = getIntent();
        if (intent.getAction().equals(MainActivity.ACTION_SUBCATEGORY_ANALYSIS)) {
            mCategory = intent.getParcelableExtra(MainActivity.EXTRA_CATEGORY);
            mCategoryId = mCategory.getCategoryId();
            new LoadDataTask().execute(1, 0, mCategoryId);
        } else if (intent.getAction().equals(MainActivity.ACTION_SEARCH_RESULT)){
            mCategoryId = intent.getIntExtra(MainActivity.EXTRA_CATEGORY_ID, 1);
            mCategory = intent.getParcelableExtra(MainActivity.EXTRA_CATEGORY);
            new LoadDataTask().execute(0, 0, mCategoryId);
        } else if (intent.getAction().equals(MainActivity.ACTION_CATEGORY)){
            mCategory = intent.getParcelableExtra(MainActivity.EXTRA_CATEGORY);
            mCategoryId = mCategory.getCategoryId();
            Log.d("AnalysisActivity", mCategory.getCategoryName());

            new LoadDataTask().execute(0, 0, mCategoryId);
        }

      //  initProgressDialog();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAnalysisActivity);*/
    }


    public class LoadDataTask extends AsyncTask<Integer, Void, ArrayList<Analysis>>{

        @Override
        protected ArrayList<Analysis> doInBackground(Integer... params) {
            DataBaseHelper helper = new DataBaseHelper(AnalysisActivity.this);

            mAnalyses = helper.getAnalysis(params[0], params[1], params[2]);
            return mAnalyses;
        }

        @Override
        protected void onPostExecute(ArrayList<Analysis> analyses) {
            super.onPostExecute(analyses);
            //initRecyclerView();
            if (mDialog != null) {
                mDialog.dismiss();
            }
            for (int i = 0; i < mAnalyses.size(); i++) {
                mAnalyses.get(i).setAnalysisValue(complexAnalysisToTable(mAnalyses.get(i).getComplexAnalysisList()));
            }


        }
    }

   /* public class LoadComplexAnalysisTask extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... params) {
            DataBaseHelper helper = new DataBaseHelper(AnalysisActivity.this);
            mComplexAnalyses = helper.getComplexAnalysis(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            complexAnalysisToTable();
        }
    }*/

    private String complexAnalysisToTable(List<ComplexAnalysis> list) {
        mAnalyses = new ArrayList<>();
        if (list != null) {
            StringBuilder builder = new StringBuilder();
            List<ComplexAnalysis> maleAnalysis = getMaleAnalysis(list);
            List<ComplexAnalysis> femaleAnalysis = getFemaleAnalysis(list);
            builder.append("<html><body><table width=\"100%\"><tbody><tr bgcolor=\"#B3E5FC\">" +
                    "<td width=\"50%\" style=\"padding-bottom: 0.5em; padding-top: 0.5em; padding-left: 1em;\">" +
                    "Мальчики</td>" +
                    "<td  width=\"50%\" style=\"padding-bottom: 0.5em; padding-top: 0.5em; padding-left: 0.5em;\">*10<sup>12</sup>ммоль/л</td></tr>");
            for (int i = 0; i < maleAnalysis.size(); i++) {
                if (i % 2 == 0) {
                    builder.append("<tr bgcolor=\"#E3F2FD\">"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 1em;\">"
                            + maleAnalysis.get(i).getText() + "</td>"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 0.3em;\">"
                            + maleAnalysis.get(i).getValue() + "</td>"
                            + "</tr>");
                } else {
                    builder.append("<tr bgcolor=\"#B3E5FC\">"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 1em;\">"
                            + maleAnalysis.get(i).getText() + "</td>"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 0.3em;\">"
                            + maleAnalysis.get(i).getValue() + "</td>"
                            + "</tr>");
                }
            }
            builder.append("<tr bgcolor=\"#F8BBD0\">" +
                    "<td width=\"50%\" style=\"padding-bottom: 0.5em; padding-top: 0.5em; padding-left: 1em;\">" +
                    "Девочки</td>" +
                    "<td  width=\"50%\" style=\"padding-bottom: 0.5em; padding-top: 0.5em; padding-left: 0.5em;\">*10<sup>12</sup>ммоль/л</td></tr>");
            for (int i = 0; i < femaleAnalysis.size(); i++) {
                if (i % 2 == 0) {
                    builder.append("<tr bgcolor=\"#FFEBEE\">"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 1em;\">"
                            + femaleAnalysis.get(i).getText() + "</td>"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 0.3em;\">"
                            + femaleAnalysis.get(i).getValue() + "</td>"
                            + "</tr>");
                } else {
                    builder.append("<tr bgcolor=\"#F8BBD0\">"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 1em;\">"
                            + femaleAnalysis.get(i).getText() + "</td>"
                            + "<td width=\"50%\" style=\"padding-bottom: 0.12em; padding-top: 0.12em; padding-left: 0.3em;\">"
                            + femaleAnalysis.get(i).getValue() + "</td>"
                            + "</tr>");
                }
            }

            builder.append("</tbody></table></body></html>");
            return builder.toString();
        }
        return null;
    }

    private List<ComplexAnalysis> getFemaleAnalysis(List<ComplexAnalysis> complexAnalyses) {
        if (complexAnalyses != null) {
            List<ComplexAnalysis> list = new ArrayList<>();
            for (int i = 0; i < complexAnalyses.size(); i++) {
                if (complexAnalyses.get(i).getSex() == 2) {
                    list.add(complexAnalyses.get(i));
                }
            }
            return list;
        }
        return null;
    }

    private List<ComplexAnalysis> getMaleAnalysis(List<ComplexAnalysis> complexAnalyses) {
        if (complexAnalyses != null) {
            List<ComplexAnalysis> list = new ArrayList<>();
            for (int i = 0; i < complexAnalyses.size(); i++) {
                if (complexAnalyses.get(i).getSex() == 1) {
                    list.add(complexAnalyses.get(i));
                }
            }
            return list;
        }
        return null;
    }

  /*  private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AnalysisAdapter adapter = new AnalysisAdapter(this, R.layout.complex_analysis_list_item, mAnalyses, mRecyclerView);
        adapter.setInfoButtonClickListener(new AnalysisAdapter.InfoButtonClickListener() {
            @Override
            public void OnInfoButtonClick(Analysis analysis) {
                Intent intent = new Intent(AnalysisActivity.this, WebViewActivity.class);
                intent.putExtra(MainActivity.WEBVIEWURL, analysis.getUrl());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
        initToolBar();

    }*/

    private void initProgressDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Wait...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
