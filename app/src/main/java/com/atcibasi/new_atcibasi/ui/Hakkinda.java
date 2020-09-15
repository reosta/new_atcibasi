package com.atcibasi.new_atcibasi.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.atcibasi.new_atcibasi.R;

public class Hakkinda extends Fragment {


    public Hakkinda() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hakkinda =inflater.inflate(R.layout.fragment_hakkinda, container, false);
        return hakkinda;
    }
}