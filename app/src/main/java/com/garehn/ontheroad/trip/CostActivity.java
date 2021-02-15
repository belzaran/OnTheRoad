package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;

    // GRAPHICS
    private EditText[] editText = new EditText[3];
    private Button button;
    private Spinner spinner;
    private DatePicker datePicker;

    // DATABASE
    private TripDatabase database;
    private TripDao tripDao;
    private CostDao costDao;

    // STRINGS
    private static String LOG_DATABASE = "Creating database";
    private static String LOG_TRIP = "Trip n°%s : %s";
    private static String LOG_COST = "Cost n°%s : %s - %s €";
    private String[] Categories;
    String formatDate;

    // OTHERS
    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        Intent intent = getIntent();
        if (intent != null) {
               Categories = intent.getStringArrayExtra("Categories");
        }
        createDatabase();
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.cost_editText0);
        editText[1] = findViewById(R.id.cost_editText1);
        editText[2] = findViewById(R.id.cost_editText2);

        // BUTTON
        button = findViewById(R.id.cost_button_add);
        button.setOnClickListener(this);

        // SPINNER
        spinner = findViewById(R.id.cost_spinner);
        List list = new ArrayList();
        for(int i =0 ; i<Categories.length; i++){
            list.add(Categories[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // DATE
        datePicker = findViewById(R.id.cost_date_picker);
        datePicker.getYear();
        datePicker.getMonth();
        datePicker.getDayOfMonth();
    }

    public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
        Log.i("ONTHEROAD_COST", String.format(LOG_DATABASE));
    }

    public String getFormatDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
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
            date = LocalDate.of(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth());
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
            Cost cost = new Cost(name, price, tripId, categoryId, formattedDate);
            costDao.insertCost(cost);
            finish();
        }
    }
}
