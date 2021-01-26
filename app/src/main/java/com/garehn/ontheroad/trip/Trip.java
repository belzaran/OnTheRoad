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

    /*----------------------------------------------------------------------------------------------
    CONSTRUCTOR
    ----------------------------------------------------------------------------------------------*/

    public Trip(){}

    public Trip(long i, String n, int b){
        id = i;
        name = n;
        budget = b;
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


}
