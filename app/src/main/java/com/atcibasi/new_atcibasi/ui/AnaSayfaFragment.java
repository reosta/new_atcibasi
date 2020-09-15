package com.atcibasi.new_atcibasi.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.Upload;
import com.atcibasi.new_atcibasi.adapter.ImageAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

public class AnaSayfaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgresCircle;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private AdView adView;

    private Button kn1;
    private InterstitialAd mInterstitialAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ana_sayfa = inflater.inflate(R.layout.fragment_ana_sayfa, container, false);

        mRecyclerView = ana_sayfa.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        mRecyclerView.setLayoutManager(layoutManager);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgresCircle = ana_sayfa.findViewById(R.id.progress_circle);


        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(getContext(), mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgresCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgresCircle.setVisibility(View.INVISIBLE);
            }
        });

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = ana_sayfa.findViewById(R.id.adView_anasayfa);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mInterstitialAd=new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1124068966706309/8427910583");
        AdRequest adRequest1=new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener(){
            public void onAdClosed(){
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


        kn1 = ana_sayfa.findViewById(R.id.konuk1);

        kn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    NavHostFragment.findNavController(AnaSayfaFragment.this)
                            .navigate(R.id.action_nav_ana_sayfa_to_nav_konuk_tahmin);
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    NavHostFragment.findNavController(AnaSayfaFragment.this)
                            .navigate(R.id.action_nav_ana_sayfa_to_nav_konuk_tahmin);
                }


            }
        });


        ana_sayfa.findViewById(R.id.konuk2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                            NavHostFragment.findNavController(AnaSayfaFragment.this).
                                    navigate(R.id.action_nav_ana_sayfa_to_nav_konuk_tahmin2);
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                            mInterstitialAd.show();
                            NavHostFragment.findNavController(AnaSayfaFragment.this).
                                    navigate(R.id.action_nav_ana_sayfa_to_nav_konuk_tahmin2);
                        }
            }
        });


        return ana_sayfa;
    }
}