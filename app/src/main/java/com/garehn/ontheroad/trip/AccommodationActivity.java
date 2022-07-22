package com.garehn.ontheroad.trip;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.garehn.ontheroad.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AccommodationActivity extends CostBaseActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 14;

    // GRAPHICS
    protected EditText[] editText = new EditText[4];
    protected Button button;
    protected DatePicker datePicker;
    protected ImageView image;
    private Integer[] images = {R.drawable.icon_food, R.drawable.icon_transport, R.drawable.icon_accommodation, R.drawable.icon_activities, R.drawable.icon_gift, R.drawable.icon_other};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        createCategories();
        createDatabase();
        createGraphics();
    }

    public void createGraphics(){
        editText[0] = findViewById(R.id.accommodation_editText0);
        editText[1] = findViewById(R.id.accommodation_editText1);
        editText[2] = findViewById(R.id.accommodation_editText2);
        editText[3] = findViewById(R.id.accommodation_editText3);

        image = findViewById(R.id.accommodation_icon);
        image.setImageResource(images[2]); // 2 is accommodation

        button = findViewById(R.id.accommodation_button_add);
        button.setOnClickListener(this);

        datePicker = findViewById(R.id.accommodation_date_picker);
        datePicker.getYear();
        datePicker.getMonth();
        datePicker.getDayOfMonth();

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

        else if(TextUtils.isEmpty(editText[3].getText().toString())){
            editText[3].setError(getString(R.string.error_empty));
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

    public int getNights(){
        int nights = Integer.valueOf(editText[3].getText().toString());
        return nights;
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            if(checkValues()) {
                String name = editText[0].getText().toString();
                float price = getPrice();
                long tripId = 0;
                //int categoryId = (int) spinner.getSelectedItemId();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                //date = date.of(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()); for Android 26
                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                Date date = calendar.getTime();
                String formattedDate = formatter.format(date);
                Cost cost = new Cost(name, price, tripId, 2, formattedDate); // separate in 3 costs ?
                costDao.insertCost(cost);
                finish();
            }
        }

    }
}