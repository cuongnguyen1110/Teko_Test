package com.example.myapplication;

public class Product {

    public String mSKU;
    public String mName;
    public int mFinalPrice;
    public int mPromoPrice;
    public String mImageURL;
    public Product(String sku, String n, int price)
    {
        mSKU = sku;
        mName = n;
        mFinalPrice = price;

    }

    public void SetPromoPrice(int p)
    {
        mPromoPrice =p;
    }
    public void SetImageURL(String url)
    {
        mImageURL = url;
    }

    public String getName()
    {
        return mName;
    }
    public int getPrice()
    {
        return mFinalPrice;
    }
}
