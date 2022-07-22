package com.garehn.ontheroad.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTrip (Trip trip);

    @Query("SELECT * FROM Trip WHERE id = :tripId")
    Trip getTrip(long tripId);

    @Query("SELECT * FROM Trip")
    List<Trip> getTrips();

    @Update
    int updateTrip(Trip trip);

    @Query("SELECT COUNT(*) FROM Trip")
    int getCount();

    @Query("DELETE FROM Trip WHERE id = :tripId")
    int deleteTrip(long tripId);
}
