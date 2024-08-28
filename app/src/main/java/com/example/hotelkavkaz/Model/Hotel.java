package com.example.hotelkavkaz.Model;

public class Hotel {
    String image, title, text, price, id;

    public Hotel(String image, String title,String price, String text, String id) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.text = text;
        this.id = id;
    }

    public Hotel(){}


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
