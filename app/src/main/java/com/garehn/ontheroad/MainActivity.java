package com.garehn.ontheroad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 0;

    //STRINGS
    private static String LOG_DATABASE = "Creating database";
    private static String LOG_TRIP = "Creating trip %s";
    private static String LOG_COST = "Cost n°%s : %s - %s €";
    private static String TXT_PRICE = "%s €";

    //GRAPHICS
    private Button button;
    private TextView textPrice;
    //private TextView[] textPrices = new TextView[5];
    private PieChart pieChart; // x = data, y = value;

    //DATABASE
    private TripDatabase database;
    TripDao tripDao;
    CostDao costDao;
    private String[] Categories = {"Food", "Transport", "Accommodation", "Activities", "Gift", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        createGraphics();
        createTrip();
        updateGraphics();
    }

    public void onResume() {
        super.onResume();
        updateGraphics();
    }

    public void createGraphics(){
        textPrice= findViewById(R.id.main_text_price);
/*
        textPrices[0] = findViewById(R.id.main_text_0_price);
        textPrices[1] = findViewById(R.id.main_text_1_price);
        textPrices[2] = findViewById(R.id.main_text_2_price);
        textPrices[3] = findViewById(R.id.main_text_3_price);
        textPrices[4] = findViewById(R.id.main_text_4_price);
*/

        button = findViewById(R.id.main_button);
        button.setOnClickListener(this);

        pieChart = findViewById(R.id.main_chart);
        pieChart.setUsePercentValues(false);
        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(64);
        pieChart.setTransparentCircleRadius(10);
        Description desc = new Description();
        desc.setText("Expenses by categories");
        pieChart.setRotationEnabled(false);
        pieChart.setDescription(desc);
        refreshChartData();
    }

    public void refreshChartData(){
        ArrayList<PieEntry> datas = new ArrayList<>();
        //ArrayList<Object> xData = new ArrayList<>();
        for(int i=0;i < Categories.length;i++){
            if(getPrice(i) != 0) {
                datas.add(new PieEntry(getPrice(i), Categories[i])); // add values
            }
        }
        PieDataSet dataSet = new PieDataSet(datas, "Expenses");
        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(11f);
        pieChart.setDrawEntryLabels(true);
        pieChart.getLegend().setEnabled(false);
        dataSet.setColors(colors);
        pieChart.invalidate(); //refresh
    }

    public void updateGraphics(){
        float f = getFullPrice();
        textPrice.setText(f + " €");
        /*for(int i = 0; i < Categories.length-1; i++){
            textPrices[i].setText(String.format(TXT_PRICE, getPrice(i)));
        }*/
        refreshChartData();
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
            Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice(), cost.getCategoryId()));
            price += cost.getPrice();
        }
        return price;
    }

    public float getPrice(int id){
        float price = 0;
            if(costDao.getCategoryCost(id) != null) {
                List<Cost> costs = costDao.getCategoryCost((id));
                int max = costs.size();
                for (int i = 0; i < max; i++) {
                    price += costs.get(i).getPrice();
                }
            }

            return price;
        }


    @Override
    public void onClick(View v) {
        if(v == button){
            Intent activity = new Intent(MainActivity.this, CostActivity.class);
            activity.putExtra("Categories", Categories);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
    }
}