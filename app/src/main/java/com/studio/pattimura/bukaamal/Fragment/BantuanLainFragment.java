package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studio.pattimura.bukaamal.Adapter.AdapterBerita;
import com.studio.pattimura.bukaamal.Adapter.BantuanLainAdapter;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanLainFragment extends Fragment {
    private ArrayList<Berita> dataUKM = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitas = new ArrayList<>();
    private AdapterBerita adapter;
    RecyclerView list;
    GridLayoutManager gridLayoutManager;
    private FirebaseDatabase database;

    public BantuanLainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bantuan_lain, container, false);
        database = FirebaseDatabase.getInstance();
        //dummyData();
        getAllData();
        list = (RecyclerView) v.findViewById(R.id.recyclerViewdaftarBantuan);
        gridLayoutManager = new GridLayoutManager(BantuanLainFragment.this.getContext(), 2, GridLayoutManager.VERTICAL, false);
        return v;
    }

    private void getAllData() {
        database.getReference("admin").child("galang_dana").child("sudah_terverifikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    Identitas identitas = data.child("identitas").getValue(Identitas.class);
                    //Toast.makeText(DaftarUKMFragment.this.getContext(), berita.getJudul(), Toast.LENGTH_SHORT).show();
                    if (berita.getKategori().equals("Bencana Alam") || berita.getKategori().equals("Penyakit") || berita.getKategori().equals("Yatim Piatu") ) {
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        long selisih = 0;
                        try {
                            Date deadline = myFormat.parse(berita.getDeadline());
                            selisih = deadline.getTime() - date.getTime();
                        } catch (ParseException e) {
                            Log.e("parse error", e.getMessage());
                        }
                        if(selisih>=0) {
                            dataUKM.add(berita);
                            dataIdentitas.add(identitas);
                        }
                    }
                }
                adapter = new AdapterBerita(BantuanLainFragment.this.getContext(), dataUKM);
                list.setAdapter(adapter);
                list.setLayoutManager(gridLayoutManager);
                adapter.SetOnItemClickListener(new AdapterBerita.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Berita mu = dataUKM.get(position);
                        Identitas ident = dataIdentitas.get(position);
                        Bundle b = new Bundle();
                        b.putParcelable("ukm", mu);
                        b.putParcelable("identitas", ident);
                        Fragment f = new DetailDonasi();
                        f.setArguments(b);
                        FragmentTransaction ft = BantuanLainFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
                        TabLayout tabl = (TabLayout) BantuanLainFragment.this.getActivity().findViewById(R.id.tabs);
                        tabl.setVisibility(View.GONE);
                        ft.replace(R.id.mainframe, f);
                        ft.addToBackStack("BantuanLainFragment");
                        ft.commit();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error database", databaseError.getMessage());
            }
        });
    }


}
