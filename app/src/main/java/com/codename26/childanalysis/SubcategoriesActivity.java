package com.codename26.childanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SubcategoriesActivity extends AppCompatActivity {
    private ArrayList<Category> mSubcategories;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        Intent intent  = getIntent();
      Category mCategory = intent.getParcelableExtra(MainActivity.CATEGORY);
      final DataBaseHelper helper = new DataBaseHelper(this);
        mSubcategories = helper.getCategories(mCategory.getCategoryId());
        System.out.println(mSubcategories.toString());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, R.layout.list_item, mSubcategories);
        mRecyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(Category category) {
                ArrayList<Analysis> mAnalyses = helper.getAnalysis(1,5, category.getCategoryId());
                for (int i = 0; i < mAnalyses.size(); i++) {
                    System.out.println(mAnalyses.get(i).getAnalysisName() + " **** "
                            + mAnalyses.get(i).getAnalysisValue() + " ***** " + mAnalyses.get(i).getAnalysisUnits());
                }


            }
        });
    }
}
