package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.garehn.ontheroad.R;

public class TripActionActivity extends CostBaseActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 22;

    Button button[] = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_action);
        createCategories();
        getTripId();
        createDatabase();
        createGraphics();
    }

    public void createGraphics() {
        button[0] = findViewById(R.id.trip_action_button_modify);
        button[1] = findViewById(R.id.trip_action_button_change);
        button[2] = findViewById(R.id.trip_action_button_add);

        for (int i = 0; i < 3; i++) {
            button[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {



        if (v == button[0]) {
            Intent activity = new Intent(TripActionActivity.this, TripModifyActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);

        } else if (v == button[1]) {
            Intent activity = new Intent(TripActionActivity.this, TripListActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);

        } else if (v == button[2]) {
            Intent activity = new Intent(TripActionActivity.this, TripAddActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }

        finish();

    }
}