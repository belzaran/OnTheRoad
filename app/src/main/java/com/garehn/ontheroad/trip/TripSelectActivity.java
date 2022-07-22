package com.garehn.ontheroad.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.garehn.ontheroad.R;

public class TripSelectActivity extends CostBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_select);
        createCategories();
        createDatabase();
        getTripId();
        createGraphics();
    }

    @Override
    public void onClick(View v) {

    }

    public void createGraphics(){

    }
}