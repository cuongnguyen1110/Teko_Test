package com.example.myapplication;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.myapplication.databinding.ProductLayoutBinding;

import androidx.databinding.DataBindingUtil;

public class SearchView
{
    private LinearLayout mRoot;
    LayoutInflater mInflater;
    ProductCtrl mProductCtrl;
    SearchView(LinearLayout root,LayoutInflater inflater)
    {
        mRoot =root;
        mInflater = inflater;
        mProductCtrl = new ProductCtrl();
    }

    public void AddProduct(Product p)
    {
        ProductLayoutBinding binding1 = DataBindingUtil.inflate(mInflater, R.layout.product_layout,mRoot,true);
        binding1.setProduct(p);
        binding1.setClickedHandler(mProductCtrl);
    }

    public void ClearView()
    {
        mRoot.removeAllViews();
    }
}
