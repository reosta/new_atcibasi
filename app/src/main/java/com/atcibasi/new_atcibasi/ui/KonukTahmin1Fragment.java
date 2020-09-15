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
import com.atcibasi.new_atcibasi.Uploadk1;
import com.atcibasi.new_atcibasi.adapter.ImageAdapterk1;
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

public class KonukTahmin1Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ImageAdapterk1 mk1Adapter;
    private ProgressBar mProgresCircle;
    private DatabaseReference mDatabaseRef;
    private List<Uploadk1> mk1Uploads;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View k1_tahmin= inflater.inflate(R.layout.fragment_konuk_tahmin1, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView=k1_tahmin.findViewById(R.id.adView_k1);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mRecyclerView=k1_tahmin.findViewById(R.id.k1_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(layoutManager);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgresCircle=k1_tahmin.findViewById(R.id.progress_circle);
        mk1Uploads=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads_k1");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Uploadk1 uploadk1=postSnapshot.getValue(Uploadk1.class);
                    mk1Uploads.add(uploadk1);
                }
                mk1Adapter= new ImageAdapterk1(getContext(),mk1Uploads);
                mRecyclerView.setAdapter(mk1Adapter);
                mProgresCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgresCircle.setVisibility(View.INVISIBLE);
            }
        });
        return k1_tahmin;
    }

}
