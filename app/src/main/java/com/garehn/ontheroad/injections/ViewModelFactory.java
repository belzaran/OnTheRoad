package com.garehn.ontheroad.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.garehn.ontheroad.repositories.CostDataRepository;
import com.garehn.ontheroad.repositories.TripDataRepository;
import com.garehn.ontheroad.trip.CostViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    // Repositories
    private final CostDataRepository costDataSource;
    private final TripDataRepository tripDataSource;
    private final Executor executor;

    public ViewModelFactory(CostDataRepository costDataSource, TripDataRepository tripDataSource, Executor executor) {
        this.costDataSource = costDataSource;
        this.tripDataSource = tripDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(CostViewModel.class)){
            return (T) new CostViewModel(costDataSource, tripDataSource, executor);
        }

        // if neeed, add here TripViewModel

        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
