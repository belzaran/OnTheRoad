package com.garehn.ontheroad.injections;

import android.content.Context;

import com.garehn.ontheroad.database.TripDatabase;
import com.garehn.ontheroad.repositories.CostDataRepository;
import com.garehn.ontheroad.repositories.TripDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TripDataRepository provideTripDataSource (Context context){
        TripDatabase database = TripDatabase.getInstance(context);
        return new TripDataRepository(database.tripDao());
    }

    public static CostDataRepository provideCostDataSource(Context context){
        TripDatabase database = TripDatabase.getInstance(context);
        return new CostDataRepository(database.costDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        CostDataRepository dataSourceCost = provideCostDataSource(context);
        TripDataRepository dataSourceTrip = provideTripDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceCost, dataSourceTrip, executor);

    }

}
