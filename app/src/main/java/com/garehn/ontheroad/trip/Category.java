package com.garehn.ontheroad.trip;

public enum Category {
    FOOD ("Food"),
    TRANSPORT("Transport"),
    GIFT ("Gift"),
    ACCOMMODATION("Accommodation");


    private String name = "";

    //Constructor
    Category(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
