package com.atcibasi.new_atcibasi.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.atcibasi.new_atcibasi.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class HesapModulFragment extends Fragment {

    private TextView sonuc,tahmini_txt,nasil_oynanir;
    private EditText num1,num2,num3,num4,num5,num6,oran;
    private Button hesapla,syf_temizle;
    private String text1,text2,text3,text4,text5,text6;
    private float sonuc_num;
    private float number1,number2,number3,number4,number5,number6,gir_oran;
    private float tevzii= (float) 0.50;
    private Spinner sp;
    private List<String> list;
    private static final String DEFAULT_VALUE = "100";
    private InterstitialAd mInterstitialAd;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hesmodul=inflater.inflate(R.layout.fragment_hesap_modul, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView=hesmodul.findViewById(R.id.adView_hesap);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mInterstitialAd=new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1124068966706309/8427910583");
        AdRequest adRequest1=new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener(){
            public void onAdClosed(){
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Toast.makeText(getContext(), "Ad failed: " + loadAdError, Toast.LENGTH_SHORT).show();
            }
        });



        num1=hesmodul.findViewById(R.id.editText_1);
        num2=hesmodul.findViewById(R.id.editText_2);
        num3=hesmodul.findViewById(R.id.editText_3);
        num4=hesmodul.findViewById(R.id.editText_4);
        num5=hesmodul.findViewById(R.id.editText_5);
        num6=hesmodul.findViewById(R.id.editText_6);
        oran=hesmodul.findViewById(R.id.oran_gir);
        sp=hesmodul.findViewById(R.id.spinner);
        tahmini_txt=hesmodul.findViewById(R.id.sonuc_text);
        nasil_oynanir=hesmodul.findViewById(R.id.hakkinda);

        nasil_oynanir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HesapModulFragment.this)
                        .navigate(R.id.action_nav_hesap_modul_to_nav_nasil_oynanir);
            }
        });

        list = new ArrayList<String>();
        list.add("0.07");
        list.add("0.05");
        list.add("0.15");
        list.add("0.10");

        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_dropdown_item_1line, list);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {

                    case 0 :
                        oran.setText("0.07");
                        break;
                    case 1 :
                        oran.setText("0.05");
                        break;
                    case 2 :
                        oran.setText("0.15");
                        break;
                    case 3 :
                        oran.setText("0.10");
                        break;
                    default :
                        oran.setText("0.07");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sonuc=hesmodul.findViewById(R.id.sonuc);
        hesapla=hesmodul.findViewById(R.id.hesapla);

        hesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oran.getText().toString().equals("0.07")||oran.getText().toString().equals("0.05")){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        Hesapla();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                        Hesapla();
                    }
                }
                if(oran.getText().toString().equals("0.15")){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        DortluHesap();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                        DortluHesap();
                    }
                }
                if(oran.getText().toString().equals("0.10")){
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        UcluHesapla();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                        UcluHesapla();
                    }
                }

            }
        });

        syf_temizle=hesmodul.findViewById(R.id.temizle);
        syf_temizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num1.getText().clear();
                num2.getText().clear();
                num3.getText().clear();
                num4.getText().clear();
                num5.getText().clear();
                num6.getText().clear();
                sonuc.setText("");
            }
        });



        return hesmodul;

    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void Hesapla(){
        if (TextUtils.isEmpty(num1.getText().toString()))
        {
            num1.setText("100");
        }if (TextUtils.isEmpty(num2.getText().toString())){
            num2.setText("100");
        }if (TextUtils.isEmpty(num3.getText().toString())){
            num3.setText("100");
        }if (TextUtils.isEmpty(num4.getText().toString())){
            num4.setText("100");
        }if (TextUtils.isEmpty(num5.getText().toString())){
            num5.setText("100");
        }if (TextUtils.isEmpty(num6.getText().toString())){
            num6.setText("100");
        }
        if (TextUtils.isEmpty(oran.getText().toString())){
            oran.setText("0.07");
        }
        number1=100/ Float.parseFloat(num1.getText().toString());
        number2=100/ Float.parseFloat(num2.getText().toString());
        number3=100/ Float.parseFloat(num3.getText().toString());
        number4=100/ Float.parseFloat(num4.getText().toString());
        number5=100/ Float.parseFloat(num5.getText().toString());
        number6=100/ Float.parseFloat(num6.getText().toString());
        gir_oran= Float.parseFloat(oran.getText().toString());
        sonuc_num=(number1*number2*number3*number4*number5*number6)*(gir_oran)*tevzii;
        DecimalFormat formatter = new DecimalFormat("#,###");
        int sonsonuc=(int)sonuc_num;
        String get_value = formatter.format(sonsonuc);
        tahmini_txt.setText("6'lı/5'li Ganyan İkramiye=");
        sonuc.setText(String.valueOf(get_value+ "TL"));

    }
    @SuppressLint("SetTextI18n")
    private void DortluHesap(){
        num1.setText("100");
        num2.setText("100");
        if (TextUtils.isEmpty(num3.getText().toString())){
            num3.setText("100");
        }if (TextUtils.isEmpty(num4.getText().toString())){
            num4.setText("100");
        }if (TextUtils.isEmpty(num5.getText().toString())){
            num5.setText("100");
        }if (TextUtils.isEmpty(num6.getText().toString())){
            num6.setText("100");
        }
        if (TextUtils.isEmpty(oran.getText().toString())){
            oran.setText("0.07");
        }
        number1=100/ Float.parseFloat(num1.getText().toString());
        number2=100/ Float.parseFloat(num2.getText().toString());
        number3=100/ Float.parseFloat(num3.getText().toString());
        number4=100/ Float.parseFloat(num4.getText().toString());
        number5=100/ Float.parseFloat(num5.getText().toString());
        number6=100/ Float.parseFloat(num6.getText().toString());
        gir_oran= Float.parseFloat(oran.getText().toString());
        sonuc_num=(number1*number2*number3*number4*number5*number6)*(gir_oran)*tevzii;
        DecimalFormat formatter = new DecimalFormat("#,###");
        int sonsonuc=(int)sonuc_num;
        String get_value = formatter.format(sonsonuc);
        tahmini_txt.setText("4'lü Ganyan İkramiye =");
        sonuc.setText(String.valueOf(get_value +" "+ "TL" ));

    }
    @SuppressLint("SetTextI18n")
    private void UcluHesapla(){
        num3.setText("100");
        num1.setText("100");
        num2.setText("100");
        if (TextUtils.isEmpty(num4.getText().toString())){
            num4.setText("100");
        }if (TextUtils.isEmpty(num5.getText().toString())){
            num5.setText("100");
        }if (TextUtils.isEmpty(num6.getText().toString())){
            num6.setText("100");
        }
        if (TextUtils.isEmpty(oran.getText().toString())){
            oran.setText("0.07");
        }
        number1=100/ Float.parseFloat(num1.getText().toString());
        number2=100/ Float.parseFloat(num2.getText().toString());
        number3=100/ Float.parseFloat(num3.getText().toString());
        number4=100/ Float.parseFloat(num4.getText().toString());
        number5=100/ Float.parseFloat(num5.getText().toString());
        number6=100/ Float.parseFloat(num6.getText().toString());
        gir_oran= Float.parseFloat(oran.getText().toString());
        sonuc_num=(number1*number2*number3*number4*number5*number6)*(gir_oran)*tevzii;
        DecimalFormat formatter = new DecimalFormat("#,###");
        int sonsonuc=(int)sonuc_num;
        String get_value = formatter.format(sonsonuc);
        tahmini_txt.setText("3'lü Ganyan İkramiye =");
        sonuc.setText(String.valueOf(get_value +" "+ "TL" ));
    }

}
