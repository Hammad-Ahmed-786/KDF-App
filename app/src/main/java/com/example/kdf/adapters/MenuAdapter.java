package com.example.kdf.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kdf.R;
import com.example.kdf.interfaces.StartListener;
import com.example.kdf.models.MenuModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context context;
    List<MenuModel> menuModelData;
    StartListener listener;

    public MenuAdapter(Context context, List<MenuModel> menuModelData, StartListener listener) {
        this.context = context;
        this.menuModelData = menuModelData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuModel model = menuModelData.get(position);
        holder.title.setText(model.getName());
        holder.desc.setText(model.getDescription());

        Shimmer shimmer = new Shimmer.ColorHighlightBuilder().setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .build();
        ShimmerDrawable drawable = new ShimmerDrawable();
        drawable.setShimmer(shimmer);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_launcher_background);
        Log.d("img", "onBindViewHolder: " + model.getImage());
        Glide.with(context).load(model.getImage()).apply(options).into(holder.itemImg);
        holder.itemView.setOnClickListener(v -> listener.start(position));
    }

    @Override
    public int getItemCount() {
        return menuModelData.size();
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImg;
        TextView title, desc;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.itemImg);
            title = itemView.findViewById(R.id.itemName);
            desc = itemView.findViewById(R.id.itemShortDesc);
        }
    }
}
