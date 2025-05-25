package com.example.garbagecollectionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.User;
import com.example.garbagecollectionapp.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone;
    private Button btnLogout, btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhone = findViewById(R.id.tv_phone);
        btnLogout = findViewById(R.id.btn_logout);
        btnChangePassword = findViewById(R.id.btn_change_password);

        loadUserData();
        setupButtons();
    }

    private void loadUserData() {
        User user = SharedPrefManager.getInstance().getUser();
        if (user != null) {
            tvName.setText(user.getFullName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhoneNumber());
        }
    }

    private void setupButtons() {
        btnLogout.setOnClickListener(v -> {
            SharedPrefManager.getInstance().logout();
            Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnChangePassword.setOnClickListener(v -> {
            // TODO: Implement change password functionality
        });
    }
}