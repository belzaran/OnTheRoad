package com.garehn.ontheroad.graphics;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.trip.Cost;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class CostCellAdapter extends BaseAdapter {

    //FOR DATA
        private List<Cost> costs;
        private Context context;
        private LayoutInflater layoutInflater;

        // CONSTRUCTOR
        public CostCellAdapter(Context context, List<Cost> costs){
            this.context = context;
            this.costs = costs;

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
                textPrice.setText(costs.get(position).getPrice() + "€");

        return convertView;
    }

    private Context getContext() {
            return this.context;
    }
}
