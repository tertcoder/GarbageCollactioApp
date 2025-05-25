package com.example.garbagecollectionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.fragments.HomeFragment;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabCreateRequest;
    private TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabCreateRequest = findViewById(R.id.fab_create_request);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        fabCreateRequest.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateRequestActivity.class));
        });

        // Load the default fragment
        loadFragment(new HomeFragment());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getSelectedNavigationItem() {
        return R.id.nav_home;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            tvToolbarTitle.setText(R.string.home);
            fragment = new HomeFragment();
        } else if (itemId == R.id.nav_areas) {
            tvToolbarTitle.setText(R.string.areas);
            startActivity(new Intent(this, AreaListActivity.class));
            return true;
        } else if (itemId == R.id.nav_schedules) {
            tvToolbarTitle.setText(R.string.schedules);
            startActivity(new Intent(this, ScheduleListActivity.class));
            return true;
        } else if (itemId == R.id.nav_requests) {
            tvToolbarTitle.setText(R.string.requests);
            startActivity(new Intent(this, RequestListActivity.class));
            return true;
        } else if (itemId == R.id.nav_profile) {
            tvToolbarTitle.setText(R.string.profile);
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return loadFragment(fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPrefManager.getInstance().isLoggedIn()) {
            Intent intent = new Intent(this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}