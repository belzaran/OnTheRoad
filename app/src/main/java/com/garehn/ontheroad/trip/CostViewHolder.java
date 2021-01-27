package com.garehn.ontheroad.trip;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.garehn.ontheroad.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     // FOR DATA
    private WeakReference<CostAdapter.Listener> callbackWeakRef;

    public CostViewHolder(View costView){
        super(costView);
        ButterKnife.bind(this, costView);
    }


    public void updateWithCost(Cost cost, CostAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<CostAdapter.Listener>(callback);
        //this.textview.setText(cost.getName());
    }

    @Override
    public void onClick(View v) {
        CostAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null){
            callback.onClickDeleteButton(getAdapterPosition());
        }

    }
}
