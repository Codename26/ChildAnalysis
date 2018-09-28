package com.codename26.childanalysis.MultipleTypeAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codename26.childanalysis.R;

import java.util.ArrayList;

public class MultipleTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<AnalysisModel> mDataSet;
    Context mContext;

    public interface InfoButtonClickListener{
        void OnInfoButtonClick(AnalysisModel analysisModel);
    }

    public static class TitleTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvUnits;
        ImageView ivInfoButton;
        public TitleTypeViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvTitleName);
            tvUnits = (TextView) itemView.findViewById(R.id.tvTitleUnits);
            ivInfoButton = itemView.findViewById(R.id.infoButtonTitleType);
        }
    }

    public static class MaleTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        TextView tvValue;
        public MaleTypeViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvMaleText);
            tvValue = (TextView) itemView.findViewById(R.id.tvMaleValue);
        }
    }

    public static class FemaleTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        TextView tvValue;
        public FemaleTypeViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvFemaleText);
            tvValue = (TextView) itemView.findViewById(R.id.tvFemaleValue);
        }
    }

    public static class NeutralTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        TextView tvValue;
        public NeutralTypeViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvNeutralText);
            tvValue = (TextView) itemView.findViewById(R.id.tvNeutralValue);
        }
    }

    public MultipleTypeAdapter(ArrayList<AnalysisModel> data, Context context){
        mDataSet = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case AnalysisModel.TITLE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_type_layout, parent, false);
               return new TitleTypeViewHolder(view);
            case AnalysisModel.MALE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.male_type_layout, parent, false);
                return new MaleTypeViewHolder(view);
            case AnalysisModel.FEMALE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.female_type_layout, parent, false);
                return new FemaleTypeViewHolder(view);
            case AnalysisModel.NEUTRAL_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neutral_type_layout, parent, false);
                return new NeutralTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AnalysisModel item = mDataSet.get(position);
        Typeface fontMontserratBold = Typeface.createFromAsset(mContext.getAssets(),  "fonts/montserrat_bold.otf");
        Typeface fontMontserratMedium = Typeface.createFromAsset(mContext.getAssets(),  "fonts/montserrat_medium.otf");
        if (item != null){
            switch (item.getType()){
                case AnalysisModel.TITLE_TYPE:
                    ((TitleTypeViewHolder) holder).tvName.setText(item.getName());
                    ((TitleTypeViewHolder) holder).tvName.setTypeface(fontMontserratBold);
                    ((TitleTypeViewHolder) holder).ivInfoButton.setVisibility(View.INVISIBLE);

                    if (  android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    {
                        ((TitleTypeViewHolder) holder).tvUnits.setText(Html.fromHtml(item.getUnits(),Html.FROM_HTML_MODE_LEGACY));
                    }
                    else {
                        ((TitleTypeViewHolder) holder).tvUnits.setText(Html.fromHtml(item.getUnits()));
                    }
                    ((TitleTypeViewHolder) holder).tvUnits.setTypeface(fontMontserratMedium);
                    break;
                case AnalysisModel.MALE_TYPE:
                   // ((MaleTypeViewHolder) holder).tvText.setText(item.getText());
                    ((MaleTypeViewHolder) holder).tvText.setTypeface(fontMontserratMedium);
                    if (  android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    {
                        ((MaleTypeViewHolder) holder).tvValue.setText(Html.fromHtml(item.getUnits(),Html.FROM_HTML_MODE_LEGACY));
                    }
                    else {
                        ((MaleTypeViewHolder) holder).tvValue.setText(Html.fromHtml(item.getUnits()));
                    }
                    ((MaleTypeViewHolder) holder).tvValue.setTypeface(fontMontserratMedium);
                    break;
                case AnalysisModel.FEMALE_TYPE:
                    //((FemaleTypeViewHolder) holder).tvText.setText(item.getText());
                    ((FemaleTypeViewHolder) holder).tvText.setTypeface(fontMontserratMedium);

                    if (  android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                    {
                        ((FemaleTypeViewHolder) holder).tvValue.setText(Html.fromHtml(item.getUnits(),Html.FROM_HTML_MODE_LEGACY));
                    }
                    else {
                        ((FemaleTypeViewHolder) holder).tvValue.setText(Html.fromHtml(item.getUnits()));
                    }
                    ((FemaleTypeViewHolder) holder).tvValue.setTypeface(fontMontserratMedium);
                    break;
                case AnalysisModel.NEUTRAL_TYPE:
                    if (!item.getText().equals("")) {
                        ((NeutralTypeViewHolder) holder).tvText.setText(item.getText());
                        ((NeutralTypeViewHolder) holder).tvValue.setText(item.getValue());
                        ((NeutralTypeViewHolder) holder).tvValue.setTypeface(fontMontserratMedium);
                    } else {
                        ((NeutralTypeViewHolder) holder).tvText.setText(item.getValue());
                        ((NeutralTypeViewHolder) holder).tvValue.setText("");
                    }
                    ((NeutralTypeViewHolder) holder).tvText.setTypeface(fontMontserratMedium);

                    break;
            }
        }

        if (item.getUrl() != null && !item.getUrl().equals("") ){
            ((TitleTypeViewHolder) holder).ivInfoButton.setVisibility(View.VISIBLE);
            if (mInfoButtonClickListener != null) {
                ((TitleTypeViewHolder) holder).ivInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInfoButtonClickListener.OnInfoButtonClick(item);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mDataSet.get(position).getType()){
            case 0:
                return AnalysisModel.TITLE_TYPE;
            case 1:
                return AnalysisModel.MALE_TYPE;
            case 2:
                return AnalysisModel.FEMALE_TYPE;
            case 3:
                return AnalysisModel.NEUTRAL_TYPE;
         default:
             return -1;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private MultipleTypeAdapter.InfoButtonClickListener mInfoButtonClickListener;

    public void setInfoButtonClickListener(MultipleTypeAdapter.InfoButtonClickListener listener){
        mInfoButtonClickListener = listener;
    }
}
