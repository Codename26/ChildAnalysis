package com.codename26.childanalysis;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        tv = (TextView) findViewById(R.id.textViewSearchResults);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            tv.setText("Результаты поиска для: " + query);
            DataBaseHelper helper = new DataBaseHelper(this);
            ArrayList<SearchResult> searchResults = helper.search(query);
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println(searchResults.get(i).getAnalysisName());
            }
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_search_results_page);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
           // AnalysisAdapter adapter = new AnalysisAdapter(this, R.layout.analysis_list_item, searchResults, mRecyclerView);
           // mRecyclerView.setAdapter(adapter);

        }
    }
}
