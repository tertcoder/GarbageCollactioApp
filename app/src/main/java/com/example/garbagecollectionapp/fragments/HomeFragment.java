package com.example.garbagecollectionapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.activities.RequestListActivity;
import com.example.garbagecollectionapp.activities.ScheduleListActivity;
import com.example.garbagecollectionapp.adapters.ScheduleAdapter;
import com.example.garbagecollectionapp.models.Schedule;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvUpcomingSchedules, rvPendingRequests;
    private ScheduleAdapter upcomingSchedulesAdapter, pendingRequestsAdapter;
    private List<Schedule> upcomingSchedulesList = new ArrayList<>();
    private List<Schedule> pendingRequestsList = new ArrayList<>();
    private ShimmerFrameLayout shimmerUpcoming, shimmerPending;
    private TextView tvViewAllSchedules, tvViewAllRequests, tvNoUpcoming, tvNoPending;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerViews();
        loadData();

        return view;
    }

    private void initViews(View view) {
        rvUpcomingSchedules = view.findViewById(R.id.rv_upcoming_schedules);
        rvPendingRequests = view.findViewById(R.id.rv_pending_requests);
        shimmerUpcoming = view.findViewById(R.id.shimmer_upcoming);
        shimmerPending = view.findViewById(R.id.shimmer_pending);
        tvViewAllSchedules = view.findViewById(R.id.tv_view_all_schedules);
        tvViewAllRequests = view.findViewById(R.id.tv_view_all_requests);
        tvNoUpcoming = view.findViewById(R.id.tv_no_upcoming);
        tvNoPending = view.findViewById(R.id.tv_no_pending);

        tvViewAllSchedules.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ScheduleListActivity.class));
        });

        tvViewAllRequests.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RequestListActivity.class));
        });
    }

    private void setupRecyclerViews() {
        // Upcoming Schedules RecyclerView
        rvUpcomingSchedules.setLayoutManager(new LinearLayoutManager(getContext()));
        upcomingSchedulesAdapter = new ScheduleAdapter(getContext(), upcomingSchedulesList);
        rvUpcomingSchedules.setAdapter(upcomingSchedulesAdapter);

        // Pending Requests RecyclerView
        rvPendingRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        pendingRequestsAdapter = new ScheduleAdapter(getContext(), pendingRequestsList);
        rvPendingRequests.setAdapter(pendingRequestsAdapter);
    }

    private void loadData() {
        // Show shimmer effect
        shimmerUpcoming.startShimmer();
        shimmerPending.startShimmer();
        rvUpcomingSchedules.setVisibility(View.GONE);
        rvPendingRequests.setVisibility(View.GONE);
        tvNoUpcoming.setVisibility(View.GONE);
        tvNoPending.setVisibility(View.GONE);

        // Simulate API call delay
        rvUpcomingSchedules.postDelayed(() -> {
            // Hide shimmer
            shimmerUpcoming.stopShimmer();
            shimmerUpcoming.setVisibility(View.GONE);
            shimmerPending.stopShimmer();
            shimmerPending.setVisibility(View.GONE);

            // TODO: Replace with actual API call
            // For now, we'll use dummy data
            if (upcomingSchedulesList.isEmpty()) {
                // Add dummy data
                for (int i = 0; i < 3; i++) {
                    Schedule schedule = new Schedule();
                    schedule.setType("REGULAR");
                    schedule.setPickupDate(new Date(System.currentTimeMillis() + (i * 86400000))); // Add i days
                    schedule.setNotes("Regular pickup for Zone " + (i + 1));
                    upcomingSchedulesList.add(schedule);
                }

                upcomingSchedulesAdapter.notifyDataSetChanged();
                rvUpcomingSchedules.setVisibility(View.VISIBLE);
            }

            if (pendingRequestsList.isEmpty()) {
                // Add dummy data
                Schedule request = new Schedule();
                request.setType("SPECIAL");
                request.setPickupDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
                request.setNotes("Bulk furniture disposal");
                pendingRequestsList.add(request);

                pendingRequestsAdapter.notifyDataSetChanged();
                rvPendingRequests.setVisibility(View.VISIBLE);
            }

            // Show empty states if no data
            if (upcomingSchedulesList.isEmpty()) {
                tvNoUpcoming.setVisibility(View.VISIBLE);
            }

            if (pendingRequestsList.isEmpty()) {
                tvNoPending.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}