package com.example.phones.model;

public class Phones {
    String name;
    String descriptions;
    int image;
    String date;
    public Phones(String name, String descriptions, int image, String date){
        this.name = name;
        this.descriptions = descriptions;
        this.image = image;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
