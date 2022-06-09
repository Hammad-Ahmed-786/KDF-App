package com.example.kdf.adapters;

import android.annotation.SuppressLint;
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
import com.example.kdf.models.SubMenuModel;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.List;

public class SubMenuItemsAdapter extends RecyclerView.Adapter<SubMenuItemsAdapter.SubMenuItemsViewHolder> {

    Context context;
    List<SubMenuModel> modelData;
    StartListener listener;

    public SubMenuItemsAdapter(Context context, List<SubMenuModel> modelData, StartListener listener) {
        this.context = context;
        this.modelData = modelData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubMenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubMenuItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.sub_menu_items, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubMenuItemsViewHolder holder, int position) {
        SubMenuModel model = modelData.get(position);
        holder.itemName.setText(model.getName());
        holder.itemDesc.setText(model.getDescription());
        holder.itemPrice.setText("RS. " + model.getPrice());

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
        return modelData.size();
    }

    static class SubMenuItemsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImg;
        TextView itemName, itemDesc, itemPrice;

        public SubMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.itemImg);
            itemName = itemView.findViewById(R.id.itemName);
            itemDesc = itemView.findViewById(R.id.itemDesc);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
