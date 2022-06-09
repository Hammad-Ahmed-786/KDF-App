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

public class SignInActivity extends AppCompatActivity {

    TextInputEditText emailET, passwordET;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressBar = findViewById(R.id.progressBar);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);

        //calling the method userLogin() for login the user
        findViewById(R.id.signInBtn).setOnClickListener(view -> userLogin());

        //if user presses on textview not register calling RegisterActivity
        findViewById(R.id.signUpTV).setOnClickListener(view -> {
            finish();
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
    }

    private void userLogin() {
        //first getting the values
        final String username = Objects.requireNonNull(emailET.getText()).toString();
        final String password = Objects.requireNonNull(passwordET.getText()).toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            emailET.setError("Please enter your name");
            emailET.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordET.setError("Please enter your password");
            passwordET.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIs.URL_LOGIN,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.d("json", "userLogin: " + obj);
                        User user;

                        JSONObject userJson = obj.getJSONObject("data");
                        user = new User(
                                userJson.getString("name"),
                                userJson.getString("email"),
                                userJson.getString("password"),
                                userJson.getString("role")
                        );

                        Preferences.userLogin(user, SignInActivity.this);
                        Log.d("testingUser", "userLogin: " + user.getName());
                        Log.d("testingUser", "userLogin: " + user.getEmail());
                        Log.d("testingUser", "userLogin: " + user.getPassword());

                        Toast.makeText(SignInActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    } catch (JSONException e) {
                        Log.d("errrrr", "userLogin: " + e.getMessage() + "");
                    }
                },
                error -> Log.d("volleyError", "Failed with error msg:\t" + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingeltonClass.getInstance(this).addToRequestQueue(stringRequest);
    }
}