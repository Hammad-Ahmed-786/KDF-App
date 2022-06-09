package com.example.kdf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kdf.R;
import com.example.kdf.adapters.SubMenuItemsAdapter;
import com.example.kdf.interfaces.StartListener;
import com.example.kdf.models.SubMenuModel;
import com.example.kdf.networking.APIs;
import com.example.kdf.utils.Preferences;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemsActivity extends AppCompatActivity implements StartListener {

    RecyclerView subMenuRV;
    private ShimmerFrameLayout mShimmerViewContainer;
    SubMenuItemsAdapter adapter;
    List<SubMenuModel> model = new ArrayList<>();
    int menuPosition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);

        subMenuRV = findViewById(R.id.subMenuItemsRecyclerView);
        mShimmerViewContainer = findViewById(R.id.shimmer);
        subMenuRV.setLayoutManager(new LinearLayoutManager(this));
        subMenuRV.setHasFixedSize(true);
        menuPosition = getIntent().getIntExtra("position", 0);
        getData();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            getData();
            pullToRefresh.setRefreshing(false);
        });
    }

    private void getData() {
        Log.d("link", "getData: " + APIs.GET_PRODUCT + menuPosition);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.GET_PRODUCT + menuPosition, response -> {
            try {
                JSONObject jObj = new JSONObject(response);
                JSONArray jr = jObj.getJSONArray("products");
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject object = jr.getJSONObject(i);

                    String name = object.getString("name");
                    String desc = object.getString("description");
                    String image = object.getString("image");
                    String price = object.getString("price");

                    Log.d("itemInfo", "getData: " + name + "\t" + price);
                    SubMenuModel m = new SubMenuModel(name, desc, image, price);
                    model.add(m);
                }
                adapter = new SubMenuItemsAdapter(this, model, MenuItemsActivity.this);
                subMenuRV.setAdapter(adapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("errrrrrrrrrr", "getData: " + e.getMessage());
            }

        }, error -> Log.d("volleyError", "onErrorResponse: " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", Preferences.getUser(MenuItemsActivity.this).getName());
                params.put("email", Preferences.getUser(MenuItemsActivity.this).getEmail());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void start(int p) {
        Intent intent = new Intent(this, MenuItemsActivity.class);
        intent.putExtra("position", p);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}