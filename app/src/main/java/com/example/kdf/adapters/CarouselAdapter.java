package com.example.kdf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.kdf.R;
import com.example.kdf.models.CarouselModel;

import java.util.List;

public class CarouselAdapter extends PagerAdapter {

    Context context;
    List<CarouselModel> modelData;

    public CarouselAdapter(Context context, List<CarouselModel> modelData) {
        this.context = context;
        this.modelData = modelData;
    }

    @Override
    public int getCount() {
        return modelData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.carousel_view, null);
        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);
        featured_image.setImageResource(modelData.get(position).getImg());
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}