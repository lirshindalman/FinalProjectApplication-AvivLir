package com.example.recipeapp;

import java.util.ArrayList;

public class FoodData {

    private String itemName;
    private ArrayList<String> description;
    private ArrayList<String> ingredient;
    private String itemImage;
    private String key;
    private int timer;

    public FoodData() {
    }
    public FoodData(String itemName, String itemImage,ArrayList<String> ingredients,ArrayList<String> description, int timer) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.ingredient = ingredients;
        this.description = description;
        this.timer = timer;
    }

    public String getItemName() {
        return itemName;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public ArrayList<String> getIngredient() {
        return ingredient;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getKey() { return key; }

    public int getTimer() { return timer; }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public void setIngredient(ArrayList<String> ingredient) {
        this.ingredient = ingredient;
    }
}
