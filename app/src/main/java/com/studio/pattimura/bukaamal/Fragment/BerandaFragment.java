package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {


    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ImageView cover = (ImageView) view.findViewById(R.id.coverBeranda);
        Picasso.with(BerandaFragment.this.getContext()).load(R.drawable.coverberanda).fit().into(cover);


        return view;
    }

}
