package com.garehn.ontheroad.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.garehn.ontheroad.MainActivity;
import com.garehn.ontheroad.R;

public class CostTypeActivity extends CostBaseActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 13;

    Button button[] = new Button[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_type);
        getTripId();
        createGraphics();
    }

    public void createGraphics(){
        button[0] = findViewById(R.id.cost_type_button_food);
        button[1] = findViewById(R.id.cost_type_button_transport);
        button[2] = findViewById(R.id.cost_type_button_accommodation);
        button[3] = findViewById(R.id.cost_type_button_activity);
        button[4] = findViewById(R.id.cost_type_button_gift);
        button[5] = findViewById(R.id.cost_type_button_other);
        for(int i = 0; i<6; i++){
            button[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == button[0]){
            Intent activity = new Intent(CostTypeActivity.this, CostActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 0);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[1]){
            Intent activity = new Intent(CostTypeActivity.this, CostActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 1);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[2]){
            Intent activity = new Intent(CostTypeActivity.this, AccommodationActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 2);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[3]){
            Intent activity = new Intent(CostTypeActivity.this, CostActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 3);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[4]){
            Intent activity = new Intent(CostTypeActivity.this, CostActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 4);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[5]){
            Intent activity = new Intent(CostTypeActivity.this, CostActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("CategoryId", 5);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }

        finish();

    }
}