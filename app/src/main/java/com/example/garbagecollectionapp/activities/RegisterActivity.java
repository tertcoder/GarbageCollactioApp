package com.example.garbagecollectionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.api.ApiClient;
import com.example.garbagecollectionapp.api.ApiInterface;
import com.example.garbagecollectionapp.models.User;
import com.example.garbagecollectionapp.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private MaterialButton btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);

        btnRegister.setOnClickListener(v -> registerUser());
        tvLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(password);

        ApiInterface apiService = ApiClient.getApiService();
        Call<ResponseBody> call = apiService.registerUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        JSONObject data = jsonObject.getJSONObject("data");

                        String token = data.getString("token");
                        String refreshToken = data.getString("refreshToken");
                        JSONObject userJson = data.getJSONObject("user");

                        User user = new User();
                        user.setId(userJson.getString("id"));
                        user.setFullName(userJson.getString("fullName"));
                        user.setEmail(userJson.getString("email"));
                        user.setPhoneNumber(userJson.getString("phoneNumber"));

                        // Save user data and tokens
                        SharedPrefManager.getInstance().saveUser(user);
                        SharedPrefManager.getInstance().saveToken(token);
                        SharedPrefManager.getInstance().saveRefreshToken(refreshToken);

                        // Navigate to main activity
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}