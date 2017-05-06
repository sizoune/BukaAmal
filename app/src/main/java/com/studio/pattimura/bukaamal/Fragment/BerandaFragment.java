package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.BantuanLainAdapter;
import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    private ArrayList<ModalUKM> dataUKM = new ArrayList<>();
    private ModalUKMAdapter adapter;
    private ArrayList<BantuanLain> dataBantuan = new ArrayList<>();
    private BantuanLainAdapter adapter1;

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
        dummyData();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView rv1 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(BerandaFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(BerandaFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new ModalUKMAdapter(dataUKM, BerandaFragment.this.getContext());
        adapter1 = new BantuanLainAdapter(dataBantuan, BerandaFragment.this.getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
        rv1.setAdapter(adapter1);
        rv1.setLayoutManager(layoutManager1);
        adapter.SetOnItemClickListener(new ModalUKMAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BerandaFragment.this.getContext(), dataUKM.get(position).getDeskripsi(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter1.SetOnItemClickListener(new BantuanLainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BerandaFragment.this.getContext(), dataBantuan.get(position).getDeskripsi(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    void dummyData() {
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih1", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih2", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih3", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih4", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));

        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung1", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung2", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung3", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung4", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung5", 5000000, 80));

    }

}
