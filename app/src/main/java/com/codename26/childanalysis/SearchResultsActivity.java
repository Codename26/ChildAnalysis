package com.codename26.childanalysis;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog mDialog;
    private Toolbar myToolbar;
    private String query;
    private ArrayList<SearchResult> mSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_search_results);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            DataBaseHelper helper = new DataBaseHelper(this);
            ArrayList<SearchResult> searchResults = helper.search(query);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_search_results_page);
            new SearchResultsActivity.LoadDataTask().execute(query);
            initProgressDialog();

        }
    }

    public class LoadDataTask extends AsyncTask<String, Void, ArrayList<SearchResult>> {

        @Override
        protected ArrayList<SearchResult> doInBackground(String... params) {
            DataBaseHelper helper = new DataBaseHelper(SearchResultsActivity.this);
            mSearchResults = helper.search(params[0]);
            return mSearchResults;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchResult> results) {
            super.onPostExecute(results);
            initRecyclerView();
            mDialog.dismiss();
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SearchResultsAdapter adapter = new SearchResultsAdapter(this, R.layout.search_result_item, mSearchResults);

        adapter.setItemClickListener(new SearchResultsAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(SearchResult searchResult) {
                Intent intent = new Intent(SearchResultsActivity.this, AnalysisActivity.class);
                if (searchResult.isCategory()){
                    intent.putExtra(MainActivity.EXTRA_CATEGORY_ID, searchResult.getCategoryId());
                    Category mCategory = new Category();
                    mCategory.setCategoryName(searchResult.getCategoryName());
                    mCategory.setCategoryId(searchResult.getCategoryId());
                    intent.putExtra(MainActivity.EXTRA_CATEGORY, mCategory);
                    intent.setAction(MainActivity.ACTION_SEARCH_RESULT);
                    //intent.putExtra(MainActivity.ANALYSIS_ARRAY, mAnalyses);
                    startActivity(intent);
                } else if (searchResult.isSubCategory()){
                    intent.putExtra(MainActivity.EXTRA_CATEGORY_ID, searchResult.getCategoryId());
                    Category mCategory = new Category();
                    mCategory.setCategoryName(searchResult.getCategoryName());
                    mCategory.setCategoryId(searchResult.getCategoryId());
                    intent.putExtra(MainActivity.EXTRA_CATEGORY, mCategory);
                    intent.setAction(MainActivity.ACTION_SEARCH_RESULT);
                    //intent.putExtra(MainActivity.ANALYSIS_ARRAY, mAnalyses);
                    startActivity(intent);
                }
                intent.putExtra(MainActivity.EXTRA_CATEGORY_ID, searchResult.getCategoryId());
                Category mCategory = new Category();
                mCategory.setCategoryName(searchResult.getCategoryName());
                mCategory.setCategoryId(searchResult.getCategoryId());
                intent.putExtra(MainActivity.EXTRA_CATEGORY, mCategory);
                intent.setAction(MainActivity.ACTION_SEARCH_RESULT);
                //intent.putExtra(MainActivity.ANALYSIS_ARRAY, mAnalyses);
                startActivity(intent);
            }
        });
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
        myToolbar.setTitle(getString(R.string.search_results) + query);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
