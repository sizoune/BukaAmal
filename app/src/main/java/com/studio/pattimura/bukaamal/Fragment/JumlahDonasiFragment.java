package com.studio.pattimura.bukaamal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.Donasi;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Donation;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class JumlahDonasiFragment extends Fragment implements View.OnClickListener {
    private TextView dana, judul;
    private EditText jumlah;
    private Button lanjut;
    private Donasi d;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private ProgressDialog progressdialog;
    private Berita berita;
//    private String penggalangId;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userProfile userProfile;
    private userAuth userData;
    private Donation donasi;
    public static TabLayout tabLayout;

    public JumlahDonasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jumlah_donasi, container, false);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        preferences = this.getActivity().getSharedPreferences("prefProfile", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefProfile", "");
        userProfile = gson.fromJson(json, userProfile.class);

        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        d = (Donasi) getActivity();
        berita = d.getBerita();

        dana = (TextView) view.findViewById(R.id.txtDanaDonasi);

        judul = (TextView) view.findViewById(R.id.titleDonasi);

        jumlah = (EditText) view.findViewById(R.id.edJumlahDonasi);
        lanjut = (Button) view.findViewById(R.id.btnLanjutMetode);
        lanjut.setOnClickListener(this);
//        setPenggalangId();
        progressdialog = new ProgressDialog(this.getContext());

        if (d.getDonasi() != null) {
            setJumlah();
        }

        dana.setText("Rp " + berita.getDana_terkumpul() + " dana telah terkumpul, dari total " + "Rp " + berita.getDana());
        judul.setText(berita.getJudul());
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == lanjut) {
            if (!isValid())
                Toast.makeText(this.getContext(), "Jumlah Donasi Tidak Valid", Toast.LENGTH_SHORT).show();
            else {
                if (!isOnline())
                    Toast.makeText(this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                else {
                    if(d.getDonasi()!=null) {
                        if (!d.getDonasi().isStatus()) {
                            progressdialog.setMessage("Mohon tunggu...");
                            progressdialog.show();
                            Submit();
                        } else {
                            Toast.makeText(this.getContext(), "Data Sudah Diverifikasi Tidak Bisa Di Ubah", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressdialog.setMessage("Mohon tunggu...");
                        progressdialog.show();
                        Submit();
                    }
                }
            }
        }
    }

    @NonNull
    private Boolean isValid() {

        if (jumlah.getText().length() <= 0 || Long.parseLong(jumlah.getText().toString()) < 10000)
            return false;

        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void Submit() {
        final long id = Long.parseLong(berita.getId() + "1");
        Query query = mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long val = dataSnapshot.getChildrenCount();
                    donasi = new Donation(id + val, String.valueOf(berita.getId()), jumlah.getText().toString(), userData.getUser_id(), false);
                    mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi").child(String.valueOf(id + val)).setValue(donasi);
                    Donasi d = (Donasi) getActivity();
                    d.setJumlah(true);
                    d.setIdDonasi(id + val);
                    progressdialog.dismiss();
                    TabLayout.Tab tab = tabLayout.getTabAt(1);
                    tab.select();
                    mDatabase.getReference("admin").child("donasi").child("belum_terverifikasi").child(String.valueOf(id + val)).setValue(donasi);
                } else {
                    donasi = new Donation(id, String.valueOf(berita.getId()), jumlah.getText().toString(), userData.getUser_id(), false);
                    mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi").child(String.valueOf(id)).setValue(donasi);
                    Donasi d = (Donasi) getActivity();
                    d.setJumlah(true);
                    d.setIdDonasi(id);
                    progressdialog.dismiss();
                    TabLayout.Tab tab = tabLayout.getTabAt(1);
                    tab.select();
                    mDatabase.getReference("admin").child("donasi").child("belum_terverifikasi").child(String.valueOf(id)).setValue(donasi);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(JumlahDonasiFragment.this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
        });
    }

//    public void setPenggalangId() {
//        mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(berita.getId())).child("identitas").child("id").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    penggalangId = dataSnapshot.getValue(String.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void setJumlah() {
        jumlah.setText(d.getDonasi().getJumlah());
        berita = d.getDonasi().getDataBerita();
        d.setJumlah(true);
        d.setMetode(true);
    }
}
