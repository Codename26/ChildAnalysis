package com.codename26.childanalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by PC on 10.11.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ListElementViewHolder> {

    private LayoutInflater mLayoutInflater;
    private int mResource;
    private ArrayList<Category> mElements;

    public RecyclerAdapter(Context context, int resource, ArrayList<Category> elements){
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
        mElements = elements;

    }

    @Override
    public ListElementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(mResource, viewGroup, false);
        return new ListElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListElementViewHolder listElementViewHolder, int i) {
        final Category mCategory = mElements.get(i);
        listElementViewHolder.mTextView.setText(mCategory.getCategoryName());

        if (mItemClickListener != null){
            listElementViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.OnItemClick(mCategory);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(Category category);
    }
}
