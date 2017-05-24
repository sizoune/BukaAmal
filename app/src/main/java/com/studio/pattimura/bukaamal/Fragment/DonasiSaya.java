package com.studio.pattimura.bukaamal.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.Adapter.AdapterDonasiSaya;
import com.studio.pattimura.bukaamal.Adapter.AdapterGalangDanaSaya;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonasiSaya extends Fragment {
    private ArrayList<com.studio.pattimura.bukaamal.Model.DonasiSaya> dataDonasi = new ArrayList<>();
    private ArrayList<Berita> dataBerita = new ArrayList<>();
    private AdapterDonasiSaya adapter;
    private RecyclerView list;
    private GridLayoutManager gridLayoutManager;
    private FirebaseDatabase database;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userAuth userData;
    private userProfile profileData;
    private int ix, iy;

    public DonasiSaya() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donasi_saya, container, false);

        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        database = FirebaseDatabase.getInstance();

        list = (RecyclerView) v.findViewById(R.id.recyclerViewdonasisaya);
        gridLayoutManager = new GridLayoutManager(DonasiSaya.this.getContext(), 1, GridLayoutManager.VERTICAL, false);
        ix = 0;
        iy = 0;
        getAllData();
        return v;
    }

    private void getAllData() {
        Query query = database.getReference("user").child("profil").child(userData.getUser_id()).child("donasi");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    com.studio.pattimura.bukaamal.Model.DonasiSaya donasi = data.getValue(com.studio.pattimura.bukaamal.Model.DonasiSaya.class);
                    dataDonasi.add(donasi);
                    ix++;
                }
                getDataBerita();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Error", databaseError.getMessage());
            }
        });
    }

    private void getDataBerita() {
        for (final com.studio.pattimura.bukaamal.Model.DonasiSaya datadonasi : dataDonasi) {
            Query query = database.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(datadonasi.getId_berita_galang());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Berita berita = dataSnapshot.child("berita").getValue(Berita.class);
                        datadonasi.addBerita(berita);
                        iy++;
                        //Toast.makeText(DonasiSaya.this.getContext(), datadonasi.getDataBerita().getJudul(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Database Error1", databaseError.getMessage());
                }
            });
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (iy == ix) {
                    adapter = new AdapterDonasiSaya(DonasiSaya.this.getContext(), dataDonasi);
                    list.setAdapter(adapter);
                    list.setLayoutManager(gridLayoutManager);
                }
            }
        }, 5000);
    }

}
