package com.garehn.ontheroad.graphics;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.Trip;

import java.text.DecimalFormat;
import java.util.List;

public class TripCellAdapter extends BaseAdapter {

    //FOR DATA
        private List<Trip> trips;
        private Context context;
        private LayoutInflater layoutInflater;

        // CONSTRUCTOR
        public TripCellAdapter(Context context, List<Trip> trips){
            this.context = context;
            this.trips = trips;
        }


    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int position) {
        return trips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.trip_cell, null);

            TextView textName = convertView.findViewById(R.id.trip_cell_name);
            textName.setText(trips.get(position).getId() + " | " + trips.get(position).getName());

        return convertView;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    private Context getContext() {
            return this.context;
    }

}
