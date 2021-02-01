package com.garehn.ontheroad.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;
import com.garehn.ontheroad.graphics.CostCellAdapter;

import java.util.List;

public class CostListActivity extends AppCompatActivity {

        private static final int GAME_ACTIVITY_REQUEST_CODE = 11;

        // DATABASE
        private TripDatabase database;
        private TripDao tripDao;
        private CostDao costDao;

        //STRINGS
        private String[] categories;

        //GRAPHICS
        ListView listView;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_list);

            Intent intent = getIntent();
            if (intent != null) {
                categories = intent.getStringArrayExtra("Categories");
            }
            createDatabase();
            createGraphics();
    }

    public void createGraphics(){

            //List of categories
            listView = findViewById(R.id.cost_list_view);
            /*ArrayAdapter Example

            ArrayList<String> costList = new ArrayList<String>();
            for (int i = 0; i< costDao.getCosts(0).size();i++){
                costList.add(costDao.getCosts(0).get(i).toString());
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CostListActivity.this,
                    android.R.layout.simple_list_item_1, costList
                    );
            listView.setAdapter(adapter);*/

        CostCellAdapter adapter=new CostCellAdapter(CostListActivity.this, getCosts());
        listView.setAdapter(adapter);


    }

    public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
    }

    public List<Cost> getCosts(){
            return costDao.getCosts(0);
    }
}