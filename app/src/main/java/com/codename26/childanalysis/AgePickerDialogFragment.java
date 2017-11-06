package com.codename26.childanalysis;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Dell on 27.10.2017.
 */

public class AgePickerDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.choose_age);
        View v = inflater.inflate(R.layout.age_picker_dialog, null);
        setCancelable(false);
        return v;
    }

    private void initNumberPickers() {
        final NumberPicker npYear = (NumberPicker) getView().findViewById(R.id.numberPickerYear);
        final NumberPicker npMonth = (NumberPicker) getView().findViewById(R.id.numberPickerMonth);
        npYear.setMaxValue(15);
        npYear.setMinValue(0);
        npMonth.setMaxValue(12);
        npMonth.setMinValue(1);

        Button btn = getView().findViewById(R.id.buttonDialogOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initNumberPickers();
    }
}
