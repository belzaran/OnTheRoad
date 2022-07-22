package com.garehn.ontheroad.trip;
import com.garehn.ontheroad.MainActivity;
import com.garehn.ontheroad.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class TripModifyActivity extends CostBaseActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 21;

    // GRAPHICS
    protected EditText[] editText = new EditText[3];
    protected Button button;
    protected ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_modify);
        createCategories();
        createDatabase();
        getTripId();
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.trip_modify_editText0);
        editText[1] = findViewById(R.id.trip_modify_editText1);
        editText[2] = findViewById(R.id.trip_modify_editText2);
        editText[0].setText(getName(tripId));
        int budget = (int) getBudget(tripId);
        editText[1].setText("" + budget);
        editText[2].setText("" + getDuration(tripId));

        image = findViewById(R.id.trip_modify_icon);
        image.setImageResource(R.drawable.icon_luggage);

        button = findViewById(R.id.trip_modify_button);
        button.setOnClickListener(this);

    }

    public String getName(long id){
        String name = "";
        if(tripDao.getTrip(id) != null){
            name = tripDao.getTrip(id).getName();
        }
        return name;
    }

    public float getBudget(long id){
        float budget = 0;
        if(tripDao.getTrip(id) != null){
            budget = tripDao.getTrip(id).getBudget();
        }
        return budget;
    }

    public int getDuration(long id){
        int duration = 0;
        if(tripDao.getTrip(id) != null){
            duration = tripDao.getTrip(id).getDuration();
        }
        return duration;
    }

    @Override
    public void onClick(View v){
        if(v == button){
            String name = editText[0].getText().toString();
            int budget = Integer.valueOf(editText[1].getText().toString());
            int duration = Integer.valueOf(editText[2].getText().toString());
            Trip trip = tripDao.getTrip(tripId);
            trip.setName(name);
            trip.setBudget(budget);
            trip.setDuration(duration);
            tripDao.updateTrip(trip);

            Intent activity = new Intent(TripModifyActivity.this, MainActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
            finish();
        }
    }
}