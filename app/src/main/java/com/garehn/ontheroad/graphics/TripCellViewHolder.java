package com.garehn.ontheroad.graphics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.Trip;

public class TripCellViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;

    public TripCellViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = name.findViewById(R.id.trip_cell_name);
    }

    public void updateWithTrips(Trip trip){
        this.name.setText(trip.getName());
    }

}
