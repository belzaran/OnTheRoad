package com.garehn.ontheroad.repositories;

import androidx.lifecycle.LiveData;

import com.garehn.ontheroad.database.TripDao;
import com.garehn.ontheroad.trip.Trip;

public class TripDataRepository {

    private final TripDao tripDao;

    public TripDataRepository(TripDao tripDao){
        this.tripDao = tripDao;
    }

    // GET TRIP
    public Trip getTrip(long tripId){
        return this.tripDao.getTrip(tripId);
    }

}
