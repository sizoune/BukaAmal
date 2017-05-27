package com.studio.pattimura.bukaamal.Fragment;


import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.Adapter.AdapterBerita;
import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarUKMFragment extends Fragment {
    private ArrayList<Berita> dataUKM = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitas = new ArrayList<>();
    private AdapterBerita adapter;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private userAuth userData;
    RecyclerView list;
    GridLayoutManager gridLayoutManager;

    public DaftarUKMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daftar_ukm, container, false);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getAllData();
        list = (RecyclerView) v.findViewById(R.id.recyclerViewdaftarUKM);
        gridLayoutManager = new GridLayoutManager(DaftarUKMFragment.this.getContext(), 2, GridLayoutManager.VERTICAL, false);
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
                    if (berita.getKategori().equals("Modal UKM")) {
                        dataUKM.add(berita);
                        dataIdentitas.add(identitas);
                    }
                }
                adapter = new AdapterBerita(DaftarUKMFragment.this.getContext(), dataUKM);
                list.setAdapter(adapter);
                list.setLayoutManager(gridLayoutManager);
                adapter.SetOnItemClickListener(new AdapterBerita.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Berita mu = dataUKM.get(position);
                        Identitas ident = dataIdentitas.get(position);
                        Bundle b = new Bundle();
                        b.putParcelable("ukm", mu);
                        b.putParcelable("identitas",ident);
                        Fragment f = new DetailDonasi();
                        f.setArguments(b);
                        FragmentTransaction ft = DaftarUKMFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
                        TabLayout tabl = (TabLayout) DaftarUKMFragment.this.getActivity().findViewById(R.id.tabs);
                        tabl.setVisibility(View.GONE);
                        ft.replace(R.id.mainframe, f);
                        ft.addToBackStack("DaftarUKMFragment");
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
