package com.example.garbagecollectionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagecollectionapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView bottomNavigationView;
    protected FloatingActionButton fabCreateRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabCreateRequest = findViewById(R.id.fab_create_request);

        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            // Set the current item based on the activity
            bottomNavigationView.setSelectedItemId(getSelectedNavigationItem());
        }

        if (fabCreateRequest != null) {
            fabCreateRequest.setOnClickListener(v -> {
                startActivity(new Intent(this, CreateRequestActivity.class));
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == getSelectedNavigationItem()) {
            return true;
        }

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.nav_areas) {
            startActivity(new Intent(this, AreaListActivity.class));
        } else if (itemId == R.id.nav_schedules) {
            startActivity(new Intent(this, ScheduleListActivity.class));
        } else if (itemId == R.id.nav_requests) {
            startActivity(new Intent(this, RequestListActivity.class));
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }

        finish();
        return true;
    }

    // Abstract method to get the layout resource ID
    protected abstract int getLayoutResourceId();

    // Abstract method to get the selected navigation item
    protected abstract int getSelectedNavigationItem();
} 