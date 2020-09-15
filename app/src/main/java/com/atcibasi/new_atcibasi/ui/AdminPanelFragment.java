package com.atcibasi.new_atcibasi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.post.PostActivity;

public class AdminPanelFragment extends Fragment {
    private EditText name,password;
    private Button btn_giris;
    private String user_name="atcibasi06";
    private String user_password="1593575atcibasi";

    public AdminPanelFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View admin_panel= inflater.inflate(R.layout.fragment_admin_panel, container, false);
        // Inflate the layout for this fragment
        name=admin_panel.findViewById(R.id.user_name);
        password=admin_panel.findViewById(R.id.user_password);
        btn_giris=admin_panel.findViewById(R.id.giris);

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name.equals(name.getText().toString()) && user_password.equals(password.getText().toString()))
                {
                    Intent intent=new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }else Toast.makeText(getContext(), "kullanıcı adı yada şifre hatalı", Toast.LENGTH_SHORT).show();

            }
        });

        return admin_panel;
    }
}
