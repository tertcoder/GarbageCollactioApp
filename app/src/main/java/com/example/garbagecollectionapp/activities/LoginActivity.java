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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(v -> loginUser());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        ApiInterface apiService = ApiClient.getApiService();
        Call<ResponseBody> call = apiService.loginUser(user);

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

                        User user = new User();
                        user.setId(data.getString("id"));
                        user.setFullName(data.getString("fullName"));
                        user.setEmail(data.getString("email"));

                        // Save user data and tokens
                        SharedPrefManager.getInstance().saveUser(user);
                        SharedPrefManager.getInstance().saveToken(token);
                        SharedPrefManager.getInstance().saveRefreshToken(refreshToken);

                        // Navigate to main activity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}