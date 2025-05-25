package com.example.garbagecollectionapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.Schedule;
import com.example.garbagecollectionapp.utils.DateUtils;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.tvType.setText(schedule.getType());
        holder.tvDate.setText(DateUtils.formatDate(schedule.getPickupDate(), DateUtils.DISPLAY_DATE_TIME_FORMAT));
        holder.tvNotes.setText(schedule.getNotes());

        // Set different background based on type
        if (schedule.getType().equals("REGULAR")) {
            holder.itemView.setBackgroundResource(R.drawable.bg_schedule_regular);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_schedule_special);
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvDate, tvNotes;

        ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvNotes = itemView.findViewById(R.id.tv_notes);
        }
    }
}