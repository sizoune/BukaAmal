package com.studio.pattimura.bukaamal.Fragment;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailIdentitasFragment extends Fragment {
    private TextView nama,alamat,email,telepon,bank,rekening;
    private ImageView ktp;
    private Bundle b;
    private Identitas identitas;
    private StorageReference mStorageRef;

    public DetailIdentitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_identitas, container, false);
        nama = (TextView) view.findViewById(R.id.txtNama);
        alamat = (TextView) view.findViewById(R.id.txtAlamat);
        email = (TextView) view.findViewById(R.id.txtEmail);
        telepon = (TextView) view.findViewById(R.id.txtTelepon);
        bank = (TextView) view.findViewById(R.id.txtBank);
        rekening = (TextView) view.findViewById(R.id.txtRekening);
        ktp = (ImageView) view.findViewById(R.id.gambarKTP);

        b = this.getActivity().getIntent().getExtras();
        if(b!=null){
            identitas = b.getParcelable("identitas");
            nama.setText(identitas.getNama());
            alamat.setText(identitas.getAlamat());
            email.setText(identitas.getEmail());
            telepon.setText(identitas.getTelepon());
            bank.setText(identitas.getBank());
            rekening.setText(identitas.getRekening());
            mStorageRef = FirebaseStorage.getInstance().getReference(identitas.getKtp());
            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(DetailIdentitasFragment.this.getContext().getApplicationContext()).load(uri).fit().into(ktp);
                }
            });
        }
        return view;
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
                    Log.e("gif--", "fragment back key is clicked");
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    RelativeLayout r = (RelativeLayout) getActivity().findViewById(R.id.rel);
                    r.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

}
