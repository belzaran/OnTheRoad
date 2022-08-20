package com.garehn.ontheroad.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.garehn.ontheroad.MainActivity;
import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.trip.Trip;

public class TripAddActivity extends CostBaseActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 20;

    EditText[] editText = new EditText[4];
    Button button;
    protected ImageView image;

    final static String LOG_TRIP = "Creating trip %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        createCategories();
        createDatabase();
        getTripId();
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.trip_add_editText0);
        editText[1] = findViewById(R.id.trip_add_editText1);
        editText[2] = findViewById(R.id.trip_add_editText2);

        image = findViewById(R.id.trip_add_icon);
        image.setImageResource(R.drawable.icon_luggage);

        button = findViewById(R.id.trip_add_button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == button){
            AddTrip();
        }



    }

    public void AddTrip() {
        //tripId = tripDao.getCount()+1;
        Trip trip = new Trip(editText[0].getText().toString(), Integer.valueOf(editText[1].getText().toString()),Integer.valueOf(editText[2].getText().toString()));
        tripDao.createTrip(trip);
        tripDao.updateTrip(trip);
        Log.i("ONTHEROAD_ADDTRIP", String.format(LOG_TRIP, trip.getName()) + " | id = " + trip.getId());
        Intent activity = new Intent(TripAddActivity.this, TripListActivity.class);
        activity.putExtra("Categories", categories);
        activity.putExtra("TripId", tripId);
        setResult(RESULT_OK, activity);
        startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        finish();
    }
}