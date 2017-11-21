package com.codename26.childanalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "ChildAnalysis.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.CATEGORIES_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.CATEGORY_NAME + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.SUBCATEGORIES_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.SUBCATEGORY_NAME + " TEXT NOT NULL,"
                + MainActivity.CATEGORY_ID + " INTEGER NOT NULL, FOREIGN KEY ("+ MainActivity.CATEGORY_ID +") REFERENCES "
                + MainActivity.CATEGORIES_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"));");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "("
                + MainActivity.SUBCATEGORY_ID + " INTEGER NOT NULL,"
                + MainActivity.ANALYSIS_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY ("+ MainActivity.SUBCATEGORY_ID +") REFERENCES " + MainActivity.SUBCATEGORIES_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"),"
                + "FOREIGN KEY ("+ MainActivity.ANALYSIS_ID +") REFERENCES " + MainActivity.ANALYSIS_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"));");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.AGE_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.AGE_NAME + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.SEX_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.SEX_NAME + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.ANALYSIS_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.ANALYSIS_NAME + " TEXT NOT NULL,"
                + MainActivity.SEX + " INTEGER NOT NULL,"
                + MainActivity.AGE + " INTEGER NOT NULL,"
                + MainActivity.VALUE + " TEXT NOT NULL,"
                + MainActivity.UNITS +" TEXT NOT NULL,"
                + "FOREIGN KEY ("+ MainActivity.SEX +") REFERENCES " + MainActivity.SEX_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"),"
                + "FOREIGN KEY ("+ MainActivity.AGE +") REFERENCES " + MainActivity.AGE_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"));");
        
        fillDB(sqLiteDatabase);
    }

    private void fillDB(SQLiteDatabase sqLiteDatabase) {
        try {

                ContentValues values = new ContentValues();
            String[] s = {"Анализ крови", "Анализ мочи", "Печеночные пробы", "Почечные пробы", "Спермограмма"};
            for (int i = 0; i < s.length; i++) {
                values.put(MainActivity.CATEGORY_NAME, s[i]);
                sqLiteDatabase.insert(MainActivity.CATEGORIES_TABLE_NAME, null, values);
            }

                values = new ContentValues();
            String[] s1 = {"Общий Анализ крови", "Общий Анализ мочи", " Общий Печеночные пробы", "Общий Почечные пробы", "Общий Спермограмма"};
            for (int i = 0; i < s1.length; i++) {
                values.put(MainActivity.SUBCATEGORY_NAME, s1[i]);
                values.put(MainActivity.CATEGORY_ID, i + 1);
                sqLiteDatabase.insert(MainActivity.SUBCATEGORIES_TABLE_NAME, null, values);
            }

            values = new ContentValues();
            values.put(MainActivity.AGE_NAME, "1 день");
            sqLiteDatabase.insert(MainActivity.AGE_TABLE_NAME, null, values);
            for (int i = 1; i < 5; i++) {
                values.put(MainActivity.AGE_NAME, i + " неделя");
                sqLiteDatabase.insert(MainActivity.AGE_TABLE_NAME, null, values);
            }
            for (int i = 1; i < 12; i++) {
                values.put(MainActivity.AGE_NAME, i + " месяц");
                sqLiteDatabase.insert(MainActivity.AGE_TABLE_NAME, null, values);
            }
            for (int i = 1; i < 17; i++) {
                values.put(MainActivity.AGE_NAME, i + " год");
                sqLiteDatabase.insert(MainActivity.AGE_TABLE_NAME, null, values);
            }

            values = new ContentValues();
            values.put(MainActivity.SEX_NAME, "мальчик");
            sqLiteDatabase.insert(MainActivity.SEX_TABLE_NAME, null, values);
            values.put(MainActivity.SEX_NAME, "девочка");
            sqLiteDatabase.insert(MainActivity.SEX_TABLE_NAME, null, values);

            values = new ContentValues();
            values.put(MainActivity.ANALYSIS_NAME, "Эритроциты");
            values.put(MainActivity.SEX, 1);
            values.put(MainActivity.AGE, 1);
            values.put(MainActivity.VALUE, "9.6");
            values.put(MainActivity.UNITS, "ммоль/л");
            sqLiteDatabase.insert(MainActivity.ANALYSIS_TABLE_NAME, null, values);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
                    mCategory.setCategoryName(cursor.getString(cursor.getColumnIndex(MainActivity.CATEGORY_NAME)));
                    mCategory.setCategoryId(cursor.getInt(cursor.getColumnIndex(MainActivity.COLUMN_ID)));
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
