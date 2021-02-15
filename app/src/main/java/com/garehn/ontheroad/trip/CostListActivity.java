package com.garehn.ontheroad.trip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;
import com.garehn.ontheroad.graphics.CostCellAdapter;

import java.text.DecimalFormat;
import java.util.List;

public class CostListActivity extends AppCompatActivity implements View.OnClickListener {

        private static final int GAME_ACTIVITY_REQUEST_CODE = 11;

        // DATABASE
        private TripDatabase database;
        private TripDao tripDao;
        private CostDao costDao;


        //STRINGS
        private String[] categories;

        //GRAPHICS
        private ListView listView;
        private Integer[] images = {R.drawable.icon_food, R.drawable.icon_transport, R.drawable.icon_accommodation, R.drawable.icon_activities, R.drawable.icon_gift, R.drawable.icon_other};
        private CostCellAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_list);

            Intent intent = getIntent();
            if (intent != null) {
                categories = intent.getStringArrayExtra("Categories");
            }
            createDatabase();
            adapter=new CostCellAdapter(CostListActivity.this, getCosts(), images);
            createGraphics();
    }

    public void createGraphics(){

        //List of categories
        listView = findViewById(R.id.cost_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendDeleteMessage(getCosts().get(position).getId(), position);
            /*long i = getCosts().get(position).getId();
            costDao.deleteCost(i);
            adapter.setCosts(getCosts());
            adapter.notifyDataSetChanged();*/
            }
        });
    }

    public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
    }

    public List<Cost> getCosts(){
            return costDao.getCosts(0);
    }

    // OPEN A MESSAGE BOX
    public void sendDeleteMessage(long l,int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getCosts().get(position).getName())
                .setMessage("What do you want to do with this expense ?")
                .setPositiveButton("NOTHING", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        costDao.deleteCost(l);
                        adapter.setCosts(getCosts());
                        adapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {

        }


}
