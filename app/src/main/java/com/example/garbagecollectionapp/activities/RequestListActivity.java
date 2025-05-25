package com.example.garbagecollectionapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.adapters.RequestAdapter;
import com.example.garbagecollectionapp.models.SpecialRequest;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    private RecyclerView rvRequests;
    private RequestAdapter requestAdapter;
    private List<SpecialRequest> requestList = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private ProgressBar progressBar;
    private TextView tvNoRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        rvRequests = findViewById(R.id.rv_requests);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        progressBar = findViewById(R.id.progress_bar);
        tvNoRequests = findViewById(R.id.tv_no_requests);

        setupRecyclerView();
        loadRequests();
    }

    private void setupRecyclerView() {
        rvRequests.setLayoutManager(new LinearLayoutManager(this));
        requestAdapter = new RequestAdapter(this, requestList);
        rvRequests.setAdapter(requestAdapter);
    }

    private void loadRequests() {
        shimmerFrameLayout.startShimmer();
        rvRequests.setVisibility(View.GONE);
        tvNoRequests.setVisibility(View.GONE);

        // Simulate API call delay
        rvRequests.postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);

            // TODO: Replace with actual API call
            // For now, we'll use dummy data
            if (requestList.isEmpty()) {
                // Add dummy data
                for (int i = 0; i < 5; i++) {
                    SpecialRequest request = new SpecialRequest();
                    request.setDescription("Request " + (i + 1) + " - Bulk item disposal");
                    request.setRequestDate(new Date(System.currentTimeMillis() + (i * 86400000))); // Add i days

                    // Set different statuses
                    if (i % 3 == 0) {
                        request.setStatus("PENDING");
                    } else if (i % 3 == 1) {
                        request.setStatus("APPROVED");
                    } else {
                        request.setStatus("REJECTED");
                    }

                    requestList.add(request);
                }

                requestAdapter.notifyDataSetChanged();
                rvRequests.setVisibility(View.VISIBLE);
            }

            if (requestList.isEmpty()) {
                tvNoRequests.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}