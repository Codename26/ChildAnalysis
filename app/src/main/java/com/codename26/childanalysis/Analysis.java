package com.codename26.childanalysis;

import android.os.Parcel;
import android.os.Parcelable;

public class Analysis implements Parcelable {
    private String mAnalysisName;
    private String mAnalysisValue;
    private String mAnalysisUnits;
    private int sex;
    private int age;

    public String getAnalysisName() {
        return mAnalysisName;
    }

    public void setAnalysisName(String analysisName) {
        mAnalysisName = analysisName;
    }

    public String getAnalysisValue() {
        return mAnalysisValue;
    }

    public void setAnalysisValue(String analysisValue) {
        mAnalysisValue = analysisValue;
    }

    public String getAnalysisUnits() {
        return mAnalysisUnits;
    }

    public void setAnalysisUnits(String analysisUnits) {
        mAnalysisUnits = analysisUnits;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAnalysisName);
        dest.writeString(this.mAnalysisValue);
        dest.writeString(this.mAnalysisUnits);
        dest.writeInt(this.sex);
        dest.writeInt(this.age);
    }

    public Analysis() {
    }

    protected Analysis(Parcel in) {
        this.mAnalysisName = in.readString();
        this.mAnalysisValue = in.readString();
        this.mAnalysisUnits = in.readString();
        this.sex = in.readInt();
        this.age = in.readInt();
    }

    public static final Creator<Analysis> CREATOR = new Creator<Analysis>() {
        @Override
        public Analysis createFromParcel(Parcel source) {
            return new Analysis(source);
        }

        @Override
        public Analysis[] newArray(int size) {
            return new Analysis[size];
        }
    };
}
