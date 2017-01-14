package com.example.integration.model;

/**
 * Created by Mikhail on 14.01.2017.
 */
public class Order {

    private int id;
    private String name;
    private int count;

    public Order() {
    }

    public Order(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
