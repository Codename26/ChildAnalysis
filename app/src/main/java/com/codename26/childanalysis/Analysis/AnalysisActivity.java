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
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity {
    private static final String TAG = "AnalysisActivity";
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
    private ArrayList<AnalysisModel> mAnalysisModels;
    private ArrayList<ComplexAnalysis> mComplexAnalyses;
    private ImageView mInfoButton;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_search_results);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
        mRecyclerView = findViewById(R.id.recyclerViewAnalysisActivity);
    }


    public class LoadDataTask extends AsyncTask<Integer, Void, ArrayList<AnalysisModel>>{

        @Override
        protected ArrayList<AnalysisModel> doInBackground(Integer... params) {
            DataBaseHelper helper = new DataBaseHelper(AnalysisActivity.this);

         //  mAnalyses = helper.getAnalysis(params[0], params[1], params[2]);
            mAnalysisModels = helper.getAnalysis(params[0], params[1], params[2]);
            return mAnalysisModels;
        }

        @Override
        protected void onPostExecute(ArrayList<AnalysisModel> analyses) {
            super.onPostExecute(analyses);
            initRecyclerView();
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }

    private void initRecyclerView() {
        MultipleTypeAdapter adapter = new MultipleTypeAdapter(mAnalysisModels, this);

        LinearLayoutManager llManager = new LinearLayoutManager(this);
        adapter.setInfoButtonClickListener(new MultipleTypeAdapter.InfoButtonClickListener() {
            @Override
            public void OnInfoButtonClick(AnalysisModel analysisModel) {
                Intent intent = new Intent(AnalysisActivity.this, WebViewActivity.class);
                intent.putExtra(MainActivity.WEBVIEWURL, analysisModel.getUrl());
                startActivity(intent);
            }
        });
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewAnalysisActivity);
        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
