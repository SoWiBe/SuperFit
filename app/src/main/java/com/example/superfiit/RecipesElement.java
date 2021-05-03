package com.example.superfiit;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipesElement implements Serializable {
    private String name;
    private String kcal;
    private String protein;
    private String fat;
    private String cards;
    private String image;
    private ArrayList<String> ingredients;

    public RecipesElement(){}
    public RecipesElement(String name, String kcal, String protein, String fat, String cards, String image) {
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.cards = cards;
        this.image = image;
    }

    public RecipesElement(String name, String kcal, String protein, String fat, String cards, String image, ArrayList<String> ingredients) {
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.cards = cards;
        this.image = image;
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }
}
