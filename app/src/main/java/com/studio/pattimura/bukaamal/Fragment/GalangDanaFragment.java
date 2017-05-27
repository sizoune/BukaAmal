package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.GalangDana;
import com.studio.pattimura.bukaamal.Perjanjian;
import com.studio.pattimura.bukaamal.R;

import java.io.File;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.studio.pattimura.bukaamal.R.id.status;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalangDanaFragment extends Fragment implements View.OnClickListener {
    private Button galang, lampirfoto, simpanlanjut;
    private TabLayout tabLayout;
    private RadioButton rb;
    private TextView kebijakan;


    public GalangDanaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galang_dana, container, false);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        rb = (RadioButton) view.findViewById(R.id.rbDisclaim);

        galang = (Button) view.findViewById(R.id.btnGalangDana);
        kebijakan = (TextView) view.findViewById(R.id.kebijakan);
        kebijakan.setOnClickListener(this);
        galang.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == galang) {
            if (rb.isChecked()) {
//                tabLayout.setVisibility(View.VISIBLE);
//                Fragment fragment = new BuatGalangDanaFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.mainframe, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
                Intent intent = new Intent(this.getContext(), GalangDana.class);
                startActivity(intent);
            } else {
                Toast.makeText(GalangDanaFragment.this.getContext(), "Setujui syarat dan ketentuan yang berlaku terlebih dahulu", Toast.LENGTH_SHORT).show();
                rb.isFocused();
            }
        }
        else if(view == kebijakan){
            Intent intent = new Intent(this.getContext(), Perjanjian.class);
            startActivity(intent);
        }
    }



}
