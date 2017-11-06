package com.codename26.childanalysis;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AgePickerDialogFragment().show(getSupportFragmentManager(), "");
            }
        });

    }
}
