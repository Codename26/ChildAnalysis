package com.codename26.childanalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.TextViewCompat;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PC on 15.11.2017.
 */

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisViewHolder> {

    private LayoutInflater mLayoutInflater;
    private int mResource;
    private ArrayList<Analysis> mElements;
    private int mExpandedPosition = -1;
    private ViewGroup mViewGroup;


    public AnalysisAdapter(Context context, int resource, ArrayList<Analysis> elements, ViewGroup viewGroup){
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
        mElements = elements;
        mViewGroup = viewGroup;
    }

    @Override
    public AnalysisViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(mResource, viewGroup, false);
        return new AnalysisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnalysisViewHolder analysisViewHolder, final int position) {
        final Analysis mAnalysis = mElements.get(position);
        analysisViewHolder.mTextViewAnalysisName.setText(mAnalysis.getAnalysisName());
        if (  android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        {
            analysisViewHolder.mTextViewAnalysisValue.setText(Html.fromHtml(mAnalysis.getAnalysisValue(),Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            analysisViewHolder.mTextViewAnalysisValue.setText(Html.fromHtml(mAnalysis.getAnalysisValue()));
        }

       // analysisViewHolder.mTextViewAnalysisValue.setText(mAnalysis.getAnalysisValue());
        if (mAnalysis.getUrl() != null && !mAnalysis.getUrl().equals("") ){
            analysisViewHolder.mInfoButton.setVisibility(View.VISIBLE);
        if (mInfoButtonClickListener != null) {
            analysisViewHolder.mInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInfoButtonClickListener.OnInfoButtonClick(mAnalysis);
                }
            });
        }
        }
      /*  analysisViewHolder.mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


       /* if (mItemClickListener != null){
            analysisViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.OnItemClick(mAnalysis);
                }
            });
        }*/

    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    private AnalysisAdapter.ItemClickListener mItemClickListener;
    private AnalysisAdapter.InfoButtonClickListener mInfoButtonClickListener;

    public void setInfoButtonClickListener(AnalysisAdapter.InfoButtonClickListener listener){
        mInfoButtonClickListener = listener;
    }

    public void setItemClickListener(AnalysisAdapter.ItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(Analysis analysis);
    }

    public interface InfoButtonClickListener{
        void OnInfoButtonClick(Analysis analysis);
    }
}
