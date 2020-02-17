package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RequestQueue mRequestQueue;
    private String mSearchURL = "https://listing.stage.tekoapis.net/api/search/?channel=pv_online&q=_PRODUCT&visitorId=&_page=1&_limit=10&terminal=CP01";
    private List<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout main = (LinearLayout)findViewById(R.id.MainLayout);

        LayoutInflater inflater = LayoutInflater.from(this.getApplicationContext());

        mSearchView = new SearchView(main,inflater);

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
        SearchForProduct("intel");
    }

    public void SearchForProduct(String product)
    {
        String url = mSearchURL.replace("_PRODUCT",product);
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
        try {
            JSONObject result = res.getJSONObject("result");

            JSONArray productList = result.getJSONArray("products");
            int count = productList.length();
            for(int i = 0; i < count; i++)
            {
                int Price = -1;
                String sku = "";
                String name ="";
                String image = "";
                try {
                    sku = productList.getJSONObject(i).getString("sku");
                }
                catch (Exception e) {}

                try
                {
                    name = productList.getJSONObject(i).getString("name");
                }
                catch (Exception e){};



                try{
                    Price = productList.getJSONObject(i).getJSONObject("price").getInt("sellPrice");
                }
                catch (Exception e) { }

                try{
                    image = productList.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("url");
                }
                catch (Exception e) { }

                Product p = new Product(sku,name,Price);
                p.SetImageURL(image);
                p.SetPromoPrice(555);
                mSearchView.AddProduct(p);
            }
            Log.d("Cuong", "parseSearchRespone: number product: "+ count);
        }catch (Exception e)
        {
            Log.i("Cuong","Can not parse respone: "+e);
        }
    }

}
