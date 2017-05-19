package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.AdapterGaleri;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDonasi extends Fragment{
    private NumberProgressBar bnp;
    private AdapterGaleri adapter;
    private ArrayList<Galeri> dataGambar = new ArrayList<>();
    private Button donasi;

    public DetailDonasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_donasi, container, false);
        Bundle b = this.getArguments();
        if (b != null) {
            ImageView cover = (ImageView) v.findViewById(R.id.coverDetailDonasi);
            TextView danaterkumpul = (TextView) v.findViewById(R.id.txtSudahTerkumupl);
            TextView persen = (TextView) v.findViewById(R.id.txtpersen);
            TextView sisahari = (TextView) v.findViewById(R.id.txtsisahari);
            TextView judul = (TextView) v.findViewById(R.id.txtJudulDonasi);
            TextView desc = (TextView) v.findViewById(R.id.txtDescDonasi);
            donasi = (Button) v.findViewById(R.id.btnDonasiSekarang);
            bnp = (NumberProgressBar) v.findViewById(R.id.numberbar5);
            if (b.getParcelable("ukm") != null) {
                ModalUKM mu = b.getParcelable("ukm");
                judul.setText(mu.getJudul());
                desc.setText(mu.getDeskripsi());
                Picasso.with(DetailDonasi.this.getContext()).load(mu.getGambar().get(0).getGambar()).fit().into(cover);
                danaterkumpul.setText("Rp." + mu.getDanaterkumpul());
                bnp.setProgress(Math.round(mu.getPersen()));
                persen.setText(Integer.toString(Math.round(mu.getPersen())) + "%");
                dataGambar = mu.getGambar();
            } else {
                BantuanLain mu = b.getParcelable("bantuan");
                judul.setText(mu.getJudul());
                desc.setText(mu.getDeskripsi());
                Picasso.with(DetailDonasi.this.getContext()).load(mu.getGambar().get(0).getGambar()).fit().into(cover);
                danaterkumpul.setText("Rp." + mu.getDanaterkumpul());
                bnp.setProgress(Math.round(mu.getPersen()));
                persen.setText(Integer.toString(Math.round(mu.getPersen())) + "%");
                dataGambar = mu.getGambar();
            }
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(DetailDonasi.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
            RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerViewDonasi);
            adapter = new AdapterGaleri(DetailDonasi.this.getContext(), dataGambar);
//            Toast.makeText(DetailDonasi.this.getContext(), Integer.toString(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
            rv.setAdapter(adapter);
            rv.setLayoutManager(layoutManager);
            donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TabLayout tabl = (TabLayout) DetailDonasi.this.getActivity().findViewById(R.id.tabs);
                    tabl.setVisibility(View.VISIBLE);
                    Fragment fragment = new DonasiSekarangFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainframe, fragment);
                    fragmentTransaction.commit();
                }
            });
        }

        return v;
    }


}
