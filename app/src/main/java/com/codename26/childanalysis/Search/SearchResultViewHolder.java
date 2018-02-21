package com.codename26.childanalysis.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codename26.childanalysis.R;

/**
 * Created by PC on 21.11.2017.
 */

public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextViewAnalysisName;
    public TextView mTextViewCategoryName;
    public SearchResultViewHolder(View itemView) {
        super(itemView);
        mTextViewAnalysisName = itemView.findViewById(R.id.textViewAnalysisName);
        mTextViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
    }
}
