package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ProductLayoutBinding;

import androidx.databinding.DataBindingUtil;

public class SearchView
{
    private Activity mMainActivity;
    private LinearLayout mRoot;
    LayoutInflater mInflater;
    ProductCtrl mProductCtrl;
    SearchView(Activity activity, LinearLayout root,LayoutInflater inflater)
    {
        mMainActivity = activity;
        mRoot =root;
        mInflater = inflater;
        mProductCtrl = new ProductCtrl();
    }

    public void AddProduct(Product p)
    {
        ProductLayoutBinding binding1 = DataBindingUtil.inflate(mInflater, R.layout.product_layout,mRoot,true);
        binding1.setProduct(p);
        binding1.setClickedHandler(mProductCtrl);

        if(p.mPromoPrice > 0)
        {
            TextView txt = (TextView) binding1.getRoot().findViewById(R.id.productPrice);
            txt.setPaintFlags(txt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            txt.setTextColor(Color.GRAY);
            txt.setTextSize(13);
        }
        ImageView imageView = (ImageView) binding1.getRoot().findViewById(R.id.producImage);

        Glide.with(mMainActivity).load(p.mImageURL).into(imageView);

        Log.i("Cuong", "url for Image: " + p.mImageURL);
    }

    public void ClearView()
    {
        mRoot.removeAllViews();
    }
}
