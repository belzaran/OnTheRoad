package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;

import java.util.ArrayList;
import java.util.List;

public class CostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;

    // GRAPHICS
    private EditText[] editText = new EditText[3];
    private Button button;
    private Spinner spinner;

    // DATABASE
    private TripDatabase database;
    private TripDao tripDao;
    private CostDao costDao;

    //STRINGS
    private static String LOG_DATABASE = "Creating database";
    private static String LOG_TRIP = "Trip n°%s : %s";
    private static String LOG_COST = "Cost n°%s : %s - %s €";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        createGraphics();
        createDatabase();
        //getCost(0);
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.cost_editText0);
        editText[1] = findViewById(R.id.cost_editText1);
        editText[2] = findViewById(R.id.cost_editText2);

        //BUTTON
        button = findViewById(R.id.cost_button_add);
        button.setOnClickListener(this);

        // SPINNER
        spinner = findViewById(R.id.cost_spinner);
        List list = new ArrayList();
        list.add("Food");
        list.add("Transport");
        list.add("Accommodation");
        list.add("Gift");
        list.add("Other");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
            int categoryId = (int) spinner.getSelectedItemId();
            Cost cost = new Cost(name, price, tripId, categoryId);
            costDao.insertCost(cost);
            finish();

        }
    }
}
