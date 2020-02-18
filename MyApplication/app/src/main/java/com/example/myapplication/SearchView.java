package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.databinding.ProductLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class SearchView
{
    private Activity mMainActivity;
    private LinearLayout mRoot;
    LayoutInflater mInflater;
    ProductCtrl mProductCtrl;
    ArrayList<ProductLayoutBinding> mBindingList;
    SearchView(Activity activity, LinearLayout root,LayoutInflater inflater)
    {
        mMainActivity = activity;
        mRoot =root;
        mInflater = inflater;
        mProductCtrl = new ProductCtrl(mMainActivity);
        mBindingList = new ArrayList<ProductLayoutBinding>();
    }

    public void SetRootView(LinearLayout root)
    {
        mRoot = root;
    }

    public void UpdateProductList(List<Product> pList)
    {
        int listSize = pList.size();
        boolean isClearLast = true;
        for(int i = 0; i < listSize; i++)
        {
            if(i < mBindingList.size())
            {
                Log.i("Cuong","add p but error: " + i +" .name = ");
               // mBindingList.get(i).setProduct(pList.get(i));
                ProductLayoutBinding binding = mBindingList.get(i);
                Product p = pList.get(i);
                binding.setProduct(p);
                binding.setClickedHandler(mProductCtrl);

                if(p.mPromoPrice > 0)
                {
                    TextView txt = (TextView) binding.getRoot().findViewById(R.id.productPrice);
                    txt.setPaintFlags(txt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    txt.setTextColor(Color.GRAY);
                    txt.setTextSize(13);
                }
                ImageView imageView = (ImageView) binding.getRoot().findViewById(R.id.producImage);

                Glide.with(mMainActivity).load(p.mImageURL).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
                mBindingList.get(i).getRoot().setVisibility(View.VISIBLE);
            }
            else
            {
                Log.i("Cuong","add p: " + i +" .name = ");
                isClearLast = false;
                ProductLayoutBinding binding = DataBindingUtil.inflate(mInflater, R.layout.product_layout,mRoot,true);
                Product p = pList.get(i);
                binding.setProduct(p);
                binding.setClickedHandler(mProductCtrl);

                if(p.mPromoPrice > 0)
                {
                    TextView txt = (TextView) binding.getRoot().findViewById(R.id.productPrice);
                    txt.setPaintFlags(txt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    txt.setTextColor(Color.GRAY);
                    txt.setTextSize(13);
                }
                ImageView imageView = (ImageView) binding.getRoot().findViewById(R.id.producImage);

                Glide.with(mMainActivity).load(p.mImageURL).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
                mBindingList.add(binding);
                Log.i("Cuong", "url for Image: " + p.mImageURL);
            }
        }

        //======= check for clear last ==============
        if(isClearLast)
        {
            int bindingSize = mBindingList.size();
            if(bindingSize > listSize) // visible last
            {
                for(int i = listSize; i < bindingSize; i ++)
                {
                    mBindingList.get(i).getRoot().setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    public void ClearView()
    {
        //mRoot.removeAllViews();
        mBindingList.clear();
    }
}
