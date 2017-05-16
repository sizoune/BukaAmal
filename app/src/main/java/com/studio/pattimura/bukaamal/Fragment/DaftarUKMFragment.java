package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.Galeri;
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
                ModalUKM mu = dataUKM.get(position);
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
        return v;
    }

    void dummyData() {
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih1", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih2", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih3", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih4", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));
        dataUKM.add(new ModalUKM("10 Hari Lagi", "Bantuan Air Bersih",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman dalam mendapatkan air bersih5", 8000000, 50));

        dataUKM.get(0).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(0).getGambar().add(new Galeri("air bersih", "http://fujiro.com/wp-content/uploads/2014/01/82041-Galon-1-770x470.jpg"));
        dataUKM.get(0).getGambar().add(new Galeri("air sehat", "http://www.nicofilter.co.id/wp-content/uploads/2014/11/Manfaat-Air-Minum-Sehat-Water-Filter-untuk-Kesehatan.jpg"));

        dataUKM.get(1).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(2).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(3).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(4).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(5).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(6).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(7).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(8).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));


    }


}
