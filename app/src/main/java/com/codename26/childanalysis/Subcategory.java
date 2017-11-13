package com.codename26.childanalysis;

import android.os.Parcel;
import android.os.Parcelable;


public class Subcategory implements Parcelable{
    private String mSubcategoryName;
    private int mSubcategoryId;
    private int mCategoryId;

    public String getSubcategoryName() {
        return mSubcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        mSubcategoryName = subcategoryName;
    }

    public int getSubcategoryId() {
        return mSubcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        mSubcategoryId = subcategoryId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSubcategoryName);
        dest.writeInt(this.mSubcategoryId);
        dest.writeInt(this.mCategoryId);
    }

    public Subcategory() {
    }

    protected Subcategory(Parcel in) {
        this.mSubcategoryName = in.readString();
        this.mSubcategoryId = in.readInt();
        this.mCategoryId = in.readInt();
    }

    public static final Creator<Subcategory> CREATOR = new Creator<Subcategory>() {
        @Override
        public Subcategory createFromParcel(Parcel source) {
            return new Subcategory(source);
        }

        @Override
        public Subcategory[] newArray(int size) {
            return new Subcategory[size];
        }
    };
}
