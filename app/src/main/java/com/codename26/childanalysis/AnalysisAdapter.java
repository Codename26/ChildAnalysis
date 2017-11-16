package com.codename26.childanalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.transitionseverywhere.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        analysisViewHolder.mTextViewAnalysisValue.setText(mAnalysis.getAnalysisValue());

        //Google IO Expandable animation
        final boolean isExpanded = position==mExpandedPosition;
        analysisViewHolder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        analysisViewHolder.itemView.setActivated(isExpanded);
        analysisViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
              // TransitionManager.beginDelayedTransition(mViewGroup);
                notifyDataSetChanged();
            }
        });


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

    public void setItemClickListener(AnalysisAdapter.ItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(Analysis analysis);
    }
}
