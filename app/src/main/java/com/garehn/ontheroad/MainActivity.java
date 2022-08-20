package com.garehn.ontheroad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.database.TripDatabase;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.CostListActivity;
import com.garehn.ontheroad.trip.CostTypeActivity;
import com.garehn.ontheroad.trip.Trip;
import com.garehn.ontheroad.trip.TripActionActivity;
import com.garehn.ontheroad.trip.TripListActivity;
import com.garehn.ontheroad.trip.TripSelectActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /*----------------------------------------------------------------------------------------------
    VARIABLES
    ----------------------------------------------------------------------------------------------*/

    private static final int GAME_ACTIVITY_REQUEST_CODE = 0;

    //STRINGS
    final static String LOG_DATABASE = "Creating database";
    final static String LOG_GRAPHICS = "Creating graphics";
    final static String LOG_DAY = "Number of days : %s";
    final static String LOG_TRIP = "Creating trip %s";
    final static String LOG_COST = "Cost n°%s : %s - %s € - tripId %s";
    final static String TXT_PRICE = "%s €";
    protected final String dateFormat = "dd MMM yyyy";

    //GRAPHICS
    private ImageView[] button = new ImageView[3];
    private TextView textTitle;
    private PieChart pieChart; // x = data, y = value;
    public static final int[] TRIP_COLORS = {
            Color.rgb(255, 255, 255), Color.rgb(230, 30, 30), Color.rgb(100, 60, 180), Color.rgb(50, 180, 160),
            Color.rgb(230, 160, 30), Color.rgb(120, 140, 220), Color.rgb(220, 120, 160), Color.rgb(0, 0, 0)
    };

    //DATABASE
    private int tripId;
    private TripDatabase database;
    private TripDao tripDao;
    private CostDao costDao;
    private String[] categories = {"Food", "Transport", "Accommodation", "Activities", "Gift", "Others"};


    /*----------------------------------------------------------------------------------------------
    INITIALISATION
    ----------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase();
        getTripId();
        //isDatabaseEmpty();
        //checkDatabase(tripId);
        if(isTripSelected()){
            SelectTrip();
        }
        createGraphics();
        try {
            updateGraphics();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        try {
            updateGraphics();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase(){
        Log.i("ONTHEROAD_MAIN", String.format(LOG_DATABASE));
        database = Room.databaseBuilder(this, TripDatabase.class,"db").allowMainThreadQueries().build();
        //database.getInstance(this);
        tripDao = database.tripDao();
        costDao = database.costDao();
        showDatabase();
    }

    public void showDatabase() {
        int n = tripDao.getCount();
        for (int j = 0; j < n; j++) {
            if (costDao.getCosts(j) != null) {
                int max = costDao.getCosts(j).size();
                Cost cost;
                for (int i = 0; i < max; i++) {
                    cost = costDao.getCosts(j).get(i);
                    Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(), cost.getName(), cost.getPrice(), cost.getTripId()));
                }
            }
        }
    }

    public void createGraphics(){
        Log.i("ONTHEROAD_MAIN", String.format(LOG_GRAPHICS));

        textTitle = findViewById(R.id.main_title);

        button[0] = findViewById(R.id.main_button_add);
        button[1] = findViewById(R.id.main_button_list);
        button[2] = findViewById(R.id.main_button_trip);
        for(int i = 0; i < button.length; i++) {
            button[i].setOnClickListener(this);
        }

        pieChart = findViewById(R.id.main_chart);
        pieChart.setUsePercentValues(false);
        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(32);
        pieChart.setTransparentCircleRadius(10);
        //Description desc = new Description();
        //desc.setText("Expenses by categories");
        //pieChart.setDescription(desc);
        pieChart.setRotationEnabled(false);
        pieChart.getDescription().setEnabled(false);

        refreshChartData();
    }

    public void checkDatabase(long id){
        if(tripDao.getTrip(id)!= null){

        }
        else{
            createBasicTrip();
        }
    }

    public void isDatabaseEmpty(){
        if(tripDao != null){

        }
        else{
            createBasicTrip();
        }
    }

    public boolean isTripSelected() {
        boolean b = false;

        if (tripId == 0) {
            b = true;
        }
        return b;
    }

    public void createTrip() {
        Trip trip = new Trip();
        trip.setName("Paris");
        trip.setBudget(500);
        trip.setDuration(7);
        tripDao.createTrip(trip);
        tripDao.updateTrip(trip);
        Log.i("ONTHEROAD_MAIN", String.format(LOG_TRIP, trip.getName()));
    }

    public void createBasicTrip() {
        Trip trip = new Trip();
        trip.setName("Example");
        trip.setBudget(500);
        trip.setDuration(7);
        tripDao.createTrip(trip);
        tripDao.updateTrip(trip);
        Log.i("ONTHEROAD_MAIN", String.format(LOG_TRIP, trip.getName()));
    }

    /*----------------------------------------------------------------------------------------------
    REFRESHING DATAS & GRAPHICS
    ----------------------------------------------------------------------------------------------*/

    public void refreshChartData(){
        ArrayList<PieEntry> datas = new ArrayList<>();
        //ArrayList<Object> xData = new ArrayList<>();
        datas.add(new PieEntry(getRemainingBudget(tripId), "Remaining")); // must be in first to be in blank
        for(int i = 0; i < categories.length; i++){
            if(getPrice(i) != 0) {
                datas.add(new PieEntry(getPrice(i), categories[i])); // add values
            }
        }


        PieDataSet dataSet = new PieDataSet(datas, "Expenses");
        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : TRIP_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(11f);
        pieChart.setDrawEntryLabels(true);
        pieChart.getLegend().setEnabled(false);
        dataSet.setColors(colors);
        pieChart.invalidate(); //refresh
    }

    public void updateGraphics() throws ParseException {
        float f = getFullPrice(tripId);
        costDao.clean(0f);
        refreshTitle();
        refreshChartData();
        //refreshProgressBar();
    }

    /*public void refreshProgressBar() throws ParseException {
        budgetBar.setMax(getBudget(0));
        budgetBar.setProgress((int) getFullPrice(0));
        textBar.setText(String.format(TXT_PROGRESSBAR, getFullPrice(0),getBudget(0)));
        DecimalFormat formatter = new DecimalFormat("#.##");
        formatter.setRoundingMode(RoundingMode.DOWN);
        String costByDay = formatter.format(getFullPrice(0) / getDays(0));
        String fullCostByDay;

        if(getDuration(0) != 0) {
            budgetDayBar.setMax((int) (getBudget(0) * 100 / getDuration(0))); // round the cost with 2 decimals
            fullCostByDay = formatter.format(getBudget(0) / getDuration(0));


            if (getDays(0) != 0) {
                budgetDayBar.setProgress(Integer.valueOf((int) (getFullPrice(0) * 100 / getDays(0))));
                textDayBar.setText(String.format(TXT_PROGRESSBAR1, costByDay, fullCostByDay));
            } else {
                textDayBar.setText(String.format(TXT_PROGRESSBAR1, 0, fullCostByDay));
            }
        }

    }*/

    public void refreshTitle(){
        textTitle.setText(getName(tripId));
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS
    ----------------------------------------------------------------------------------------------*/

    public int getTripId(){
        Intent intent = getIntent();
        if (intent != null) {
            tripId = intent.getIntExtra("TripId", 0);
            Log.i("ONTHEROAD_MAIN", "* Getting tripId : " + tripId);
        }
        else tripId = 0;
        Log.i("ONTHEROAD_MAIN", "tripId = " + tripId);
        return tripId;
    }

    public float getBudget(int id){
        float budget = 0;
        if(tripDao.getTrip(id) != null) {
            budget = tripDao.getTrip(id).getBudget();
        }
        return budget;
    }

    public float getRemainingBudget(int id){
        float result = 0f;
        result = getBudget(id) - getFullPrice(id);
        if(result > 0){
            return result;
        }
        else{
            return 0;
        }
    }

    public int getDuration(int id){
        int duration = 0;
        if(tripDao.getTrip(id) != null) {
            duration = tripDao.getTrip(id).getDuration();
        }
        return duration;
    }

    public float getCostByDay(int id){
        float budget = 0;
        if(tripDao.getTrip(id) != null) {
            budget = tripDao.getTrip(id).getCostByDay();
        }
        return budget;
    }

    public String getName(long id){
        String name = "";
        if(tripDao.getTrip(id) != null){
            name = tripDao.getTrip(id).getName();
        }
        return name;
    }

    public void getCost(long id){
        if(costDao.getCosts(id) != null){
            int max  = costDao.getCosts(id).size();
            Cost cost;
            for (int i=0;i< max;i++){
                cost = costDao.getCosts(id).get(i);
                Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice(), cost.getTripId()));
            }
        }

    }

    /*public void getTrip() throws InterruptedException {
        Trip trip = LiveDataUtil.getValue(tripDao.getTrip(0));
        String name = trip.getName();
        Log.i("ONTHEROAD_MAIN", String.format(LOG_TRIP, name));
    }*/

    public float getFullPrice(long id){
        float price = 0;
        int max  = costDao.getCosts(id).size();
        Cost cost;
        for (int i=0;i< max;i++){
            cost = costDao.getCosts(id).get(i);
            Log.i("ONTHEROAD_MAIN", String.format(LOG_COST, cost.getId(),cost.getName(), cost.getPrice(), cost.getTripId()));
            price += cost.getPrice();
        }
        return price;
    }

    public float getPrice(int id){
        float price = 0;
            if(costDao.getCategoryCost(id, tripId) != null) {
                List<Cost> costs = costDao.getCategoryCost(id, tripId);
                int max = costs.size();
                for (int i = 0; i < max; i++) {
                    price += costs.get(i).getPrice();
                }
            }
        return price;
    }

    public int getDays(int id) throws ParseException {
        int day = 0;
        if(tripDao.getTrip(id) != null) {
            List<Cost> costs = costDao.getCosts(id);
            Date[] dates = new Date[costs.size()];
            Calendar[] calendars = new Calendar[costs.size()];
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            long min = 0;
            long max = 0;

            for(int i = 0; i < costs.size(); i++){
                dates[i] = formatter.parse(costs.get(i).getDate());
                calendars[i] = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendars[i].setTime(dates[i]);
                if(i == 0){ // initialisation
                    min = calendars[i].getTimeInMillis();
                    max = calendars[i].getTimeInMillis();
                }
                else{ // let's check for min and max dates
                    if(calendars[i].getTimeInMillis() < min){
                        min = calendars[i].getTimeInMillis();
                                            }
                    else if(calendars[i].getTimeInMillis() > max){

                        max = calendars[i].getTimeInMillis();
                    }
                }
            }
            day = (int) ((max-min) / 86400000);
        }
        Log.i("ONTHEROAD_MAIN", String.format(LOG_DAY, day));
        return day;
    }

    /*----------------------------------------------------------------------------------------------
    METHODS
    ----------------------------------------------------------------------------------------------*/
    public void SelectTrip(){
        Intent activity = new Intent(MainActivity.this, TripListActivity.class);
        activity.putExtra("Categories", categories);
        activity.putExtra("TripId", tripId);
        setResult(RESULT_OK, activity);
        startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        if(v == button[0]){ // add
            Intent activity = new Intent(MainActivity.this, CostTypeActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[1]){ // list
            Intent activity = new Intent(MainActivity.this, CostListActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }
        else if(v == button[2]){ // modify
            Intent activity = new Intent(MainActivity.this, TripListActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
        }

    }
}