package com.example.myapplication;

import org.json.JSONObject;

public class Product {

    public String mSKU;
    public String mName;
    public int mFinalPrice;
    public int mPromoPrice;
    public String mImageURL;

    public String mCode;
    public int mStatus;
    public Product()
    {
        mSKU = "";
        mName = "";
        mFinalPrice = -1;
        mPromoPrice = -1;
        mImageURL = "";
        mCode = mSKU;
        mStatus = 1;
    }
    public Product(String sku, String n, int price)
    {
        mSKU = sku;
        mName = n;
        mFinalPrice = price;

    }

    public void Construct(JSONObject obj)
    {
        try {
            mSKU = obj.getString("sku");
        }
        catch (Exception e) {}

        try
        {
            mName = obj.getString("name");
        }
        catch (Exception e){};

        try{
            mFinalPrice = obj.getJSONObject("price").getInt("sellPrice");
        }
        catch (Exception e) { }

        try{
            mPromoPrice = obj.getJSONObject("price").getInt("supplierSalePrice");
        }
        catch (Exception e) { }

        try{
            if(obj.getJSONArray("images").length() > 0 )
            {
                mImageURL = obj.getJSONArray("images").getJSONObject(0).getString("url");
            }
        }
        catch (Exception e) { }

    }

    public void ConstructDetail(JSONObject obj)
    {
        try {
            mSKU = obj.getString("sku");
        }
        catch (Exception e) {}

        try
        {
            mName = obj.getString("name");
        }
        catch (Exception e){};

        try{
            mFinalPrice = obj.getJSONObject("price").getInt("sellPrice");
        }
        catch (Exception e) { }

        try{
            mPromoPrice = obj.getJSONObject("price").getInt("supplierSalePrice");
        }
        catch (Exception e) { }

        try{
            mStatus = obj.getJSONObject("status").getBoolean("publish")?1:0;
        }
        catch (Exception e) { }

        try{
            mCode = "TEST_CODE_9999"; // can not find in response
        }
        catch (Exception e) { }

        try{
            if(obj.getJSONArray("images").length() > 0 )
            {
                mImageURL = obj.getJSONArray("images").getJSONObject(0).getString("url");
            }
        }
        catch (Exception e) { }
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
