package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.garehn.ontheroad.MainActivity;
import com.garehn.ontheroad.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CostAddActivity extends CostBaseActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;

    // GRAPHICS
    protected EditText[] editText = new EditText[3];
    protected Button button;
    protected DatePicker datePicker;
    protected ImageView image;
    private Integer[] images = {R.drawable.icon_food, R.drawable.icon_transport, R.drawable.icon_accommodation, R.drawable.icon_activities, R.drawable.icon_gift, R.drawable.icon_other, R.drawable.icon_going};

    // STRINGS
    /*protected static String LOG_DATABASE = "Creating database";
    protected static String LOG_TRIP = "Trip n°%s : %s";
    protected static String LOG_COST = "Cost n°%s : %s - %s €";*/

    protected int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        createCategories();
        getCategoryId();
        createDatabase();
        getTripId();
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.cost_editText0);
        editText[1] = findViewById(R.id.cost_editText1);
        editText[2] = findViewById(R.id.cost_editText2);

        int imageId = getCategoryId();
        image = findViewById(R.id.cost_icon);
        image.setImageResource(images[imageId]);
        //image.setImageDrawable(icon_accommodation);

        // BUTTON
        button = findViewById(R.id.cost_button_add);
        button.setOnClickListener(this);

        // DATE
        datePicker = findViewById(R.id.cost_date_picker);
        datePicker.getYear();
        datePicker.getMonth();
        datePicker.getDayOfMonth();
    }

    public int getCategoryId(){
        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getIntExtra("CategoryId", 0);
        }
        return categoryId;
    }

    public float getPrice(){
        float priceUnity = Float.valueOf(editText[1].getText().toString());
        float priceCents = Float.valueOf(editText[2].getText().toString());
        float price = (priceUnity * 100 + priceCents);
        price = price/100;
        return price;
    }

    public boolean checkValues(){
        boolean check = false;

        if(TextUtils.isEmpty(editText[0].getText().toString())){
            editText[0].setError(getString(R.string.error_name));
        }
        else if(TextUtils.isEmpty(editText[1].getText().toString())){
            editText[1].setError(getString(R.string.error_empty));
        }
        else if(TextUtils.isEmpty(editText[2].getText().toString())){
            editText[2].setError(getString(R.string.error_empty));
        }

        else {
            float priceCents = Float.valueOf(editText[2].getText().toString());
            if (priceCents < 100 && priceCents >= 0) {
                check = true;
            } else {
                editText[2].setError(getString(R.string.error_cents));
                editText[2].setText(null);
            }
        }

        return check;
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            if(checkValues()) {
                String name = editText[0].getText().toString();
                //float price = Float.valueOf(editText[1].getText().toString());
                float price = getPrice();
                //long tripId = Long.valueOf(editText[2].getText().toString()); // don't manage this now
                //int categoryId = (int) spinner.getSelectedItemId();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                //date = date.of(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()); for Android 26
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                Date date = calendar.getTime();
                String formattedDate = formatter.format(date);
                Cost cost = new Cost(name, price, tripId, categoryId, formattedDate);
                costDao.insertCost(cost);
                costDao.updateCost(cost);

                Intent activity = new Intent(CostAddActivity.this, MainActivity.class);
                activity.putExtra("Categories", categories);
                activity.putExtra("CategoryId", 5);
                activity.putExtra("TripId", tripId);
                setResult(RESULT_OK, activity);
                startActivityForResult(activity, GAME_ACTIVITY_REQUEST_CODE);
                finish();
            }
        }
    }
}
