package com.example.kdf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kdf.R;
import com.example.kdf.activities.MenuItemsActivity;
import com.example.kdf.adapters.CarouselAdapter;
import com.example.kdf.adapters.MenuAdapter;
import com.example.kdf.interfaces.StartListener;
import com.example.kdf.models.CarouselModel;
import com.example.kdf.models.MenuModel;
import com.example.kdf.networking.APIs;
import com.example.kdf.utils.Preferences;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements StartListener {

    RecyclerView menuItemsRecyclerView;
    MenuAdapter adapter;
    List<MenuModel> model = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    ViewPager carouselViewPager;
    List<CarouselModel> carouselModel = new ArrayList<>();
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        menuItemsRecyclerView = view.findViewById(R.id.menuItemsRecyclerView);
        mShimmerViewContainer = view.findViewById(R.id.shimmer);
        menuItemsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        menuItemsRecyclerView.setHasFixedSize(true);
        getData();

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            getData();
            pullToRefresh.setRefreshing(false);
        });

        carouselViewPager = view.findViewById(R.id.carouselViewPager);
        tabLayout = view.findViewById(R.id.my_tablayout);
        getCarouselData();
        carouselViewPager.setAdapter(new CarouselAdapter(requireContext(), carouselModel));
        tabLayout.setupWithViewPager(carouselViewPager, true);
        return view;
    }

    private void getCarouselData() {
        carouselModel.clear();
        carouselModel.add(new CarouselModel(R.drawable.ic_launcher_background));
        carouselModel.add(new CarouselModel(R.drawable.ic_launcher_background));
        carouselModel.add(new CarouselModel(R.drawable.ic_launcher_background));
        carouselModel.add(new CarouselModel(R.drawable.ic_launcher_background));
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, APIs.MENU, response -> {
            try {
                JSONObject jObj = new JSONObject(response);
                JSONArray jr = jObj.getJSONArray("menu_data");
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject object = jr.getJSONObject(i);

                    String name = object.getString("name");
                    String desc = object.getString("short_desc");
                    String image = object.getString("image");

                    MenuModel m = new MenuModel(name, desc, image);
                    model.add(m);
                }
                adapter = new MenuAdapter(requireContext(), model, HomeFragment.this);
                menuItemsRecyclerView.setAdapter(adapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> Log.d("volleyError", "onErrorResponse: " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", Preferences.getUser(requireContext()).getName());
                params.put("email", Preferences.getUser(requireContext()).getEmail());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void start(int p) {
        Intent intent = new Intent(requireContext(), MenuItemsActivity.class);
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