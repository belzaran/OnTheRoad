package com.garehn.ontheroad.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.garehn.ontheroad.trip.Cost;
import com.garehn.ontheroad.trip.Trip;

import java.util.Currency;


@Database(entities = {Cost.class, Trip.class}, version = 1, exportSchema = false)
public abstract class TripDatabase extends RoomDatabase {

    //    SINGLETON
    public static volatile TripDatabase INSTANCE;

    // DAO
    public abstract CostDao costDao();
    public abstract TripDao tripDao();

    /*----------------------------------------------------------------------------------------------
    INSTANCE
    ----------------------------------------------------------------------------------------------*/
    public static TripDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TripDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TripDatabase.class, "MyDatabase.db").addCallback(prepopulateDatabase()).build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id",1);
                contentValues.put("name", "Paris");
                contentValues.put("budget", 0);
                contentValues.put("tripId", 1);
                db.insert("Trip", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

}
