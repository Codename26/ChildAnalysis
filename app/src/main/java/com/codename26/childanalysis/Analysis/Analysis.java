package com.codename26.childanalysis.Analysis;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Analysis implements Parcelable {
    private String mAnalysisName;
    private String mAnalysisValue;
    private String mAnalysisUnits;
    private String url;
    private int sex;
    private int age;
    private int id;
    private List<ComplexAnalysis> mComplexAnalysisList;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ComplexAnalysis> getComplexAnalysisList() {
        return mComplexAnalysisList;
    }

    public void setComplexAnalysisList(List<ComplexAnalysis> complexAnalysisList) {
        mComplexAnalysisList = complexAnalysisList;
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
        dest.writeString(this.url);
        dest.writeInt(this.sex);
        dest.writeInt(this.age);
        dest.writeInt(this.id);
        dest.writeTypedList(this.mComplexAnalysisList);
    }

    public Analysis() {
    }

    protected Analysis(Parcel in) {
        this.mAnalysisName = in.readString();
        this.mAnalysisValue = in.readString();
        this.mAnalysisUnits = in.readString();
        this.url = in.readString();
        this.sex = in.readInt();
        this.age = in.readInt();
        this.id = in.readInt();
        this.mComplexAnalysisList = in.createTypedArrayList(ComplexAnalysis.CREATOR);
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
