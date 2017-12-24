package com.codename26.childanalysis;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private String mCategoryName;
    private int mCategoryId;
    private int mHasSubcategory;
    private int mSuperCategoryId;
    private String mIcon;

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public int getSuperCategoryId() {
        return mSuperCategoryId;
    }

    public void setSuperCategoryId(int superCategoryId) {
        mSuperCategoryId = superCategoryId;
    }

    public int getHasSubcategory() {
        return mHasSubcategory;
    }

    public void setHasSubcategory(int hasSubcategory) {
        mHasSubcategory = hasSubcategory;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCategoryName);
        dest.writeInt(this.mCategoryId);
        dest.writeInt(this.mHasSubcategory);
        dest.writeInt(this.mSuperCategoryId);
        dest.writeString(this.mIcon);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.mCategoryName = in.readString();
        this.mCategoryId = in.readInt();
        this.mHasSubcategory = in.readInt();
        this.mSuperCategoryId = in.readInt();
        this.mIcon = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
