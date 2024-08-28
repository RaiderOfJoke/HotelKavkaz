package com.example.hotelkavkaz.Model;

public class Booking {
    private String type, guestNumber, dateIn, dateOut, pool, spa, hotelTitle, hotelID, bookID, hotelImage, Price;

    public Booking() {
    }

    public Booking(String type, String guestNumber, String dateIn, String dateOut,String pool, String spa, String hotelID, String hotelTitle, String bookID, String hotelImage, String Price) {
        this.type = type;
        this.guestNumber = guestNumber;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.pool = pool;
        this.spa = spa;
        this.hotelID = hotelID;
        this.hotelTitle = hotelTitle;
        this.bookID = bookID;
        this.hotelImage = hotelImage;
        this.Price = Price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(String guestNumber) {
        this.guestNumber = guestNumber;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getSpa() {
        return spa;
    }

    public void setSpa(String spa) {
        this.spa = spa;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public void setHotelTitle(String hotelTitle) {
        this.hotelTitle = hotelTitle;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = Price;
    }
}
