package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.LayoutInflater;

import com.example.myapplication.databinding.ProductLayoutBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout main = (LinearLayout)findViewById(R.id.MainLayout);

        View v = (View) findViewById((R.id.MainLayout));
        LayoutInflater inflater = LayoutInflater.from(this.getApplicationContext());

        ProductCtrl pCtr = new ProductCtrl();

        ProductLayoutBinding binding1 = DataBindingUtil.inflate(inflater,R.layout.product_layout,main,true);
        Product p1 = new Product("Test name","999.999");
        binding1.setProduct(p1);
        binding1.setClickedHandler(pCtr);

        ProductLayoutBinding binding2 = DataBindingUtil.inflate(inflater,R.layout.product_layout,main,true);
        Product p2 = new Product("Test name--222","999.111");
        binding2.setProduct(p2);
        binding2.setClickedHandler(pCtr);

    }
}
