package com.example.kdf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.kdf.R;
import com.example.kdf.models.User;
import com.example.kdf.networking.APIs;
import com.example.kdf.networking.VolleySingeltonClass;
import com.example.kdf.utils.Preferences;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextEmail, editTextPassword, phoneNumberEditText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar = findViewById(R.id.progressBar);

        editTextUsername = findViewById(R.id.userNameEditText);
        editTextEmail = findViewById(R.id.emailED);
        editTextPassword = findViewById(R.id.passwordEditText);
        phoneNumberEditText = findViewById(R.id.phoneED);

        findViewById(R.id.signUp).setOnClickListener(view -> registerUser());

        findViewById(R.id.signInTV).setOnClickListener(view -> {
            finish();
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        });

    }

    private void registerUser() {
        final String username = Objects.requireNonNull(editTextUsername.getText()).toString();
        final String email = Objects.requireNonNull(editTextEmail.getText()).toString();
        final String password = Objects.requireNonNull(editTextPassword.getText()).toString();
        final String phone = Objects.requireNonNull(phoneNumberEditText.getText()).toString();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_REGISTER,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        JSONObject userJson = obj.getJSONObject("data");

                        User user = new User(
                                userJson.getString("name"),
                                userJson.getString("email"),
                                userJson.getString("number"),
                                editTextPassword.getText().toString(),
                                userJson.getString("role")
                        );

                        Preferences.userLogin(user, SignUpActivity.this);
                        Log.d("userInfo1", "registerUser: " + user.getName());
                        Log.d("userInfo1", "registerUser: " + user.getEmail());
                        Log.d("userInfo1", "registerUser: " + user.getPassword());
                        Log.d("userInfo1", "registerUser: " + user.getRole());
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } catch (JSONException e) {
                        Log.d("errrrr", "registerUser: " + e.getMessage());
                    }
                },
                error -> Log.d("volleyError", "Failed with error msg:\t" + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("email", email);
                params.put("password", password);
                params.put("number", phone);
                params.put("role", "waiter");

                return params;
            }
        };

        VolleySingeltonClass.getInstance(this).addToRequestQueue(stringRequest);
    }
}