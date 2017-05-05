package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalangDanaFragment extends Fragment{
    Button galang;

    public GalangDanaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galang_dana, container, false);

        galang = (Button) view.findViewById(R.id.btnGalangDana);
        galang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BuatGalangDanaFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainframe, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
