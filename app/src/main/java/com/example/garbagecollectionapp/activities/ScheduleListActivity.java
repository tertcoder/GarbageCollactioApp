package com.example.garbagecollectionapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.adapters.ScheduleAdapter;
import com.example.garbagecollectionapp.models.Schedule;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleListActivity extends BaseActivity {

    private RecyclerView rvSchedules;
    private ScheduleAdapter scheduleAdapter;
    private List<Schedule> scheduleList = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private ProgressBar progressBar;
    private TextView tvNoSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        rvSchedules = findViewById(R.id.rv_schedules);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        progressBar = findViewById(R.id.progress_bar);
        tvNoSchedules = findViewById(R.id.tv_no_schedules);

        setupRecyclerView();
        loadSchedules();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_schedule_list;
    }

    @Override
    protected int getSelectedNavigationItem() {
        return R.id.nav_schedules;
    }

    private void setupRecyclerView() {
        rvSchedules.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(this, scheduleList);
        rvSchedules.setAdapter(scheduleAdapter);
    }

    private void loadSchedules() {
        shimmerFrameLayout.startShimmer();
        rvSchedules.setVisibility(View.GONE);
        tvNoSchedules.setVisibility(View.GONE);

        // Simulate API call delay
        rvSchedules.postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);

            // TODO: Replace with actual API call
            // For now, we'll use dummy data
            if (scheduleList.isEmpty()) {
                // Add dummy data
                for (int i = 0; i < 10; i++) {
                    Schedule schedule = new Schedule();
                    schedule.setType(i % 2 == 0 ? "REGULAR" : "SPECIAL");
                    schedule.setPickupDate(new Date(System.currentTimeMillis() + (i * 86400000))); // Add i days
                    schedule.setNotes(i % 2 == 0 ? "Regular weekly pickup" : "Special request for bulk items");
                    scheduleList.add(schedule);
                }

                scheduleAdapter.notifyDataSetChanged();
                rvSchedules.setVisibility(View.VISIBLE);
            }

            if (scheduleList.isEmpty()) {
                tvNoSchedules.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}