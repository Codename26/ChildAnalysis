package com.codename26.childanalysis.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codename26.childanalysis.Analysis.Analysis;
import com.codename26.childanalysis.Category;
import com.codename26.childanalysis.MainActivity;
import com.codename26.childanalysis.Search.SearchResult;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by PC on 24.11.2017.
 */

public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "analysisDB.db";
    private static final int DATABASE_VERSION = 2;
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public ArrayList<Category> getCategories(int categoryId){
        ArrayList<Category> categoriesArray = new ArrayList<>();
        if (categoryId == 0) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            try {
                cursor = db.query(MainActivity.CATEGORIES_TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {
                    Category mCategory = new Category();
                    mCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(MainActivity.CATEGORY_NAME)));
                    mCategory.setCategoryId(cursor.getInt(cursor.getColumnIndex(MainActivity.COLUMN_ID)));
                    mCategory.setHasSubcategory(cursor.getInt(cursor.getColumnIndex(MainActivity.CATEGORY_HAS_SUBCATEGORIES)));
                    mCategory.setIcon(cursor.getString(cursor.getColumnIndex(MainActivity.CATEGORY_ICON)));
                    categoriesArray.add(mCategory);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                // db.close();
            }

            return categoriesArray;
        } else if (categoryId > 0){
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            try {
                String query = MainActivity.CATEGORY_ID + " = " + categoryId;
                cursor = db.query(MainActivity.SUBCATEGORIES_TABLE_NAME, null, query, null, null, null, null);

                while (cursor.moveToNext()) {
                    Category mCategory = new Category();
                    mCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(MainActivity.SUBCATEGORY_NAME)));
                    mCategory.setCategoryId(cursor.getInt(cursor.getColumnIndex(MainActivity.SUBCATEGORY_ID)));
                    mCategory.setSuperCategoryId(categoryId);
                    categoriesArray.add(mCategory);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                // db.close();
            }

            return categoriesArray;
        }
        return categoriesArray;
    }

    public ArrayList<Analysis> getAnalysis(int sex, int age, int subcategoryId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Analysis> result = new ArrayList<>();
        String query  = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + ", "
                + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.UNITS + " from " + MainActivity.ANALYSIS_TABLE_NAME
                + " inner join " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + " on "
                + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = "
                + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                + " inner join " + MainActivity.SUBCATEGORIES_TABLE_NAME + " on "
                + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = "
                + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID
                + " where " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + String.valueOf(subcategoryId)
                + " and " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.SEX + " = " + String.valueOf(sex)
                + " and " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.AGE + " = " + String.valueOf(age) +";";
        try {
            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                Analysis mAnalysis = new Analysis();
                mAnalysis.setAnalysisName(cursor.getString(cursor.getColumnIndex(MainActivity.ANALYSIS_NAME)));
                String valueUnits = new StringBuilder().append(cursor.getString(cursor.getColumnIndex(MainActivity.VALUE)))
                        .append(" ").append(cursor.getString(cursor.getColumnIndex(MainActivity.UNITS))).toString();
                mAnalysis.setAnalysisValue(valueUnits);
                //mAnalysis.setAnalysisUnits(cursor.getString(cursor.getColumnIndex(MainActivity.UNITS)));
                result.add(mAnalysis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // db.close();
        }
        return result;
    }

    public ArrayList<SearchResult> search(String query) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<SearchResult> searchResult = new ArrayList<>();

        try {
            String selection = MainActivity.ANALYSIS_NAME + " LIKE ?";
            String dbQuery = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.COLUMN_ID
                    + ", " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME
                    + ", " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " as " + MainActivity.SUBCATEGORY_ID
                    + ", " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.SUBCATEGORY_NAME
                    +" from " + MainActivity.ANALYSIS_TABLE_NAME +
                    " INNER JOIN " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + " ON " +
                    MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME
                    + "." + MainActivity.ANALYSIS_ID + " INNER JOIN " + MainActivity.SUBCATEGORIES_TABLE_NAME  + " ON "
                    + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME
                    + "." + MainActivity.SUBCATEGORY_ID + " where " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME
                    + " LIKE ? ;";

            cursor = db.rawQuery(dbQuery, new String[] {"%" + query + "%"});
            SearchResult mSearchResult = new SearchResult();
            while (cursor.moveToNext()) {
                if (!categoryInArray(cursor.getInt(cursor.getColumnIndex(MainActivity.SUBCATEGORY_ID)), searchResult)) {
                    mSearchResult = new SearchResult();
                    mSearchResult.setAnalysisId(cursor.getInt(cursor.getColumnIndex(MainActivity.COLUMN_ID)));
                    mSearchResult.setAnalysisName(cursor.getString(cursor.getColumnIndex(MainActivity.ANALYSIS_NAME)));
                    mSearchResult.setCategoryId(cursor.getInt(cursor.getColumnIndex(MainActivity.SUBCATEGORY_ID)));
                    mSearchResult.setCategoryName(cursor.getString(cursor.getColumnIndex(MainActivity.SUBCATEGORY_NAME)));
                    searchResult.add(mSearchResult);
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // db.close();
        }


        return searchResult;
    }

    private boolean categoryInArray(int categoryId, ArrayList<SearchResult> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if (categoryId == arrayList.get(i).getCategoryId()){
                return true;
            }
        }
        return false;
    }
}
