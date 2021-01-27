package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;

public class CostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;

    private EditText[] editText = new EditText[4];
    private Button button;
    private TripDatabase database;
    private static String LOG_DATABASE = "Creating database";
    private static String LOG_TRIP = "Trip n°%s : %s";
    private static String LOG_COST = "Cost n°%s : %s - %s €";
    private TripDao tripDao;
    private CostDao costDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        createGraphics();
        createDatabase();
        getCost(0);
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.cost_editText0);
        editText[1] = findViewById(R.id.cost_editText1);
        editText[2] = findViewById(R.id.cost_editText2);
        editText[3] = findViewById(R.id.cost_editText3);
        button = findViewById(R.id.cost_button_add);
        button.setOnClickListener(this);
    }

    public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
        Log.i("ONTHEROAD_COST", String.format(LOG_DATABASE));
    }

    public void getTrip(){
        Trip trip = tripDao.getTrip(0);
        Log.i("ONTHEROAD_COST", String.format(LOG_TRIP, trip.getId(),trip.getName()));
    }

    public void getCost(long l){
        Cost cost = costDao.getCosts(0).get(0);
        Log.i("ONTHEROAD_COST", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice()));
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            String name = editText[0].getText().toString();
            float price = Float.valueOf(editText[1].getText().toString());
            long tripId = Long.valueOf(editText[2].getText().toString());
            Cost cost = new Cost(name, price, tripId);
            costDao.insertCost(cost);
            finish();

        }
    }
}
