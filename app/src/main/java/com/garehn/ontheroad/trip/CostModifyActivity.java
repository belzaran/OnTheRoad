package com.garehn.ontheroad.trip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.garehn.ontheroad.R;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CostModifyActivity extends CostBaseActivity implements View.OnClickListener {

    private static final int GAME_ACTIVITY_REQUEST_CODE = 12;

    int costId;
    Cost cost;
    LocalDate date;

    // GRAPHICS
    protected EditText[] editText = new EditText[3];
    protected Button button;
    protected Spinner spinner;
    protected DatePicker datePicker;

    // STRINGS
    protected static String TEXT_PRICE = "%s €";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_modify);
        createCategories();
        createDatabase();
        createGraphics();
    }


    public void createGraphics() {

        // EDIT TEXT
        editText[0] = findViewById(R.id.cost_modify_editText0);
        editText[1] = findViewById(R.id.cost_modify_editText1);
        editText[2] = findViewById(R.id.cost_modify_editText2);

        editText[0].setText(costDao.getCosts(0).get((int) costId).getName());
        float price = costDao.getCosts(0).get((int) costId).getPrice();
        editText[1].setText(""+ price);
//      editText[2].setText(0); // to change when multiples trips will be available

        // BUTTON
        button = findViewById(R.id.cost_modify_button_add);
        button.setOnClickListener(this);

        // SPINNER
        spinner = findViewById(R.id.cost_modify_spinner);
        List list = new ArrayList();
        for (int i = 0; i < categories.length; i++) {
            list.add(categories[i]);
        }

        int categoryId = costDao.getCosts(0).get(costId).getCategoryId();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(categoryId);

        // DATE
        datePicker = findViewById(R.id.cost_modify_date_picker);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        String dateString = costDao.getCosts(0).get((int) costId).getDate();
        date = LocalDate.parse(dateString, formatter);
        datePicker.updateDate(date.getYear(), date.getMonthValue() - 1 , date.getDayOfMonth());
    }


    public void createCategories() {
        Intent intent = getIntent();
        if (intent != null) {
            costId = intent.getIntExtra("id", 1);
            categories = intent.getStringArrayExtra("Categories");
        }
    }

    public String formatPrice(double p) {
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        f.setMinimumFractionDigits(2);
        return f.format(p);
    }

    @Override
    public void onClick(View v) {
        if (v == button) {
            String name = editText[0].getText().toString();
            float price = Float.valueOf(editText[1].getText().toString());
            long tripId = Long.valueOf(editText[2].getText().toString());
            int categoryId = (int) spinner.getSelectedItemId();
            date = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
            String formattedDate = date.format(DateTimeFormatter.ofPattern(dateFormat));
            Cost cost = costDao.getCosts(0).get(costId);
            cost.setName(name);
            cost.setPrice(price);
            cost.setCategoryId(categoryId);
            cost.setDate(formattedDate);
            costDao.updateCost(cost);
            finish();
        }
    }
}