package com.example.garbagecollectionapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.adapters.AreaAdapter;
import com.example.garbagecollectionapp.models.Area;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class AreaListActivity extends BaseActivity {

    private RecyclerView rvAreas;
    private AreaAdapter areaAdapter;
    private List<Area> areaList = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private ProgressBar progressBar;
    private TextView tvNoAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);

        rvAreas = findViewById(R.id.rv_areas);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        progressBar = findViewById(R.id.progress_bar);
        tvNoAreas = findViewById(R.id.tv_no_areas);

        setupRecyclerView();
        loadAreas();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_area_list;
    }

    @Override
    protected int getSelectedNavigationItem() {
        return R.id.nav_areas;
    }

    private void setupRecyclerView() {
        rvAreas.setLayoutManager(new LinearLayoutManager(this));
        areaAdapter = new AreaAdapter(this, areaList);
        rvAreas.setAdapter(areaAdapter);
    }

    private void loadAreas() {
        shimmerFrameLayout.startShimmer();
        rvAreas.setVisibility(View.GONE);
        tvNoAreas.setVisibility(View.GONE);

        // Simulate API call delay
        rvAreas.postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);

            // TODO: Replace with actual API call
            // For now, we'll use dummy data
            if (areaList.isEmpty()) {
                // Add dummy data
                for (int i = 0; i < 10; i++) {
                    Area area = new Area();
                    area.setName("Area " + (i + 1));
                    area.setZone("Zone " + (i % 3 + 1));

                    List<String> pickupDays = new ArrayList<>();
                    pickupDays.add("MONDAY");
                    pickupDays.add("WEDNESDAY");
                    pickupDays.add("FRIDAY");
                    area.setPickupDays(pickupDays);

                    areaList.add(area);
                }

                areaAdapter.notifyDataSetChanged();
                rvAreas.setVisibility(View.VISIBLE);
            }

            if (areaList.isEmpty()) {
                tvNoAreas.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}