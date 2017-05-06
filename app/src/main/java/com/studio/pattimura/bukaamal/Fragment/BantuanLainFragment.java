package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.Adapter.BantuanLainAdapter;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanLainFragment extends Fragment {
    private ArrayList<BantuanLain> dataBantuan = new ArrayList<>();
    private BantuanLainAdapter adapter1;

    public BantuanLainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bantuan_lain, container, false);
        dummyData();
        RecyclerView list = (RecyclerView) v.findViewById(R.id.recyclerViewdaftarBantuan);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BantuanLainFragment.this.getContext(), 2, GridLayoutManager.VERTICAL, false);
        adapter1 = new BantuanLainAdapter(dataBantuan, BantuanLainFragment.this.getContext());
        list.setAdapter(adapter1);
        list.setLayoutManager(gridLayoutManager);
        adapter1.SetOnItemClickListener(new BantuanLainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BantuanLainFragment.this.getContext(), dataBantuan.get(position).getDeskripsi(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    void dummyData() {
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
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung6", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung7", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung8", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung9", 5000000, 80));
        dataBantuan.add(new BantuanLain("https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg", "10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung10", 5000000, 80));

    }

}
