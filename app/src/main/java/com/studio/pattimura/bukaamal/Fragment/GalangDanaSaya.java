package com.studio.pattimura.bukaamal.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
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
public class GalangDanaSaya extends Fragment {
    private ArrayList<Berita> databerita = new ArrayList<>();
    private AdapterGalangDanaSaya adapter;
    private RecyclerView list;
    private GridLayoutManager gridLayoutManager;
    private FirebaseDatabase database;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userAuth userData;
    private userProfile profileData;

    public GalangDanaSaya() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);



        View v = inflater.inflate(R.layout.fragment_galang_dana_saya, container, false);
        database = FirebaseDatabase.getInstance();

        list = (RecyclerView) v.findViewById(R.id.recyclerViewgalangdanasaya);
        gridLayoutManager = new GridLayoutManager(GalangDanaSaya.this.getContext(), 1, GridLayoutManager.VERTICAL, false);
        getAllData();
        return v;
    }

    private void getAllData() {
        Query query = database.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    databerita.add(berita);
                }
                adapter = new AdapterGalangDanaSaya(GalangDanaSaya.this.getContext(), databerita);
                list.setAdapter(adapter);
                list.setLayoutManager(gridLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
