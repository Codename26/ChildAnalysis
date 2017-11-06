package com.codename26.childanalysis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "ChildAnalysis.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.CATEGORIES_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.CATEGORY_NAME + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.SUBCATEGORIES_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.CATEGORY_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY ("+ MainActivity.CATEGORY_ID +") REFERENCES " + MainActivity.CATEGORIES_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"),"
                + MainActivity.SUBCATEGORY_NAME + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.SUBCATEGORIES_ANALYSIS_TABLE_NAME + "("
                + MainActivity.SUBCATEGORY_ID + " INTEGER NOT NULL,"
                + MainActivity.ANALYSIS_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY ("+ MainActivity.SUBCATEGORY_ID +") REFERENCES " + MainActivity.SUBCATEGORIES_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +"),"
                + "FOREIGN KEY ("+ MainActivity.ANALYSIS_ID +") REFERENCES " + MainActivity.ANALYSIS_TABLE_NAME + " ("+ MainActivity.COLUMN_ID +");");
        sqLiteDatabase.execSQL("CREATE TABLE " + MainActivity.ANALYSIS_TABLE_NAME + "("
                + MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MainActivity.ANALYSIS_NAME + " TEXT NOT NULL,"
                + "1_month TEXT NOT NULL);");





    }


   @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean updateTask(GeoTask geoTask){
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
    }


}
