package com.codename26.childanalysis;

import android.app.Dialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String COLUMN_ID = "_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String SUBCATEGORIES_TABLE_NAME = "subcategories";
    public static final String SUBCATEGORIES_ANALYSIS_TABLE_NAME = "subcategories_analysis";
    public static final String SUBCATEGORY_NAME = "category_name";
    public static final String CATEGORY_ID = "category_id";
    public static final String SUBCATEGORY_ID = "subcategory_id" ;
    public static final String ANALYSIS_ID = "analysis_id";
    public static final String ANALYSIS_TABLE_NAME = "analysis";
    public static final String ANALYSIS_NAME = "analysis_name";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String VALUE = "value";
    public static final String UNITS = "units";
    public static final String AGE_TABLE_NAME = "age";
    public static final String AGE_NAME = "age_name";
    public static final String SEX_TABLE_NAME = "sex_table";
    public static final String SEX_NAME = "sex_name";

    private ArrayList<String> categories;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper helper = new DataBaseHelper(MainActivity.this);
        categories = helper.getCategories();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(this, R.layout.list_item, categories);
        mRecyclerView.setAdapter(adapter);



       /* Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalysisFragment analysisFragment = new AnalysisFragment();
                FragmentManager fm = getSupportFragmentManager();
                if (findViewById(R.id.fragmentContainer) != null) {
                    fm.beginTransaction().add(R.id.fragmentContainer, analysisFragment).commit();
                }

                new AgePickerDialogFragment().show(getSupportFragmentManager(), "");


            }
        });
        */

    }
}
