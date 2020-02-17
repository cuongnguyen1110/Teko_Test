package com.example.myapplication;

public class Product {

    private String name;
    private String  price;
    Product(String n, String p)
    {
        name = n;
        price =p;
    }

    public String getName()
    {
        return name;
    }
    public String getPrice()
    {
        return price;
    }
}
