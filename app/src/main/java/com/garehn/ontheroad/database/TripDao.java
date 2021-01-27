package com.garehn.ontheroad.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.garehn.ontheroad.trip.Trip;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTrip (Trip trip);

    @Query("SELECT * FROM Trip WHERE id = :tripId")
    Trip getTrip(long tripId);

}
