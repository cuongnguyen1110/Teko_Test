package com.example.myapplication;

import android.app.Activity;
import android.util.Log;

public class ProductCtrl
{
    private Activity mMainActivity;
    ProductCtrl(Activity main)
    {
        mMainActivity = main;
    }
    public void OnProductClicked(Product p)
    {
        Log.i("Cuong5","Clicked product: " + p.getName());
        MainActivity main = (MainActivity)(mMainActivity);
        main.GetProductDetail(p);
    }
}
