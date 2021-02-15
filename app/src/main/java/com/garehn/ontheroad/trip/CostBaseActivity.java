package com.garehn.ontheroad.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;

public abstract class CostBaseActivity extends AppCompatActivity {

    // DATABASE
    protected TripDatabase database;
    protected TripDao tripDao;
    protected CostDao costDao;

    //STRINGS
    protected String[] categories;
    protected final String dateFormat = "dd MMM yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCategories();
        createDatabase();
    }

    public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
    }

    public void createCategories(){
        Intent intent = getIntent();
        if (intent != null) {
            categories = intent.getStringArrayExtra("Categories");
        }
    }
}