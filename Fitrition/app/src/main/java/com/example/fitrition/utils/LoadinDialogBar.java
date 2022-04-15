package com.example.fitrition.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.example.fitrition.R;

public class LoadinDialogBar {
    Context context;
    Dialog dialog;

    public LoadinDialogBar(Context context) {
        this.context = context;
    }
    public void ShowDialog (String title){
        dialog = new Dialog (context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.textView);

        titleTextView.setText(title);
        dialog.create();
        dialog.show();


    }
    public void HideDialog(){
        dialog.dismiss();
    }
}
