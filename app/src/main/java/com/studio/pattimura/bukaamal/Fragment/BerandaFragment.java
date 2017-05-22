package com.studio.pattimura.bukaamal.Fragment;


import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.BantuanLainAdapter;
import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Galeri;
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
    private Button ukmlengkap, bantuanlengkap;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ImageView cover = (ImageView) view.findViewById(R.id.coverBeranda);
        Picasso.with(BerandaFragment.this.getContext()).load(R.drawable.coverberanda).fit().into(cover);
        dummyData();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView rv1 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        ukmlengkap = (Button) view.findViewById(R.id.btnSelengkapnyaUKM);
        bantuanlengkap = (Button) view.findViewById(R.id.btnSelengkapnyabencana);
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
                //Toast.makeText(BerandaFragment.this.getContext(), dataUKM.get(position).getDeskripsi(), Toast.LENGTH_SHORT).show();
                ModalUKM mu = dataUKM.get(position);
                Bundle b = new Bundle();
                b.putParcelable("ukm", mu);
                Fragment f = new DetailDonasi();
                f.setArguments(b);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe, f);
                ft.commit();
                TabLayout tabl = (TabLayout) BerandaFragment.this.getActivity().findViewById(R.id.tabs);
                NavigationView navigationView = (NavigationView) BerandaFragment.this.getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.donasi);
                Toolbar toolbar = (Toolbar) BerandaFragment.this.getActivity().findViewById(R.id.toolbar);
                ImageView cover = (ImageView) toolbar.findViewById(R.id.logobuka);
                TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
                cover.setVisibility(View.GONE);
                judul.setText("Donasi");
//                tabl.setVisibility(View.VISIBLE);
            }
        });
        adapter1.SetOnItemClickListener(new BantuanLainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BantuanLain mu = dataBantuan.get(position);
                Bundle b = new Bundle();
                b.putParcelable("bantuan", mu);
                Fragment f = new DetailDonasi();
                f.setArguments(b);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe, f);
                ft.commit();
                ft.addToBackStack(null);
                TabLayout tabl = (TabLayout) BerandaFragment.this.getActivity().findViewById(R.id.tabs);
                NavigationView navigationView = (NavigationView) BerandaFragment.this.getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.donasi);
                Toolbar toolbar = (Toolbar) BerandaFragment.this.getActivity().findViewById(R.id.toolbar);
                ImageView cover = (ImageView) toolbar.findViewById(R.id.logobuka);
                TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
                cover.setVisibility(View.GONE);
                judul.setText("Donasi");
//                tabl.setVisibility(View.GONE);
            }
        });
        ukmlengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new DonasiFragment();
                FragmentTransaction ft = BerandaFragment.this.getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe, f);
                ft.commit();
                TabLayout tabl = (TabLayout) BerandaFragment.this.getActivity().findViewById(R.id.tabs);
                NavigationView navigationView = (NavigationView) BerandaFragment.this.getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.donasi);
                Toolbar toolbar = (Toolbar) BerandaFragment.this.getActivity().findViewById(R.id.toolbar);
                ImageView cover = (ImageView) toolbar.findViewById(R.id.logobuka);
                TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
                cover.setVisibility(View.GONE);
                judul.setText("Donasi");
                tabl.setVisibility(View.VISIBLE);
            }
        });
        bantuanlengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("1", 2);
                Fragment f = new DonasiFragment();
                FragmentTransaction ft = BerandaFragment.this.getFragmentManager().beginTransaction();
                f.setArguments(b);
                ft.replace(R.id.mainframe, f);
                ft.commit();
                TabLayout tabl = (TabLayout) BerandaFragment.this.getActivity().findViewById(R.id.tabs);
                NavigationView navigationView = (NavigationView) BerandaFragment.this.getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.donasi);
                Toolbar toolbar = (Toolbar) BerandaFragment.this.getActivity().findViewById(R.id.toolbar);
                ImageView cover = (ImageView) toolbar.findViewById(R.id.logobuka);
                TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
                cover.setVisibility(View.GONE);
                judul.setText("Donasi");
                tabl.setVisibility(View.VISIBLE);
            }
        });
        return view;
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

        dataUKM.get(0).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(0).getGambar().add(new Galeri("air bersih", "http://fujiro.com/wp-content/uploads/2014/01/82041-Galon-1-770x470.jpg"));
        dataUKM.get(0).getGambar().add(new Galeri("air sehat", "http://www.nicofilter.co.id/wp-content/uploads/2014/11/Manfaat-Air-Minum-Sehat-Water-Filter-untuk-Kesehatan.jpg"));

        dataUKM.get(1).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(2).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(3).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));
        dataUKM.get(4).getGambar().add(new Galeri("air minum", "https://assets.kitabisa.com/images/banner-child.png"));

        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung1", 5000000, 80));
        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung2", 5000000, 80));
        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung3", 5000000, 80));
        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung4", 5000000, 80));
        dataBantuan.add(new BantuanLain("10 Hari Lagi", "Bantuan Gunung Meletus",
                "Dibutuhkan bantuan dalam bentuk apapun untuk membantu desa pedalaman setelah terkena dampak gunung5", 5000000, 80));

        dataBantuan.get(0).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
        dataBantuan.get(0).getGambar().add(new Galeri("gunung", "https://cdn.sindonews.net/dyn/620/content/2015/07/14/149/1023483/gunung-berapi-dengan-letusan-paling-dahsyat-oD5.jpg"));

        dataBantuan.get(1).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
        dataBantuan.get(2).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
        dataBantuan.get(3).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
        dataBantuan.get(4).getGambar().add(new Galeri("gunung merapi", "https://i1.wp.com/obatrindu.com/wp-content/uploads/2017/01/bencana-alam-gunung-meletus.jpg"));
    }

}
