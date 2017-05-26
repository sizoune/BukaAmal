package com.studio.pattimura.bukaamal.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
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
public class MetodePembayaranFragment extends Fragment implements View.OnClickListener {
    private TextView judul;
    private RadioButton bca, bni, bri, mandiri;
    private Button lanjut;
    private Donasi d;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private ProgressDialog progressdialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userProfile userProfile;
    private userAuth userData;
    private Donation donasi;
    private String bank;
    private Berita berita;
    public static TabLayout tabLayout;

    public MetodePembayaranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metode_pembayaran, container, false);

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

        judul = (TextView) view.findViewById(R.id.titleDonasi);
        bca = (RadioButton) view.findViewById(R.id.rbBca);
        bni = (RadioButton) view.findViewById(R.id.rbBni);
        bri = (RadioButton) view.findViewById(R.id.rbBri);
        mandiri = (RadioButton) view.findViewById(R.id.rbMandiri);
        lanjut = (Button) view.findViewById(R.id.btnLanjutPemabayaran);
        lanjut.setOnClickListener(this);

        progressdialog = new ProgressDialog(this.getContext());

        if (d.getDonasi() != null) {
            setMetode();
        }
        judul.setText(berita.getJudul());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == lanjut) {
            if (!isValid())
                Toast.makeText(this.getContext(), "Pilih Metode Pembayaran Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            else {
                if (!isOnline())
                    Toast.makeText(this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                else {
                    if (d.getDonasi() != null) {
                        if (!d.getDonasi().isStatus()) {
                            progressdialog.setMessage("Mohon tunggu...");
                            progressdialog.show();
                            if (bri.isChecked())
                                bank = "BRI";
                            if (bni.isChecked())
                                bank = "BNI";
                            if (bca.isChecked())
                                bank = "BCA";
                            if (mandiri.isChecked())
                                bank = "MANDIRI";
                            Submit(bank);
                        } else {
                            Toast.makeText(this.getContext(), "Data Sudah Diverifikasi Tidak Bisa Di Ubah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressdialog.setMessage("Mohon tunggu...");
                        progressdialog.show();
                        if (bri.isChecked())
                            bank = "BRI";
                        if (bni.isChecked())
                            bank = "BNI";
                        if (bca.isChecked())
                            bank = "BCA";
                        if (mandiri.isChecked())
                            bank = "MANDIRI";
                        Submit(bank);
                    }

                }
            }
        }
    }

    @NonNull
    private Boolean isValid() {
        RadioButton[] fields = {bri, bni, bca, mandiri};
        for (RadioButton e : fields) {
            if (e.isChecked()) return true;
        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void Submit(String bank) {
        final String bk = bank;
        mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi").child(String.valueOf(d.getIdDonasi())).child("metode_bayar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi").child(String.valueOf(d.getIdDonasi())).child("metode_bayar").setValue(bk);
                    d.setMetode(true);
                    d.setBank(bk);
                    progressdialog.dismiss();
                    TabLayout.Tab tab = tabLayout.getTabAt(2);
                    tab.select();
                    mDatabase.getReference("admin").child("donasi").child("belum_terverifikasi").child(String.valueOf(d.getIdDonasi())).child("metode_bayar").setValue(bk);
                } else {
                    mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("donasi").child(String.valueOf(d.getIdDonasi())).child("metode_bayar").setValue(bk);
                    d.setMetode(true);
                    d.setBank(bk);
                    progressdialog.dismiss();
                    TabLayout.Tab tab = tabLayout.getTabAt(2);
                    tab.select();
                    mDatabase.getReference("admin").child("donasi").child("belum_terverifikasi").child(String.valueOf(d.getIdDonasi())).child("metode_bayar").setValue(bk);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setMetode() {
        if (d.getDonasi().getMetode_bayar().equals("BCA"))
            bca.setChecked(true);
        else if (d.getDonasi().getMetode_bayar().equals("BRI"))
            bri.setChecked(true);
        else if (d.getDonasi().getMetode_bayar().equals("BNI"))
            bni.setChecked(true);
        else if (d.getDonasi().getMetode_bayar().equals("MANDIRI"))
            mandiri.setChecked(true);
        berita = d.getDonasi().getDataBerita();
        d.setMetode(true);
    }
}
