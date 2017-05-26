package com.studio.pattimura.bukaamal.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Donasi;
import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PembayaranFragment extends Fragment implements View.OnClickListener {
    private TextView judul, metode, bank, norek;
    private ImageView imlogo;
    private Button bayar;
    private Donasi d;


    public PembayaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pembayaran, container, false);

        judul = (TextView) view.findViewById(R.id.titleDonasi);
        metode = (TextView) view.findViewById(R.id.txtTrans);
        bank = (TextView) view.findViewById(R.id.txtBank);
        norek = (TextView) view.findViewById(R.id.txtNoRek);
        bayar = (Button) view.findViewById(R.id.btnSudahBayar);
        imlogo = (ImageView) view.findViewById(R.id.imgMetode);
        d = (Donasi) getActivity();

        judul.setText(d.getBerita().getJudul());
        if (d.getDonasi() != null) {
            bank.setText("Bank " + d.getDonasi().getMetode_bayar());
            if (d.getDonasi().getMetode_bayar().equals("BCA"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bca_logo).fit().into(imlogo);
            else if (d.getDonasi().getMetode_bayar().equals("BNI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bni_logo).fit().into(imlogo);
            else if (d.getDonasi().getMetode_bayar().equals("BRI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bri_logo).fit().into(imlogo);
            else if (d.getDonasi().getMetode_bayar().equals("MANDIRI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.mandiri_logo).fit().into(imlogo);
        } else {
            bank.setText("Bank " + d.getBank());
            if (d.getBank().equals("BCA"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bca_logo).fit().into(imlogo);
            else if (d.getBank().equals("BNI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bni_logo).fit().into(imlogo);
            else if (d.getBank().equals("BRI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.bri_logo).fit().into(imlogo);
            else if (d.getBank().equals("MANDIRI"))
                Picasso.with(PembayaranFragment.this.getContext()).load(R.drawable.mandiri_logo).fit().into(imlogo);
        }

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
