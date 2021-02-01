package com.garehn.ontheroad.graphics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garehn.ontheroad.R;
import com.garehn.ontheroad.trip.Cost;

public class CostCellViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;

    public CostCellViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = name.findViewById(R.id.cost_cell_name);
        this.price = price.findViewById(R.id.cost_cell_price);
    }

    public void updateWithCosts(Cost cost){
        this.name.setText(cost.getName());
        this.price.setText(cost.getPrice() + "€");
    }

}
