package com.codename26.childanalysis;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dell on 09.11.2017.
 */

public class
ListElementViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public ImageView mIcon;
    public ListElementViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.textView);
        mIcon = itemView.findViewById(R.id.iv_icon);
    }
}
