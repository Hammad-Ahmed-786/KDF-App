package com.example.kdf.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kdf.R;
import com.example.kdf.utils.Preferences;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("userInfo", "onCreateView: 1" + Preferences.getUser(requireContext()));
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}