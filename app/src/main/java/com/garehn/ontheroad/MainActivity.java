package com.garehn.ontheroad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.CostActivity;
import com.garehn.ontheroad.trip.Trip;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 0;

    //STRINGS
    private static String LOG_DATABASE = "Creating database";
    private static String LOG_TRIP = "Creating trip %s";
    private static String LOG_COST = "Cost n°%s : %s - %s €";

    //GRAPHICS
    private Button button;
    private TextView textPrice;

    //DATABASE
    private TripDatabase database;
    TripDao tripDao;
    CostDao costDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGraphics();
        createDatabase();
        //createTrip();
        //getCost();
        updateGraphics();
    }

    public void onResume() {
        super.onResume();
        updateGraphics();
    }

    public void createGraphics(){
        textPrice = findViewById(R.id.main_text_price);
        button = findViewById(R.id.main_button);
        button.setOnClickListener(this);
    }

    public void updateGraphics(){
        float f = getFullPrice();
        textPrice.setText(f + " €");
    }

    public void createDatabase(){
        database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        tripDao = database.tripDao();
        costDao = database.costDao();
        Log.i("ONTHEROAD_MAIN", String.format(LOG_DATABASE));
    }

    public void createTrip() {
        Trip trip = new Trip();
        trip.setName("Paris");
        trip.setBudget(500);
        trip.setDuration(7);
        tripDao.createTrip(trip);
        Log.i("ONTHEROAD_MAIN", String.format(LOG_TRIP, trip.getName()));
    }

    public void getCost(){
        int max  = costDao.getCosts(0).size();
        Cost cost;
        for (int i=0;i< max;i++){
            cost = costDao.getCosts(0).get(i);
            Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice()));
        }
    }

    /*public void getTrip() throws InterruptedException {
        Trip trip = LiveDataUtil.getValue(tripDao.getTrip(0));
        String name = trip.getName();
        Log.i("ONTHEROAD_MAIN", String.format(LOG_TRIP, name));
    }*/

    public float getFullPrice(){
        float price = 0;
        int max  = costDao.getCosts(0).size();
        Cost cost;
        for (int i=0;i< max;i++){
            cost = costDao.getCosts(0).get(i);
            Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice()));
            price += cost.getPrice();
        }
        return price;
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            Intent activity = new Intent(MainActivity.this, CostActivity.class);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
    }
}