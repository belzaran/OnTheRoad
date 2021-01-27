package com.garehn.ontheroad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.garehn.ontheroad.trip.Trip;

public class AddTripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 20;

    EditText[] editText = new EditText[4];
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.travel_editText0);
        editText[1] = findViewById(R.id.travel_editText1);
        editText[2] = findViewById(R.id.travel_editText2);
        editText[3] = findViewById(R.id.travel_editText3);
        button = findViewById(R.id.travel_button_add);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == button){
            addTravel();
        }

    }

    public void addTravel(){
        String name = editText[0].getText().toString();
        String description = editText[1].getText().toString();
        int budget = Integer.valueOf(editText[3].getText().toString());
        int duration = Integer.valueOf(editText[2].getText().toString());

        // Take database.getTrip().size() to set the correct id

        Trip trip = new Trip(0, name, budget, duration);

        // put the trip in database
    }
}