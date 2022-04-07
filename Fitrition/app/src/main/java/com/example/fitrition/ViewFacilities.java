package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewFacilities extends AppCompatActivity {
    private TextView viewFacilitiesName;
    private ImageView viewFacilitiesImage;
    private RatingBar viewFacilitiesRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);

        viewFacilitiesName = (TextView) findViewById(R.id.viewFacilitiesName);
        viewFacilitiesImage = (ImageView) findViewById(R.id.viewFacilitiesImage);
        viewFacilitiesRatingBar = (RatingBar) findViewById(R.id.viewFacilitiesRatingBar);

        Bundle b = getIntent().getExtras();
        String facilitiesName = null;
        if(b != null){
            facilitiesName = b.getString("facilitiesName");
            viewFacilitiesName.setText(facilitiesName);
            //viewFacilitiesImage.setImageDrawable(this.getAssets().open(""));
        }

        /*Field[] drawables = android.R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                System.out.println("R.drawable." + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        /*for (int j = 1; j < 10; j++) {
            getDrawable();
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier("ic_back_arrow.xml", "drawable", getPackageName()));
            viewFacilitiesImage.setImageDrawable(drawable);

            }
         */

        int resId = this.getResources().getIdentifier(
                "ic_back_arrow",
                "drawable",
                this.getPackageName()
        );

        viewFacilitiesImage.setImageDrawable(getDrawable(resId));
        Log.d("ViewFaciltiyImage",Integer.toString(resId));

        viewFacilitiesRatingBar.setRating((float) 2.20);



    }
}