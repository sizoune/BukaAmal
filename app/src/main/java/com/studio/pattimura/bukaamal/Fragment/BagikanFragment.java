package com.studio.pattimura.bukaamal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.GalangDana;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BagikanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private com.studio.pattimura.bukaamal.Model.userProfile userProfile;
    private userAuth userData;
    private ProgressDialog progressdialog;
    public static TabLayout tabLayout;
    private SwipeRefreshLayout swipe;
    private Berita berita;
    private TextView judul, dana, deadline, kategori, lokasi, status;
    private GalangDana g;
    long id;

    public BagikanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bagikan, container, false);
        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        judul = (TextView) view.findViewById(R.id.txtJudul);
        dana = (TextView) view.findViewById(R.id.txtDana);
        deadline = (TextView) view.findViewById(R.id.txtDeadline);
        kategori = (TextView) view.findViewById(R.id.txtKategori);
        lokasi = (TextView) view.findViewById(R.id.txtLokasi);
        status = (TextView) view.findViewById(R.id.txtStatus);

        g = (GalangDana) getActivity();

        if (g.getIdBerita() != 0)
            id = g.getIdBerita();
        else
            id = g.getBerita().getId();

        setBerita();
        if (g.getBerita() != null) {
            berita = g.getBerita();
            setDetailVerif();
        } else {
            swipe.post(new Runnable() {
                           @Override
                           public void run() {
                               swipe.setRefreshing(true);
                               swipe.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       setBerita();
                                   }
                               }, 30000);
                               if (!isOnline())
                                   Toast.makeText(BagikanFragment.this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                               else
                                   setDetailVerif();
                               swipe.setRefreshing(false);
                           }
                       }
            );
        }
        return view;
    }

    @Override
    public void onRefresh() {
        swipe.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBerita();
            }
        }, 30000);
        if (!isOnline())
            Toast.makeText(this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
        else
            setDetailVerif();
        swipe.setRefreshing(false);
    }

    public void setBerita() {
        mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("berita").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    berita = dataSnapshot.getValue(Berita.class);
                } else {
                    berita = null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BagikanFragment.this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setDetailVerif() {
        if (berita != null) {
            judul.setText(berita.getJudul().toString());
            dana.setText("Rp " + String.valueOf(berita.getDana()));
            deadline.setText(berita.getDeadline().toString());
            kategori.setText(berita.getKategori().toString());
            lokasi.setText(berita.getLokasi().toString());
            if (berita.getStatus()) {
                status.setText("Disetujui");
                status.setTextColor(Color.GREEN);
                g.setVerifikasiTrue(true);
            } else {
                status.setText("Menunggu");
                status.setTextColor(Color.RED);
            }
        } else {
            judul.setText("");
            dana.setText("");
            deadline.setText("");
            kategori.setText("");
            lokasi.setText("");
            status.setText("");
        }
        swipe.setRefreshing(false);
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}