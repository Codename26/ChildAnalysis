package com.codename26.childanalysis.MultipleTypeAdapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.codename26.childanalysis.Analysis.Analysis;

public class AnalysisModel implements Parcelable {

    public static final int TITLE_TYPE=0;
    public static final int MALE_TYPE=1;
    public static final int FEMALE_TYPE=2;
    public static final int NEUTRAL_TYPE=3;
    public static final int SUBTITLE_TYPE=3;

    private int mType;
    private String mName;
    private String mValue;
    private String mUnits;
    private String mText;
    private String mUrl;

    public AnalysisModel (int type, String name, String text, String value, String units){
        mType = type;
        mText = text;
        mValue = value;
        mUnits = units;
        mName = name;
    }

    public  AnalysisModel(){

    }

    public AnalysisModel(int type, String units){
        mType = type;
        mUnits = units;
    }

    public AnalysisModel(int type, String name, String units){
        mType = type;
        mUnits = units;
        mName = name;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeString(this.mName);
        dest.writeString(this.mValue);
        dest.writeString(this.mUnits);
        dest.writeString(this.mText);
        dest.writeString(this.mUrl);
    }

    protected AnalysisModel(Parcel in) {
        this.mType = in.readInt();
        this.mName = in.readString();
        this.mValue = in.readString();
        this.mUnits = in.readString();
        this.mText = in.readString();
        this.mUrl = in.readString();
    }

    public static final Creator<AnalysisModel> CREATOR = new Creator<AnalysisModel>() {
        @Override
        public AnalysisModel createFromParcel(Parcel source) {
            return new AnalysisModel(source);
        }

        @Override
        public AnalysisModel[] newArray(int size) {
            return new AnalysisModel[size];
        }
    };
}
