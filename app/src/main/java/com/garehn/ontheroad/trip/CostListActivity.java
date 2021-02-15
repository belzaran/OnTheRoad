package com.garehn.ontheroad.trip;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.garehn.ontheroad.R;
import com.garehn.ontheroad.graphics.CostCellAdapter;
import java.util.List;

public class CostListActivity extends CostBaseActivity implements View.OnClickListener {

        private static final int GAME_ACTIVITY_REQUEST_CODE = 11;

        //GRAPHICS
        private ListView listView;
        private Integer[] images = {R.drawable.icon_food, R.drawable.icon_transport, R.drawable.icon_accommodation, R.drawable.icon_activities, R.drawable.icon_gift, R.drawable.icon_other};
        private CostCellAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_list);
        createCategories();
        createDatabase();
        adapter = new CostCellAdapter(CostListActivity.this, getCosts(), images);
        createGraphics();
    }

    public void createGraphics(){

        //List of categories
        listView = findViewById(R.id.cost_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                sendDeleteMessage(getCosts().get(position).getId(), position);
            }
        });
    }

    /*public void createDatabase(){
        this.database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        this.tripDao = database.tripDao();
        this.costDao = database.costDao();
    }*/

    public List<Cost> getCosts(){
            return costDao.getCosts(0);
    }

    // OPEN A MESSAGE BOX
    public void sendDeleteMessage(long l,int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getCosts().get(position).getName())
                .setMessage("What do you want to do with this expense ?")
                .setPositiveButton("MODIFY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int itemId = (int) adapter.getCosts().get(position).getId();

                        Intent activity = new Intent(CostListActivity.this, CostModifyActivity.class);
                        activity.putExtra("Categories", categories);
                        activity.putExtra("id", position);
                        setResult(RESULT_OK, activity);
                        startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
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
