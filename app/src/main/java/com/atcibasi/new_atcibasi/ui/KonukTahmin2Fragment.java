package com.atcibasi.new_atcibasi.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.Uploadk2;
import com.atcibasi.new_atcibasi.adapter.ImageAdapterk2;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class KonukTahmin2Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageAdapterk2 mk2Adapter;
    private ProgressBar mProgresCircle;
    private DatabaseReference mDatabaseRef;
    private List<Uploadk2> mk2Uploads;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View k2_tahmin=inflater.inflate(R.layout.fragment_konuk_tahmin2, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView=k2_tahmin.findViewById(R.id.adView_k2);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        mRecyclerView=k2_tahmin.findViewById(R.id.k2_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(layoutManager);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgresCircle=k2_tahmin.findViewById(R.id.progress_circle);
        mk2Uploads=new ArrayList<>();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads_k2");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Uploadk2 uploadk2=postSnapshot.getValue(Uploadk2.class);
                    mk2Uploads.add(uploadk2);
                }
                mk2Adapter= new ImageAdapterk2(getContext(),mk2Uploads);
                mRecyclerView.setAdapter(mk2Adapter);
                mProgresCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgresCircle.setVisibility(View.INVISIBLE);
            }
        });

       return  k2_tahmin;
    }
}
