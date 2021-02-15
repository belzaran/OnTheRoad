package com.garehn.ontheroad.graphics;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.trip.Cost;

import java.text.DecimalFormat;
import java.util.List;

public class CostCellAdapter extends BaseAdapter {

    //FOR DATA
        private List<Cost> costs;
        private Context context;
        private LayoutInflater layoutInflater;
        private Integer[] images;

        // CONSTRUCTOR
        public CostCellAdapter(Context context, List<Cost> costs, Integer[] images){
            this.context = context;
            this.costs = costs;
            this.images = images;
        }


    @Override
    public int getCount() {
        return costs.size();
    }

    @Override
    public Object getItem(int position) {
        return costs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.cost_cell, null);

            TextView textName = convertView.findViewById(R.id.cost_cell_name);
            textName.setText(costs.get(position).getName());

            TextView textPrice = convertView.findViewById(R.id.cost_cell_price);
            textPrice.setText(formatPrice(costs.get(position).getPrice()) + "€");

            TextView textDate = convertView.findViewById(R.id.cost_cell_date);
            textDate.setText(costs.get(position).getDate());

            ImageView imageIcon = convertView.findViewById(R.id.cost_cell_icon);
            //imageIcon.setImageResource(costs.get(position).getCategoryId());
            int imageIconId = costs.get(position).getCategoryId();
            imageIcon.setImageResource(images[imageIconId]);

        return convertView;
    }

    public String formatPrice(double p){
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        f.setMinimumFractionDigits(2);
        return f.format(p);
    }

    public void setCosts(List<Cost> costs) {
        this.costs = costs;
    }

    public List<Cost> getCosts() {
        return costs;
    }

    private Context getContext() {
            return this.context;
    }

}
