package com.codename26.childanalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public ArrayList<String> getCategories(){
        ArrayList<String> categoriesArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(MainActivity.CATEGORIES_TABLE_NAME, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                categoriesArray.add(cursor.getString(cursor.getColumnIndex(MainActivity.CATEGORY_NAME)));
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

    public String getAnalysis(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String result = "";
        try {
            cursor = db.query(MainActivity.ANALYSIS_TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex("1_month"));
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


   /* public boolean updateTask(GeoTask geoTask){
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NAME, geoTask.getTaskName());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_DESCRIPTION, geoTask.getTaskDescription());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_TAG, geoTask.getTaskTag());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NOTIFICATION, geoTask.getTaskNotification());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LATITUDE, geoTask.getTaskLatitude());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LONGITUDE , geoTask.getTaskLongitude());

            db.update(com.codename26.maptasker.GeoTask.TABLE_NAME, values, com.codename26.maptasker.GeoTask.COLUMN_ID + "=" + geoTask.getTaskId(),null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  db.close();

        return false;
    }

    public long insertTask(GeoTask geoTask){
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NAME, geoTask.getTaskName());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_DESCRIPTION, geoTask.getTaskDescription());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_TAG, geoTask.getTaskTag());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NOTIFICATION, geoTask.getTaskNotification());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LATITUDE, geoTask.getTaskLatitude());
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LONGITUDE , geoTask.getTaskLongitude());

            id = db.insert(com.codename26.maptasker.GeoTask.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  db.close();

        return id;
    }

    public long newTask(){
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NAME, "");
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_DESCRIPTION, "");
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_TAG, "");
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_NOTIFICATION, 0);
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LATITUDE, 0);
            values.put(com.codename26.maptasker.GeoTask.COLUMN_TASK_LONGITUDE , 0);

            id = db.insert(com.codename26.maptasker.GeoTask.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  db.close();

        return id;
    }

    public GeoTask getTask(long id){
        SQLiteDatabase db = getWritableDatabase();
        GeoTask geoTask = new GeoTask();
        Cursor cursor = null;

        try {
            cursor = db.query(com.codename26.maptasker.GeoTask.TABLE_NAME, null, com.codename26.maptasker.GeoTask.COLUMN_ID + "=" + id, null, null, null, null);

            if (cursor.moveToFirst()) {

                    geoTask.setTaskId(cursor.getLong(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_ID)));
                    geoTask.setTaskName(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_NAME)));
                    geoTask.setTaskDescription(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_DESCRIPTION)));
                    geoTask.setTaskTag(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_TAG)));
                    geoTask.setTaskNotification(cursor.getInt(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_NOTIFICATION)));
                    geoTask.setTaskLatitude(cursor.getDouble(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_LATITUDE)));
                    geoTask.setTaskLongitude(cursor.getDouble(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_LONGITUDE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
           // db.close();
        }

        return geoTask;
    }

    public ArrayList<GeoTask> getTasks() {
        ArrayList<GeoTask> geoTasks = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(com.codename26.maptasker.GeoTask.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    GeoTask geoTask = new GeoTask();

                    geoTask.setTaskId(cursor.getLong(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_ID)));
                    geoTask.setTaskName(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_NAME)));
                    geoTask.setTaskDescription(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_DESCRIPTION)));
                    geoTask.setTaskTag(cursor.getString(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_TAG)));
                    geoTask.setTaskNotification(cursor.getInt(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_NOTIFICATION)));
                    geoTask.setTaskLatitude(cursor.getDouble(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_LATITUDE)));
                    geoTask.setTaskLongitude(cursor.getDouble(cursor.getColumnIndex(com.codename26.maptasker.GeoTask.COLUMN_TASK_LONGITUDE)));
                    geoTasks.add(geoTask);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
          //  db.close();
        }
        return geoTasks;
    }

    public boolean deleteTask(long id) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();

        try {
            count = db.delete(com.codename26.maptasker.GeoTask.TABLE_NAME, com.codename26.maptasker.GeoTask.COLUMN_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // db.close();

        return count > 0;
    }*/


}
