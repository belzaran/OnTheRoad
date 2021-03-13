package com.garehn.ontheroad.trip;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Currency;

@Entity
public class Trip {

    /*----------------------------------------------------------------------------------------------
    VARIABLES
    ----------------------------------------------------------------------------------------------*/
    @PrimaryKey
    private long id;
    private String name;
    private int budget;
    private boolean isSelected;
    private int duration;

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTOR
    ----------------------------------------------------------------------------------------------*/

    public Trip(){}

    public Trip(long i, String n, int b, int d){
        id = i;
        name = n;
        budget = b;
        duration = d;
        isSelected = false;
    }

    /*----------------------------------------------------------------------------------------------
    GETTERS & SETTERS
    ----------------------------------------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getCostByDay(){
        float f;
        if (duration == 0){
            f = 0;
        }
        else{
            f = budget/duration;
        }
        return f;
    }


}
