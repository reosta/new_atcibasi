package com.atcibasi.new_atcibasi.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.atcibasi.new_atcibasi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NasilOynanirFragment extends Fragment {

    public NasilOynanirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nasil_oynanir, container, false);
    }
}
