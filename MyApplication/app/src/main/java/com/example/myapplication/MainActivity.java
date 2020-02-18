package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ProductDetailLayoutBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RequestQueue mRequestQueue;
    private String mSearchURL = "https://listing.stage.tekoapis.net/api/search/?channel=pv_online&q=_PRODUCT&visitorId=&_page=1&_limit=10&terminal=CP01";
    private String mDitailProdURL = "https://listing.stage.tekoapis.net/api/products/PROD_SKU?channel=pv_online&terminal=CP01";
    //private List<Product> mProductList;
    ArrayList<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout main = (LinearLayout)findViewById(R.id.MainLayout);

        main = main.findViewById(R.id.Scroll).findViewById(R.id.ProductList);

        LayoutInflater inflater = LayoutInflater.from(this.getApplicationContext());

        final EditText editText = (EditText) findViewById(R.id.SearchEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String text = editText.getText().toString();
                    SearchForProduct(text);
                    //handled = true;
                }
                return handled;
            }
        });

        mSearchView = new SearchView(this,main,inflater);

        mProductList = new ArrayList<Product>();
/*
        ProductCtrl pCtr = new ProductCtrl();

        ProductLayoutBinding binding1 = DataBindingUtil.inflate(inflater,R.layout.product_layout,main,true);
        Product p1 = new Product("Test name","999.999");
        binding1.setProduct(p1);
        binding1.setClickedHandler(pCtr);

        ProductLayoutBinding binding2 = DataBindingUtil.inflate(inflater,R.layout.product_layout,main,true);
        Product p2 = new Product("Test name--222","999.111");
        binding2.setProduct(p2);
        binding2.setClickedHandler(pCtr);
*/
        InitRequestQueue();

    }

    private void InitRequestQueue()
    {

        Cache cache = new DiskBasedCache(getCacheDir(), 10*1024 * 1024); // 10MB cache
        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

    }
    public void GetProductDetail(Product p)
    {
        String url = mDitailProdURL.replace("PROD_SKU",p.mSKU);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseProdDetailRespone(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Add the request to the RequestQueue.
        mRequestQueue.add(jsonObjectRequest);
    }

    private void parseProdDetailRespone(JSONObject obj)
    {
        try
        {
            JSONObject product = obj.getJSONObject("result").getJSONObject("product");
            Product p = new Product();
            p.ConstructDetail(product);
            ShowDetailProduct(p);
        }
        catch (Exception e){}

    }

    public void SearchForProduct(String product)
    {
        String pEncode = product;
        try
        {
            pEncode = URLEncoder.encode(product,"US-ASCII");
        } catch (Exception e){}


        String url = mSearchURL.replace("_PRODUCT",pEncode);


        Log.i("Cuong","sear url : "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseSearchRespone(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Add the request to the RequestQueue.
        mRequestQueue.add(jsonObjectRequest);
    }


    private void parseSearchRespone(JSONObject res)
    {
        mProductList.clear();
        //mSearchView.ClearView();
        try {
            Log.d("Cuong", "log 11111 ");
            JSONObject result = res.getJSONObject("result");
            Log.d("Cuong", "log 22222 ");
            JSONArray productList = result.getJSONArray("products");
            Log.d("Cuong", "log 3333 ");
            int count = productList.length();
            Log.d("Cuong", "log 4444 lengh:  "+count);
            for(int i = 0; i < count; i++)
            {
              JSONObject obj = productList.getJSONObject(i);
              Product p = new Product();
              p.Construct(obj);
              mProductList.add(p);
            }
            mSearchView.UpdateProductList(mProductList);
            Log.d("Cuong", "parseSearchRespone: number product: "+ count);
        }catch (Exception e)
        {
            Log.i("Cuong","Can not parse respone: "+e);
        }
    }

    public void ShowDetailProduct(Product p)
    {
        ProductDetailLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.product_detail_layout);
        binding.setProduct(p);

        ImageView imageView = (ImageView) binding.getRoot().findViewById(R.id.producImage);

        Glide.with(this).load(p.mImageURL).into(imageView);
    }

    public void BackPress(View v)
    {
        Log.i("Cuong","Press back button");
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.SearchEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String text = editText.getText().toString();
                    SearchForProduct(text);
                    //handled = true;
                }
                return handled;
            }
        });
        
        LinearLayout main = (LinearLayout)findViewById(R.id.MainLayout);

        main = main.findViewById(R.id.Scroll).findViewById(R.id.ProductList);
        mSearchView.SetRootView(main);
        mSearchView.ClearView();
        mSearchView.UpdateProductList(mProductList);
    }

}
