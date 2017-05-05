package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.

 */
public class BuatberitaFragment extends Fragment {

    Spinner sp;

    public BuatberitaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buatberita, container, false);
        sp = (Spinner) view.findViewById(R.id.spinKategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.Kategori, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp.setAdapter(adapter);
        return view;
    }
}
