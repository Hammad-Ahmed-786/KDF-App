package com.example.kdf.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class UpdateUserActivity extends AppCompatActivity {

    TextInputEditText ed1, ed2, ed3, ed4;
    String name, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        ed1 = findViewById(R.id.userNameEditText);
        ed2 = findViewById(R.id.emailED);
        ed3 = findViewById(R.id.phoneED);
        ed4 = findViewById(R.id.passwordEditText);

        name = getIntent().getStringExtra("name");
        name = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("password");
        ed1.setText(name);
        ed2.setText(email);
        ed3.setText(phone);
        ed4.setText(password);

        findViewById(R.id.update).setOnClickListener(view -> updateUser());
    }

    private void updateUser() {
        final String username = Objects.requireNonNull(ed1.getText()).toString();
        final String email = Objects.requireNonNull(ed2.getText()).toString();
        final String password = Objects.requireNonNull(ed4.getText()).toString();
        final String phone = Objects.requireNonNull(ed3.getText()).toString();

        if (TextUtils.isEmpty(username)) {
            ed1.setError("Please enter username");
            ed1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            ed2.setError("Please enter your email");
            ed2.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed2.setError("Enter a valid email");
            ed2.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ed4.setError("Enter a password");
            ed4.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_REGISTER,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        JSONObject userJson = obj.getJSONObject("data");

                        User user = new User(
                                userJson.getString("name"),
                                userJson.getString("email"),
                                userJson.getString("number"),
                                userJson.getString("password"),
                                userJson.getString("role")
                        );

                        Preferences.userLogin(user, UpdateUserActivity.this);
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