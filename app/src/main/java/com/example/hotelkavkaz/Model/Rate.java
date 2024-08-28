package com.example.hotelkavkaz.Model;

public class Rate {
    String stars, textRate, title, username;

    public Rate(String stars, String textRate, String title, String username) {
        this.stars = stars;
        this.textRate = textRate;
        this.title = title;
        this.username = username;
    }
    public Rate()
    {

    }
    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getTextRate() {
        return textRate;
    }

    public void setTextRate(String textRate) {
        this.textRate = textRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
