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
    private ArrayList<String> mElements;

    public RecyclerAdapter(Context context, int resource, ArrayList<String> elements){
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
        listElementViewHolder.mTextView.setText(mElements.get(i));
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }
}
