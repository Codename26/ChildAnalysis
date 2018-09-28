package com.codename26.childanalysis.Subcategories;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.codename26.childanalysis.Analysis.AnalysisActivity;
import com.codename26.childanalysis.Category;
import com.codename26.childanalysis.DB.DataBaseHelper;
import com.codename26.childanalysis.MainActivity;
import com.codename26.childanalysis.R;
import com.codename26.childanalysis.RecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SubcategoriesActivity extends AppCompatActivity {
    private ArrayList<Category> mSubcategories;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar myToolbar;
    private Category mCategory;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar_search_results);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent  = getIntent();
        mCategory = intent.getParcelableExtra(MainActivity.EXTRA_CATEGORY);
      final DataBaseHelper helper = new DataBaseHelper(this);
        mSubcategories = helper.getCategories(mCategory.getCategoryId());
        System.out.println(mSubcategories.toString());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SubcategoriesAdapter adapter = new SubcategoriesAdapter(this, R.layout.list_item, mSubcategories, mCategory);
        mRecyclerView.setAdapter(adapter);
        initToolBar();


        adapter.setItemClickListener(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(Category category) {
               // ArrayList<Analysis> mAnalyses = helper.getAnalysis(1,5, category.getCategoryId());
               Intent intent = new Intent(SubcategoriesActivity.this, AnalysisActivity.class);
               intent.putExtra(MainActivity.EXTRA_CATEGORY_ID, category.getCategoryId());
               intent.putExtra(MainActivity.EXTRA_CATEGORY, category);
               intent.setAction(MainActivity.ACTION_SUBCATEGORY_ANALYSIS);

               //intent.putExtra(MainActivity.ANALYSIS_ARRAY, mAnalyses);
               startActivity(intent);
                }
            }
        );
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
