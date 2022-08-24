package com.garehn.ontheroad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.garehn.ontheroad.database.CostDao;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.CostBaseActivity;
import com.garehn.ontheroad.trip.CostListActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatsActivity extends CostBaseActivity implements View.OnClickListener {

    // GRAPHICS
    protected TextView[] text = new TextView[7];
    protected TextView[] textResult = new TextView[7];
    protected ImageView button;

    int duration = 1;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        createCategories();
        createDatabase();
        getTripId();
        try {
            createGraphics();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createGraphics() throws ParseException {

        textResult[0] = findViewById(R.id.stats_0_text);
        textResult[1] = findViewById(R.id.stats_1_text);
        textResult[2] = findViewById(R.id.stats_2_text);
        textResult[3] = findViewById(R.id.stats_3_text);
        textResult[4] = findViewById(R.id.stats_4_text);
        textResult[5] = findViewById(R.id.stats_5_text);
        textResult[6] = findViewById(R.id.stats_6_text);

        text[0] = findViewById(R.id.stats_0);
        text[1] = findViewById(R.id.stats_1);
        text[2] = findViewById(R.id.stats_2);
        text[3] = findViewById(R.id.stats_3);
        text[4] = findViewById(R.id.stats_4);
        text[5] = findViewById(R.id.stats_5);
        text[6] = findViewById(R.id.stats_6);

        // Categories = {"Food", "Transport", "Accommodation", "Activities", "Gift", "Others", "0AndComing"};

        if (costDao.getCosts(tripId).size() > 0) {

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);

            for (int i = 0; i < categories.length; i++) {
                duration = getDuration(tripId, i);
                textResult[i].setText(df.format(getCategoryTotalCost(i) / duration) + " €/day");
                text[i].setText(categories[i]);
            }
        }
        else {
            for (int i = 0; i < categories.length; i++) {
                textResult[i].setText(0 + " €/day");
                text[i].setText(categories[i]);
            }
        }


            /* obsolete
            duration = getDuration(tripId);

            text[0].setText(df.format(getCategoryTotalCost(6) / duration) + " €/day");
            text[1].setText(df.format(getCategoryTotalCost(1) / duration) + " €/day");
            text[2].setText(df.format(getCategoryTotalCost(0) / duration) + " €/day");
            text[3].setText(df.format(getCategoryTotalCost(2) / duration) + " €/day");
            text[4].setText(df.format(getCategoryTotalCost(3) / duration) + " €/day");
            text[5].setText(df.format(getCategoryTotalCost(4) / duration) + " €/day");
            text[6].setText(df.format(getCategoryTotalCost(5) / duration) + " €/day");
        }
        else {
            text[0].setText(0 + " €/day");
            text[1].setText(0 + " €/day");
            text[2].setText(0 + " €/day");
            text[3].setText(0 + " €/day");
            text[4].setText(0 + " €/day");
            text[5].setText(0 + " €/day");
            text[6].setText(0 + " €/day");
        }
        */

            button = findViewById(R.id.stats_return);
            button.setOnClickListener(this);


        }


    public float getCategoryTotalCost(int id) {
        float total = 0f;

        List<Cost> costs = costDao.getCategoryCost(id, tripId);

        for (int i = 0; i < costs.size(); i++) {
            total += costs.get(i).getPrice();
        }

        return total;
    }

    public int getDuration(int id, int categoryId) throws ParseException {

        List<Cost> costs = costDao.getCosts(id);
        String sDate = costs.get(0).getDate();
        Date date = new SimpleDateFormat("dd MMM yyyy").parse(sDate);
        Date maxDate = date;
        Date minDate = date;

        for (int i = 0; i < costs.size(); i++) {
            if (costs.get(i).getCategoryId() != 6) {
                String dateTestString = costs.get(i).getDate();
                Date dateTest = new SimpleDateFormat("dd MMM yyyy").parse(dateTestString);

                if (dateTest.before(minDate)) {
                    minDate = dateTest;
                }

                if (dateTest.after(maxDate)) {
                    maxDate = dateTest;
                }
            }
        }
        Log.i("ONTHEROAD_STATS", "DATES : Min = " + minDate + " | Max = " + maxDate + " | Date = " + date);
        long difference = maxDate.getTime() - minDate.getTime(); // difference in milliseconds
        duration = (int) (difference / (1000 * 60 * 60 * 24)) + 1;
        Log.i("ONTHEROAD_STATS", "DURATION = " + duration);
        // duration can't be equal to zero
        if (duration == 0) {
            duration = 1;
        }
        return duration;
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            Intent activity = new Intent(StatsActivity.this, MainActivity.class);
            activity.putExtra("Categories", categories);
            activity.putExtra("TripId", tripId);
            setResult(RESULT_OK, activity);
            startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
            finish();
        }
    }
}