package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarUKMFragment extends Fragment {
    private ArrayList<ModalUKM> dataUKM = new ArrayList<>();
    private ModalUKMAdapter adapter;

    public DaftarUKMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daftar_ukm, container, false);
        dummyData();
        RecyclerView list = (RecyclerView) v.findViewById(R.id.recyclerViewdaftarUKM);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DaftarUKMFragment.this.getContext(), 2, GridLayoutManager.VERTICAL, false);
        adapter = new ModalUKMAdapter(dataUKM, DaftarUKMFragment.this.getContext());
        list.setAdapter(adapter);
        list.setLayoutManager(gridLayoutManager);
        adapter.SetOnItemClickListener(new ModalUKMAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DaftarUKMFragment.this.getContext(), dataUKM.get(position).getDeskripsi(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
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
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih6", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih7", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih8", 8000000, 50));
        dataUKM.add(new ModalUKM("https://assets.kitabisa.com/images/banner-child.png", "10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih9", 8000000, 50));



    }


}
