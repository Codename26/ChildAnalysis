package com.codename26.childanalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
    private ArrayList<Analysis> mAnalyses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_search_results);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
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
        initProgressDialog();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAnalysisActivity);
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
            initRecyclerView();
            mDialog.dismiss();
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AnalysisAdapter adapter = new AnalysisAdapter(this, R.layout.analysis_list_item, mAnalyses, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        initToolBar();

    }

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
}
