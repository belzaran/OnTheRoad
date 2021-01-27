package com.garehn.ontheroad.trip;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.garehn.ontheroad.repositories.CostDataRepository;
import com.garehn.ontheroad.repositories.TripDataRepository;
import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.Trip;

import java.util.List;
import java.util.concurrent.Executor;

public class CostViewModel extends ViewModel {

    // Repositories
    private final CostDataRepository costDataSource;
    private final TripDataRepository tripDataSource;
    private final Executor executor;

    // Data
    @Nullable
    private LiveData<Trip> currentTrip;

    public CostViewModel(CostDataRepository costDataSource, TripDataRepository tripDataSource, Executor executor){
        this.costDataSource = costDataSource;
        this.tripDataSource = tripDataSource;
        this.executor = executor;
    }

    /*public void init(long tripId){
        if(this.currentTrip != null){
            return;
        }
        currentTrip = tripDataSource.getTrip(tripId);
    }

    // FOR TRIP

    public LiveData<Trip> getTrip(long tripId) { return this.currentTrip;}

    // FOR COST

    public LiveData<List<Cost>> getCosts(long tripId){return costDataSource.getCosts(tripId);}

    public void createCost(Cost cost){
        executor.execute(() -> {
            costDataSource.createCost(cost);
        });
    }

    public void deleteCost(long costId){
        executor.execute(()-> {
            costDataSource.deleteCost(costId);
        });
    }

    public void updateCost(Cost cost){
        executor.execute(()-> {
            costDataSource.updateCost(cost);
        });
    }*/

}
