package com.garehn.ontheroad.trip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.garehn.ontheroad.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CostActivity extends CostBaseActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;

    // GRAPHICS
    protected EditText[] editText = new EditText[3];
    protected Button button;
    protected Spinner spinner;
    protected DatePicker datePicker;

    // STRINGS
    protected static String LOG_DATABASE = "Creating database";
    protected static String LOG_TRIP = "Trip n°%s : %s";
    protected static String LOG_COST = "Cost n°%s : %s - %s €";

    // OTHERS
    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        createCategories();
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
        for(int i =0 ; i<categories.length; i++){
            list.add(categories[i]);
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

    @Override
    public void onClick(View v) {
        if(v == button){
            String name = editText[0].getText().toString();
            float price = Float.valueOf(editText[1].getText().toString());
            long tripId = Long.valueOf(editText[2].getText().toString());
            int categoryId = (int) spinner.getSelectedItemId();
            date = LocalDate.of(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth());
            String formattedDate = date.format(DateTimeFormatter.ofPattern(dateFormat));
            Cost cost = new Cost(name, price, tripId, categoryId, formattedDate);
            costDao.insertCost(cost);
            finish();
        }
    }
}
