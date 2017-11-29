package com.codename26.childanalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    public DataBaseHelper(Context context) {
        super(context, "analysisDB.db", null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

    public ArrayList<Analysis> getAnalysis(int subcategory, int search, int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Analysis> result = new ArrayList<>();
        if (subcategory > 0) {
            String query = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + " from " + MainActivity.ANALYSIS_TABLE_NAME
                    + " inner join " + MainActivity.SUBCATEGORY_ANALYSIS_TABLE_NAME + " on "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID + " = "
                    + MainActivity.SUBCATEGORY_ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                    + " inner join " + MainActivity.SUBCATEGORY_TABLE_NAME + " on "
                    + MainActivity.SUBCATEGORY_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID + " = "
                    + MainActivity.SUBCATEGORY_ANALYSIS_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID
                    + " where " + MainActivity.SUBCATEGORY_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID + " = " + String.valueOf(categoryId) + ";";


            /*String query = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.UNITS + " from " + MainActivity.ANALYSIS_TABLE_NAME
                    + " inner join " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + " on "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = "
                    + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                    + " inner join " + MainActivity.SUBCATEGORIES_TABLE_NAME + " on "
                    + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = "
                    + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID
                    + " where " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + String.valueOf(categoryId)
                    + " and " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.SEX + " = " + String.valueOf(subcategory)
                    + " and " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.AGE + " = " + String.valueOf(search) + ";";*/
            try {
                cursor = db.rawQuery(query, null);

                while (cursor.moveToNext()) {
                    Analysis mAnalysis = new Analysis();
                    mAnalysis.setAnalysisName(cursor.getString(cursor.getColumnIndex(MainActivity.ANALYSIS_NAME)));
                    mAnalysis.setAnalysisValue(cursor.getString(cursor.getColumnIndex(MainActivity.VALUE)));
                    result.add(mAnalysis);
                    //mAnalysis.setAnalysisUnits(cursor.getString(cursor.getColumnIndex(MainActivity.UNITS)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                // db.close();
            }
        } else if (subcategory == 0 & search == 0){
            Log.d("Retreiving analysis", "entering correct branch");

            String query = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + " from " + MainActivity.ANALYSIS_TABLE_NAME
                    + " inner join " + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME + " on "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID + " = "
                    + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                    + " inner join " + MainActivity.CATEGORIES_TABLE_NAME + " on "
                    + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = "
                    + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME + "." + MainActivity.CATEGORY_ID
                    + " where " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + String.valueOf(categoryId) + ";";

            try {
                cursor = db.rawQuery(query, null);

                while (cursor.moveToNext()) {
                    Analysis mAnalysis = new Analysis();
                    mAnalysis.setAnalysisName(cursor.getString(cursor.getColumnIndex(MainActivity.ANALYSIS_NAME)));
                    mAnalysis.setAnalysisValue(cursor.getString(cursor.getColumnIndex(MainActivity.VALUE)));
                    result.add(mAnalysis);
                    Log.d("Retreiving analysis", mAnalysis.getAnalysisName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                // db.close();
            }


        }
        return result;
    }

    public ArrayList<SearchResult> search(String query) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<SearchResult> searchResult = new ArrayList<>();

        try {
            String selection = MainActivity.ANALYSIS_NAME + " LIKE ?";
            String dbQuery = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                    + ", " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME
                    + ", " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " as " + MainActivity.CATEGORY_ID
                    + ", " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.CATEGORY_NAME
                    +" from " + MainActivity.ANALYSIS_TABLE_NAME +
                    " INNER JOIN " + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME + " ON " +
                    MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID + " = " + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME
                    + "." + MainActivity.ANALYSIS_ID + " INNER JOIN " + MainActivity.CATEGORIES_TABLE_NAME  + " ON "
                    + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID + " = " + MainActivity.CATEGORY_ANALYSIS_TABLE_NAME
                    + "." + MainActivity.CATEGORY_ID + " where " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME_LC
                    + " LIKE ?;";

            String queryLC = query.toLowerCase();
            cursor = db.rawQuery(dbQuery, new String[] {"%" + queryLC + "%"});
            SearchResult mSearchResult = new SearchResult();
            while (cursor.moveToNext()) {
                        if (!categoryInArray(cursor.getInt(cursor.getColumnIndex(MainActivity.CATEGORY_ID)), searchResult)) {
                            mSearchResult = new SearchResult();
                            mSearchResult.setAnalysisId(cursor.getInt(cursor.getColumnIndex(MainActivity.ANALYSIS_ID)));
                            mSearchResult.setAnalysisName(cursor.getString(cursor.getColumnIndex(MainActivity.ANALYSIS_NAME)));
                            mSearchResult.setCategoryId(cursor.getInt(cursor.getColumnIndex(MainActivity.CATEGORY_ID)));
                            mSearchResult.setCategoryName(cursor.getString(cursor.getColumnIndex(MainActivity.CATEGORY_NAME)));
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
