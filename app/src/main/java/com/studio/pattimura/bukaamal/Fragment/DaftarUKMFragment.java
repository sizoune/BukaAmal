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
        database.getReference("admin").child("galang_dana").child("belum_terverifikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    //Toast.makeText(DaftarUKMFragment.this.getContext(), berita.getJudul(), Toast.LENGTH_SHORT).show();
                    if (berita.getKategori().equals("Modal UKM")) {
                        dataUKM.add(berita);
                    }
                }
                adapter = new AdapterBerita(DaftarUKMFragment.this.getContext(), dataUKM);
                list.setAdapter(adapter);
                list.setLayoutManager(gridLayoutManager);
                adapter.SetOnItemClickListener(new AdapterBerita.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Berita mu = dataUKM.get(position);
                        Bundle b = new Bundle();
                        b.putParcelable("ukm", mu);
                        Fragment f = new DetailDonasi();
                        f.setArguments(b);
                        FragmentTransaction ft = DaftarUKMFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
                        TabLayout tabl = (TabLayout) DaftarUKMFragment.this.getActivity().findViewById(R.id.tabs);
                        tabl.setVisibility(View.GONE);
                        ft.replace(R.id.mainframe, f);
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

//    void dummyData() {
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih1", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih2", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih3", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih4", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
//        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
//
//        dataUKM.get(0).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(0).getGambar().add(new Galeri("air bersih", "http://fujiro.com/wp-content/uploads/2014/01/82041-Galon-1-770x470.jpg"));
//        dataUKM.get(0).getGambar().add(new Galeri("air sehat", "http://www.nicofilter.co.id/wp-content/uploads/2014/11/Manfaat-Air-Minum-Sehat-Water-Filter-untuk-Kesehatan.jpg"));
//
//        dataUKM.get(1).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(2).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(3).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(4).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(5).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(6).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(7).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//        dataUKM.get(8).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
//
//
//    }


}
