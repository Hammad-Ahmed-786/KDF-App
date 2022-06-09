package com.example.kdf.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kdf.R;
import com.example.kdf.activities.UpdateUserActivity;
import com.example.kdf.models.User;
import com.example.kdf.utils.Preferences;

public class ProfileFragment extends Fragment {

    TextView t1, t2, t3, t4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        User user = Preferences.getUser(requireContext());

        t1 = view.findViewById(R.id.userName);
        t2 = view.findViewById(R.id.email);
        t3 = view.findViewById(R.id.phone);
        t4 = view.findViewById(R.id.password);

        t1.setText(user.getName());
        t2.setText(user.getEmail());
        t3.setText(user.getPhone());
        t4.setText(user.getPassword());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update) {
            Intent intent = new Intent(requireContext(), UpdateUserActivity.class);
            intent.putExtra("name", t1.getText().toString());
            intent.putExtra("email", t2.getText().toString());
            intent.putExtra("phone", t3.getText().toString());
            intent.putExtra("password", t4.getText().toString());
        } else {
            Preferences.logout(requireContext());
        }
        return true;
    }
}