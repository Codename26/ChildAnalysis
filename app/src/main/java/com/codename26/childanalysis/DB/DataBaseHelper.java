package com.codename26.childanalysis.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codename26.childanalysis.Analysis.Analysis;
import com.codename26.childanalysis.Category;
import com.codename26.childanalysis.Analysis.ComplexAnalysis;
import com.codename26.childanalysis.MainActivity;
import com.codename26.childanalysis.MultipleTypeAdapter.AnalysisModel;
import com.codename26.childanalysis.R;
import com.codename26.childanalysis.Search.SearchResult;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public ArrayList<AnalysisModel> getAnalysis(int subcategory, int search, int categoryId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Analysis> result = new ArrayList<>();
        if (subcategory > 0) {
            String query = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.URL
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID + ", "
                    + " from " + MainActivity.ANALYSIS_TABLE_NAME
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
                    mAnalysis.setUrl(cursor.getString(cursor.getColumnIndex(MainActivity.URL)));
                    mAnalysis.setId(cursor.getInt(cursor.getColumnIndex(MainActivity.ANALYSIS_ID)));
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
                    String query = "select " + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_NAME + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.VALUE + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.URL + ", "
                    + MainActivity.ANALYSIS_TABLE_NAME + "." + MainActivity.ANALYSIS_ID
                    + " from " + MainActivity.ANALYSIS_TABLE_NAME
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
                    mAnalysis.setUrl(cursor.getString(cursor.getColumnIndex(MainActivity.URL)));
                    mAnalysis.setAnalysisValue(cursor.getString(cursor.getColumnIndex(MainActivity.VALUE)));
                    mAnalysis.setId(cursor.getInt(cursor.getColumnIndex(MainActivity.ANALYSIS_ID)));
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
        }
        for (int i = 0; i < result.size(); i++) {
            List<ComplexAnalysis> list = new ArrayList<>();
            list = getComplexAnalysis(result.get(i).getId());
            if (list.size() > 0){
                result.get(i).setComplexAnalysisList(list);
            }
        }
        return analysisToAnalysisModel(result);
    }

    private ArrayList<AnalysisModel> analysisToAnalysisModel(ArrayList<Analysis> result) {
        ArrayList<AnalysisModel> list = new ArrayList<>();
        Analysis tempAnalysis;
        for (int i = 0; i < result.size(); i++) {
            tempAnalysis = result.get(i);
            AnalysisModel analysisModel = new AnalysisModel();
            analysisModel.setName(tempAnalysis.getAnalysisName());
            analysisModel.setUrl(tempAnalysis.getUrl());
            analysisModel.setType(AnalysisModel.TITLE_TYPE);
            list.add(analysisModel);
            List list1 = sortComplexAnalysisList(tempAnalysis.getComplexAnalysisList(), tempAnalysis.getAnalysisName());
            for (int j = 0; j < tempAnalysis.getComplexAnalysisList().size(); j++) {
                AnalysisModel analysisModelComplex = new AnalysisModel();
                analysisModelComplex.setText(tempAnalysis.getComplexAnalysisList().get(j).getText());
                analysisModelComplex.setValue(tempAnalysis.getComplexAnalysisList().get(j).getValue());
                analysisModelComplex.setUnits(tempAnalysis.getComplexAnalysisList().get(j).getUnits());
                if (tempAnalysis.getComplexAnalysisList().get(j).getSex() == AnalysisModel.MALE_TYPE){
                    analysisModelComplex.setType(AnalysisModel.MALE_TYPE);
                } else if (tempAnalysis.getComplexAnalysisList().get(j).getSex() == AnalysisModel.FEMALE_TYPE){
                    analysisModelComplex.setType(AnalysisModel.FEMALE_TYPE);
                } else {
                    analysisModelComplex.setType(AnalysisModel.NEUTRAL_TYPE);
                }
                list.add(analysisModelComplex);
            }
        }
        return list;
    }

    private List sortComplexAnalysisList(List<ComplexAnalysis> complexAnalysisList, String name) {
        List list = new ArrayList();
        List listGroup1 = new ArrayList();
        List listGroup2 = new ArrayList();
        List listGroup3 = new ArrayList();
        List listMale = new ArrayList();
        List listFemale = new LinkedList();
        if (complexAnalysisList.get(0).getGroup() > 0){
            for (int i = 0; i < complexAnalysisList.size(); i++) {
                if (complexAnalysisList.get(i).getGroup() == 1){
                    listGroup1.add(complexAnalysisList.get(i));
                } else if (complexAnalysisList.get(i).getGroup() == 2){
                    listGroup2.add(complexAnalysisList.get(i));
                } else listGroup3.add(complexAnalysisList.get(i));
            }
        } else {
            for (int i = 0; i < complexAnalysisList.size(); i++) {
                if (complexAnalysisList.get(i).getSex() == 1) {
                    listMale.add(complexAnalysisList.get(i));
                } else if (complexAnalysisList.get(i).getSex() == 2) {
                    listFemale.add(complexAnalysisList.get(i));

                }
            }

        }
        List<AnalysisModel> listAnaltsisModel1 = addTitles(listGroup1, name);


        return list;
    }

    private List<AnalysisModel> addTitles(List<ComplexAnalysis> listGroup, String name) {
        boolean isPrevMale = false;
        boolean isPrevFemale = false;
        String units = listGroup.get(0).getUnits();
        List<AnalysisModel> listAnalysisModel = new LinkedList<>();
       // listAnalysisModel1.add(0, new AnalysisModel(AnalysisModel.SUBTITLE_TYPE, name, listGroup1.get(0).getUnits()));
        for (int i = 0; i < listGroup.size(); i++) {
            if (i+1 < listGroup.size()){
                if (listGroup.get(i).getGroup() == listGroup.get(i+1).getGroup()){

                }
            }
            if (listGroup.get(i).getSex() == AnalysisModel.MALE_TYPE && !isPrevMale){
                listAnalysisModel.add(i, new AnalysisModel(AnalysisModel.MALE_TYPE, listGroup.get(i).getUnits()));
                i++;
                isPrevMale = true;
            }else if (listGroup.get(i).getSex() == AnalysisModel.FEMALE_TYPE && !isPrevFemale){
                listAnalysisModel.add(i, new AnalysisModel(AnalysisModel.FEMALE_TYPE, listGroup.get(i).getUnits()));
                i++;
                isPrevFemale = true;
            }
        }
        return listAnalysisModel;
    }

    public ArrayList<SearchResult> search(String query) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        Cursor categoryCursor = null;
        Cursor subcategoryCursor = null;
        ArrayList<SearchResult> searchResult = new ArrayList<>();
        String queryLC = query.toLowerCase();


        try {
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


            cursor = db.rawQuery(dbQuery, new String[] {"%" + queryLC + "%"});
            while (cursor.moveToNext()) {
                        if (!categoryInArray(cursor.getInt(cursor.getColumnIndex(MainActivity.CATEGORY_ID)), searchResult)) {
                            SearchResult mSearchResult = new SearchResult();
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
        //Categories search

        try{
            String dbQuery = "select " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.CATEGORY_NAME
                    + ", " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.COLUMN_ID
                    +", " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.CATEGORY_NAME_LC
                    +" from " + MainActivity.CATEGORIES_TABLE_NAME
                    +" where " + MainActivity.CATEGORIES_TABLE_NAME + "." + MainActivity.CATEGORY_NAME_LC
                    + " LIKE ?;";
            categoryCursor = db.rawQuery(dbQuery, new String[] {"%" + queryLC + "%"});
            while (categoryCursor.moveToNext()) {
                if (!categoryInArray(categoryCursor.getInt(categoryCursor.getColumnIndex(MainActivity.COLUMN_ID)), searchResult)) {
                    SearchResult mSearchResult = new SearchResult();
                    mSearchResult.setCategoryId(categoryCursor.getInt(categoryCursor.getColumnIndex(MainActivity.COLUMN_ID)));
                    mSearchResult.setCategoryName(categoryCursor.getString(categoryCursor.getColumnIndex(MainActivity.CATEGORY_NAME)));
                    mSearchResult.setAnalysisName(mContext.getString(R.string.read_more));
                    mSearchResult.setIsCategory(true);
                    searchResult.add(mSearchResult);
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (categoryCursor != null) {
                cursor.close();
            }
        }

        //Subcategories search

        try{
            String dbQuery = "select " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.SUBCATEGORY_NAME
                    + ", " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.SUBCATEGORY_ID
                    +", " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.SUBCATEGORY_NAME_LC
                    +" from " + MainActivity.SUBCATEGORIES_TABLE_NAME
                    +" where " + MainActivity.SUBCATEGORIES_TABLE_NAME + "." + MainActivity.SUBCATEGORY_NAME_LC
                    + " LIKE ?;";
            subcategoryCursor = db.rawQuery(dbQuery, new String[] {"%" + queryLC + "%"});
            while (subcategoryCursor.moveToNext()) {
                if (!categoryInArray(subcategoryCursor.getInt(subcategoryCursor.getColumnIndex(MainActivity.SUBCATEGORY_ID)), searchResult)) {
                    SearchResult mSearchResult = new SearchResult();
                    mSearchResult.setCategoryId(subcategoryCursor.getInt(subcategoryCursor.getColumnIndex(MainActivity.SUBCATEGORY_ID)));
                    mSearchResult.setCategoryName(subcategoryCursor.getString(subcategoryCursor.getColumnIndex(MainActivity.SUBCATEGORY_NAME)));
                    mSearchResult.setAnalysisName(mContext.getString(R.string.read_more));
                    mSearchResult.setIsSubCategory(true);
                    searchResult.add(mSearchResult);
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (categoryCursor != null) {
                cursor.close();
            }
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

    public ArrayList<ComplexAnalysis> getComplexAnalysis(int analysisId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        ArrayList<ComplexAnalysis> result = new ArrayList<>();
        if (analysisId > 0) {
            String query = "select * from "
                    + MainActivity.COMPLEX_ANALYSIS_TABLE_NAME
                    + " where " + MainActivity.COMPLEX_ANALYSIS_PARENT_ID + " = \"" + String.valueOf(analysisId) +"\""
                    +";";

            try {
                cursor = db.rawQuery(query, null);

                while (cursor.moveToNext()) {
                    ComplexAnalysis mComplexAnalysis = new ComplexAnalysis();
                    mComplexAnalysis.setText(cursor.getString(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_TEXT)));
                    mComplexAnalysis.setValue(cursor.getString(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_VALUE)));
                    mComplexAnalysis.setUnits(cursor.getString(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_UNITS)));
                    mComplexAnalysis.setSex(cursor.getInt(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_SEX)));
                    mComplexAnalysis.setGroup(cursor.getInt(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_GROUP)));
                    mComplexAnalysis.setGroupName(cursor.getString(cursor.getColumnIndex(MainActivity.COMPLEX_ANALYSIS_GROUP_NAME)));
                    result.add(mComplexAnalysis);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return result;
    }
}