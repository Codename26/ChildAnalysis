package com.codename26.childanalysis.Analysis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 25.01.2018.
 */

public class ComplexAnalysis implements Parcelable {
    private int mId;
    private int mParentId;
    private String mText;
    private String mValue;
    private String mUnits;
    private int mSex;
    private int mGroup;
    private String mGroupName;


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getParentId() {
        return mParentId;
    }

    public void setParentId(int parentId) {
        mParentId = parentId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getUnits() {
        return mUnits;
    }

    public void setUnits(String units) {
        mUnits = units;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public int getGroup() {
        return mGroup;
    }

    public void setGroup(int group) {
        mGroup = group;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mParentId);
        dest.writeString(this.mText);
        dest.writeString(this.mValue);
        dest.writeString(this.mUnits);
        dest.writeInt(this.mSex);
        dest.writeInt(this.mGroup);
        dest.writeString(this.mGroupName);
    }

    public ComplexAnalysis() {
    }

    protected ComplexAnalysis(Parcel in) {
        this.mId = in.readInt();
        this.mParentId = in.readInt();
        this.mText = in.readString();
        this.mValue = in.readString();
        this.mUnits = in.readString();
        this.mSex = in.readInt();
        this.mGroup = in.readInt();
        this.mGroupName = in.readString();
    }

    public static final Creator<ComplexAnalysis> CREATOR = new Creator<ComplexAnalysis>() {
        @Override
        public ComplexAnalysis createFromParcel(Parcel source) {
            return new ComplexAnalysis(source);
        }

        @Override
        public ComplexAnalysis[] newArray(int size) {
            return new ComplexAnalysis[size];
        }
    };
}
