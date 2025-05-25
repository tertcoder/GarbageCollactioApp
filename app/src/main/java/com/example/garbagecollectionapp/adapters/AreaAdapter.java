package com.example.garbagecollectionapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.Area;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {

    private Context context;
    private List<Area> areaList;

    public AreaAdapter(Context context, List<Area> areaList) {
        this.context = context;
        this.areaList = areaList;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_area, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Area area = areaList.get(position);

        holder.tvName.setText(area.getName());
        holder.tvZone.setText(area.getZone());

        StringBuilder pickupDays = new StringBuilder();
        for (String day : area.getPickupDays()) {
            if (pickupDays.length() > 0) {
                pickupDays.append(", ");
            }
            pickupDays.append(day);
        }
        holder.tvPickupDays.setText(pickupDays.toString());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvZone, tvPickupDays;

        AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvZone = itemView.findViewById(R.id.tv_zone);
            tvPickupDays = itemView.findViewById(R.id.tv_pickup_days);
        }
    }
}