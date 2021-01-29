package com.garehn.ontheroad.trip;

public enum Category {
    FOOD ("Food", 0),
    TRANSPORT("Transport", 1),
    GIFT ("Gift", 2),
    ACCOMMODATION("Accommodation", 3);


    private String name = "";
    private int id;
    private int max = 4;

    //Constructor
    Category(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String toString(){
        return name;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
