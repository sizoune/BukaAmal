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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;

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
        editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
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
                    if (berita.getKategori().equals("Bencana Alam") || berita.getKategori().equals("Penyakit") || berita.getKategori().equals("Yatim Piatu")) {
                        dataUKM.add(berita);
                        dataIdentitas.add(identitas);
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
                        editor.putString("TAG", "BantuanLainFragment");
                        editor.commit();
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

//    void dummyData() {
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung1", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung2", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung3", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung4", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung5", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung1", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung2", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung3", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung4", 5000000, 80));
//        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
//                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung5", 5000000, 80));
//
//        dataBantuan.get(0).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(0).getGambar().add(new Galeri("gunung", "https://cdn.sindonews.net/dyn/620/content/2015/07/14/149/1023483/gunung-berapi-dengan-letusan-paling-dahsyat-oD5.jpg"));
//
//        dataBantuan.get(1).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(2).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(3).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(4).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(5).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(6).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(7).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(8).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//        dataBantuan.get(9).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
//    }

}
