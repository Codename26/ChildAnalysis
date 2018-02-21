package com.codename26.childanalysis.Search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 19.11.2017.
 */

public class SearchResult implements Parcelable {
    private String mCategoryName;
    private String mAnalysisName;
    private int mCategoryId;
    private int mAnalysisId;
    private boolean isCategory;
    private boolean isSubCategory;

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public String getAnalysisName() {
        return mAnalysisName;
    }

    public void setAnalysisName(String analysisName) {
        mAnalysisName = analysisName;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public int getAnalysisId() {
        return mAnalysisId;
    }

    public void setAnalysisId(int analysisId) {
        mAnalysisId = analysisId;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setIsCategory(boolean category) {
        isCategory = category;
    }

    public boolean isSubCategory() {
        return isSubCategory;
    }

    public void setIsSubCategory(boolean subCategory) {
        isSubCategory = subCategory;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCategoryName);
        dest.writeString(this.mAnalysisName);
        dest.writeInt(this.mCategoryId);
        dest.writeInt(this.mAnalysisId);
        dest.writeByte(this.isCategory ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSubCategory ? (byte) 1 : (byte) 0);
    }

    public SearchResult() {
    }

    protected SearchResult(Parcel in) {
        this.mCategoryName = in.readString();
        this.mAnalysisName = in.readString();
        this.mCategoryId = in.readInt();
        this.mAnalysisId = in.readInt();
        this.isCategory = in.readByte() != 0;
        this.isSubCategory = in.readByte() != 0;
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
