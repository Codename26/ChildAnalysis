package com.codename26.childanalysis.MultipleTypeAdapter;

import com.codename26.childanalysis.Analysis.Analysis;

public class AnalysisModel {

    public static final int TITLE_TYPE=0;
    public static final int MALE_TYPE=1;
    public static final int FEMALE_TYPE=2;
    public static final int NEUTRAL_TYPE=3;

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
}
