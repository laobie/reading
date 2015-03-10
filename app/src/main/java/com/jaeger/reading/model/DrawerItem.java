package com.jaeger.reading.model;

public class DrawerItem {
    private int itemIconId;
    private String itemName;
    public DrawerItem(int itemIconId, String itemName){
        this.itemIconId = itemIconId;
        this.itemName = itemName;
    }

    public int getItemIconId() {
        return itemIconId;
    }

    public String getItemName(){
        return itemName;
    }
}
