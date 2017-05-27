package com.studio.pattimura.bukaamal.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.AdapterGaleri;
import com.studio.pattimura.bukaamal.Donasi;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.R.attr.fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDonasi extends Fragment {
    private NumberProgressBar bnp;
    //    private AdapterGaleri adapter;
//    private ArrayList<Galeri> dataGambar = new ArrayList<>();
    private Button donasi;
    private StorageReference mStorageref;
    private Berita mu;
    private Identitas identitas;

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
            final ImageView cover = (ImageView) v.findViewById(R.id.coverDetailDonasi);
            TextView danaterkumpul = (TextView) v.findViewById(R.id.txtSudahTerkumupl);
            TextView persen = (TextView) v.findViewById(R.id.txtpersen);
            TextView sisahari = (TextView) v.findViewById(R.id.txtsisahari);
            TextView judul = (TextView) v.findViewById(R.id.txtJudulDonasi);
            TextView desc = (TextView) v.findViewById(R.id.txtDescDonasi);
            TextView nama = (TextView) v.findViewById(R.id.txtPenggalang);
            TextView target = (TextView) v.findViewById(R.id.txtTargetDonasi);
            ImageView avatar = (ImageView) v.findViewById(R.id.profile_detailDonasi);
            donasi = (Button) v.findViewById(R.id.btnDonasiSekarang);
            bnp = (NumberProgressBar) v.findViewById(R.id.numberbar5);
            if (b.getParcelable("ukm") != null) {
                mu = b.getParcelable("ukm");
                identitas = b.getParcelable("identitas");
                judul.setText(mu.getJudul());
                desc.setText(mu.getDeskripsi());
                mStorageref = FirebaseStorage.getInstance().getReference(mu.getFoto());
                mStorageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(DetailDonasi.this.getContext()).load(uri).fit().into(cover);
                    }
                });
                nama.setText(identitas.getNama());
                Picasso.with(this.getContext()).load(identitas.getAvatar()).fit().into(avatar);
                danaterkumpul.setText("Rp " + mu.getDana_terkumpul());
                target.setText("Rp " + mu.getDana());
                double d = (double) mu.getDana_terkumpul() / mu.getDana();

                if (d * 100 < 100) {
                    bnp.setProgress((int) Math.round((d) * 100));
                    persen.setText(String.format("%.2f", ((d) * 100)) + "%");
                } else {
                    bnp.setProgress(100);
                    persen.setText("100%");
                }
                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                try {
                    Date deadline = myFormat.parse(mu.getDeadline());
                    long selisih = deadline.getTime() - date.getTime();
                    sisahari.setText(Long.toString(TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS)));
                } catch (ParseException e) {
                    Log.e("parse error", e.getMessage());
                }
                //dataGambar = mu.getGambar();
            }
//            LinearLayoutManager layoutManager
//                    = new LinearLayoutManager(DetailDonasi.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
//            RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerViewDonasi);
//            adapter = new AdapterGaleri(DetailDonasi.this.getContext(), dataGambar);
////            Toast.makeText(DetailDonasi.this.getContext(), Integer.toString(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
//            rv.setAdapter(adapter);
//            rv.setLayoutManager(layoutManager);
            donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    TabLayout tabl = (TabLayout) DetailDonasi.this.getActivity().findViewById(R.id.tabs);
//                    tabl.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(DetailDonasi.this.getContext(), Donasi.class);
                    intent.putExtra("Berita", mu);
                    startActivity(intent);
//                    Fragment fragment = new DonasiSekarangFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.mainframe, fragment);
//                    fragmentTransaction.commit();
                }
            });
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("gif--","fragment back key is clicked");
                    getActivity().getSupportFragmentManager().popBackStack("BerandaFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }


}
