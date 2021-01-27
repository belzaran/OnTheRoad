package com.garehn.ontheroad.trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.garehn.ontheroad.R;

import java.util.ArrayList;
import java.util.List;

public class CostAdapter extends RecyclerView.Adapter<CostViewHolder> {

    // CALLBACK
    public interface Listener{
        void onClickDeleteButton(int position);
    }
    private final Listener callback;

    // FOR DATA
    private List<Cost> costs;

    // CONSTRUCTOR
    public CostAdapter(Listener callback){
        this.costs = new ArrayList<>();
        this.callback = callback;
    }

    @Override
    public CostViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_cost, parent, false);

        return new CostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CostViewHolder viewHolder, int position){
        viewHolder.updateWithCost(this.costs.get(position), this.callback);
    }

    @Override
    public int getItemCount(){
        return this.costs.size();
    }

    public Cost getCost(int position){
        return this.costs.get(position);
    }

    public void updateData(List<Cost> costs){
        this.costs = costs;
        this.notifyDataSetChanged();
    }


}
