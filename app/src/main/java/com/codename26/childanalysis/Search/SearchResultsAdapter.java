package com.codename26.childanalysis.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by PC on 21.11.2017.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private LayoutInflater mLayoutInflater;
    private int mResource;
    private ArrayList<SearchResult> mElements;

    public SearchResultsAdapter(Context context, int resource, ArrayList<SearchResult> elements) {
        mLayoutInflater = LayoutInflater.from(context);;
        mResource = resource;
        mElements = elements;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(mResource, viewGroup, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder searchResultViewHolder, int i) {

        final SearchResult mSearchResult = mElements.get(i);
        searchResultViewHolder.mTextViewAnalysisName.setText(mSearchResult.getAnalysisName());
        searchResultViewHolder.mTextViewCategoryName.setText(mSearchResult.getCategoryName());

        if (mItemClickListener != null){
            searchResultViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.OnItemClick(mSearchResult);
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
        void OnItemClick(SearchResult searchResult);
    }
}
