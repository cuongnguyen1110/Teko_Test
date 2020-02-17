package com.example.myapplication;

import android.util.Log;

public class ProductCtrl
{
    public void OnProductClicked(Product p)
    {
        Log.i("Cuong5","Clicked product: " + p.getName());
    }
}
