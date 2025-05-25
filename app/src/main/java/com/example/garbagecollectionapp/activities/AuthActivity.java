package com.example.garbagecollectionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.RegisterRequest;
import com.example.garbagecollectionapp.models.User;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    private EditText etFullName, etEmail, etPassword, etPhoneNumber;
    private Button btnRegister;
    private TextView tvLogin;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://10.0.2.2:8080/api/v1/auth/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize views
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Set click listeners
        btnRegister.setOnClickListener(v -> registerUser());
        tvLogin.setOnClickListener(v -> {
            // Switch to login mode
            etFullName.setVisibility(View.GONE);
            btnRegister.setText(R.string.login);
            tvLogin.setText(R.string.register_instead);
        });
    }

    private void registerUser() {
        // Get input values
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        // Validate input
        if (!validateInput(fullName, email, password, phoneNumber)) {
            return;
        }

        // Show progress
        progressBar.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);

        // Create register request
        RegisterRequest registerRequest = new RegisterRequest(fullName, email, password, phoneNumber);
        String requestBody = new Gson().toJson(registerRequest);

        try {
            JSONObject jsonBody = new JSONObject(requestBody);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL + "register",
                    jsonBody,
                    response -> {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONObject data = response.getJSONObject("data");
                                String token = data.getString("token");
                                String refreshToken = data.getString("refreshToken");
                                
                                // Create user object
                                User user = new User();
                                user.setId(data.getString("id"));
                                user.setFullName(data.getString("fullName"));
                                user.setEmail(data.getString("email"));
                                user.setPhoneNumber(phoneNumber);

                                // Save user data and tokens
                                SharedPrefManager.getInstance().saveUser(user);
                                SharedPrefManager.getInstance().saveToken(token);
                                SharedPrefManager.getInstance().saveRefreshToken(refreshToken);

                                // Navigate to main activity
                                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            showError("Error parsing response: " + e.getMessage());
                        }
                    },
                    error -> {
                        String errorMessage = "Registration failed";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                JSONObject errorJson = new JSONObject(new String(error.networkResponse.data));
                                errorMessage = errorJson.getString("message");
                            } catch (JSONException e) {
                                errorMessage = "Registration failed: " + error.getMessage();
                            }
                        }
                        showError(errorMessage);
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            requestQueue.add(request);
        } catch (JSONException e) {
            showError("Error creating request: " + e.getMessage());
        }
    }

    private boolean validateInput(String fullName, String email, String password, String phoneNumber) {
        // Validate full name
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Full name is required");
            return false;
        }

        // Validate email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            return false;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return false;
        }
        if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            return false;
        }

        // Validate phone number
        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Phone number is required");
            return false;
        }
        if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
            etPhoneNumber.setError("Phone number must be 10-15 digits");
            return false;
        }
        if (!phoneNumber.matches("\\d+")) {
            etPhoneNumber.setError("Phone number must contain only digits");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        btnRegister.setEnabled(true);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}