package com.codename26.childanalysis;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.widget.TextViewCompat;

/**
 * Created by PC on 15.11.2017.
 */

public class AnalysisViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewAnalysisName;
    public TextView mTextViewAnalysisValue;
    public ImageView mInfoButton;
    public AnalysisViewHolder(View itemView) {
        super(itemView);
        mTextViewAnalysisName = itemView.findViewById(R.id.tvAnalysisName);
        mTextViewAnalysisValue = itemView.findViewById(R.id.tvAnalysisValue);
        mInfoButton = itemView.findViewById(R.id.infoButton);
    }


}
