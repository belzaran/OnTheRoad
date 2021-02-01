package com.garehn.ontheroad.trip;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Trip.class,
        parentColumns = "id",
        childColumns = "tripId"))
public class Cost {

    /*----------------------------------------------------------------------------------------------
    VARIABLES
    ----------------------------------------------------------------------------------------------*/
    @PrimaryKey(autoGenerate = true)
    private long id;
    private float price;
    private String name;
    private long tripId;
    private int categoryId;
    private boolean isSelected;
    private static String LOG_CREATION = "Creating COST %s - %s € - type : %s";


/*----------------------------------------------------------------------------------------------
    CONSTRUCTOR
    ----------------------------------------------------------------------------------------------*/

    public Cost(){    }

    public Cost(String n, float p, long l, int cId){
        this.name = n;
        this.price = p;
        this.tripId = l;
        this.categoryId = cId;
        this.isSelected = false;
        Log.i("ONTHEROAD_COST", String.format(LOG_CREATION, name, price, categoryId));
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString(){
        String description = name + " - " + price + "€";
        return description;
    }
}
