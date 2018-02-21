package com.codename26.childanalysis.Subcategories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codename26.childanalysis.Category;
import com.codename26.childanalysis.ListElementViewHolder;
import com.codename26.childanalysis.RecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by User on 24.12.2017.
 */

public class SubcategoriesAdapter extends RecyclerView.Adapter<ListElementViewHolder> {
    private LayoutInflater mLayoutInflater;
    private int mResource;
    private ArrayList<Category> mElements;
    private Category mSuperCategory;
    Context mContext;

    public SubcategoriesAdapter(Context context, int resource, ArrayList<Category> elements, Category superCategory){
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
        mElements = elements;
        mContext = context;
        mSuperCategory = superCategory;

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
        if ( mSuperCategory.getIcon() != null ) {
            if (!mSuperCategory.getIcon().equals("")) {
                String iconID = mSuperCategory.getIcon();
                int resourceID = mContext.getResources().getIdentifier(iconID, "drawable",
                        mContext.getPackageName());
                listElementViewHolder.mIcon.setImageResource(resourceID);
            }
        }

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

    private RecyclerAdapter.ItemClickListener mItemClickListener;

    public void setItemClickListener(RecyclerAdapter.ItemClickListener listener){
        mItemClickListener = listener;
    }

    public interface ItemClickListener{
        void OnItemClick(Category category);
    }

}
