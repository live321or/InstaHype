package com.likesmm.instahype;

public class Order {
    int id;
    String date;
    double price;
    double count;
    String type;
    String link;
    int type1;

    public Order(int id, String date, double count, double price, int type, String link) {
        this.id = id;
        this.type1 = type;
        this.count = count;
        this.price = price;
        this.link = link;
        this.date = date;
    }

    public Order(int id, String date, double count, double price, String type, String link) {
        this.id = id;
        this.type = type;
        this.count = count;
        this.price = price;
        this.link = link;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getType1() {
        return type1;
    }

    public String getType() {
        return type;
    }

    public double getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }
}
