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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.BantuanLainAdapter;
import com.studio.pattimura.bukaamal.Adapter.ModalUKMAdapter;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.Model.topUser;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    private ArrayList<Berita> dataUKM = new ArrayList<>();
    private ModalUKMAdapter adapter;
    private ArrayList<Berita> dataBantuan = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitasUKM = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitasBantuan = new ArrayList<>();
    private BantuanLainAdapter adapter1;
    private Button ukmlengkap, bantuanlengkap;
    private FirebaseDatabase mDatabase;
    private ImageView avatar;
    private TextView nama, total, top;
    private String id_user;
    private long jumlah_donasi;
    private String judul;
    private long dana_terkumpul;
    private Berita berita, beritaTemp;
    private topUser userTop, userTopTemp;
    RecyclerView rv;
    RecyclerView rv1;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        mDatabase = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        avatar = (ImageView) view.findViewById(R.id.profile_imageBeranda);
        nama = (TextView) view.findViewById(R.id.txtNamaDonatur1);
        total = (TextView) view.findViewById(R.id.txtallTotalDonate);
        top = (TextView) view.findViewById(R.id.txtTopDonasi);
        total.setText("Memuat...");
        nama.setText("Memuat...");
        top.setText("Memuat...");
        getData();
        ImageView cover = (ImageView) view.findViewById(R.id.coverBeranda);
        Picasso.with(BerandaFragment.this.getContext()).load(R.drawable.coverberanda).fit().into(cover);

        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv1 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        ukmlengkap = (Button) view.findViewById(R.id.btnSelengkapnyaUKM);
        bantuanlengkap = (Button) view.findViewById(R.id.btnSelengkapnyabencana);


        return view;
    }

    public void getData() {
        mDatabase.getReference("admin").child("total_donasi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total.setText("Rp " + String.valueOf(dataSnapshot.getValue(long.class)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BerandaFragment.this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.getReference("admin").child("top_user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userTopTemp =new topUser();
                userTopTemp.setJumlah_donasi(0);
                userTopTemp.setId_user("");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datai : dataSnapshot.getChildren()) {
                            userTop=datai.getValue(topUser.class);
                            if(userTop.getJumlah_donasi()>userTopTemp.getJumlah_donasi())
                                userTopTemp=userTop;
                    }
                } else {
                    Toast.makeText(BerandaFragment.this.getActivity(), "ga ada", Toast.LENGTH_SHORT).show();
                }
                if (!userTopTemp.getId_user().equals("")) {
                    requestProfile(userTopTemp.getId_user());
                } else {
                    nama.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.getReference("admin").child("galang_dana").child("sudah_terverifikasi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                beritaTemp = new Berita();
                beritaTemp.setDana_terkumpul(0);
                beritaTemp.setJudul("");
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datai : dataSnapshot.getChildren()) {
                        Berita b = datai.child("berita").getValue(Berita.class);
                        Identitas i = datai.child("identitas").getValue(Identitas.class);
                        if (b.getKategori().equals("Modal UKM")) {
                            dataUKM.add(b);
                            dataIdentitasUKM.add(i);
                        } else {
                            dataBantuan.add(b);
                            dataIdentitasBantuan.add(i);
                        }
                        for (DataSnapshot dataj : datai.getChildren()) {
                            if (dataj.exists()) {
                                try {
                                    berita = dataj.getValue(Berita.class);
                                    if (berita.getDana_terkumpul() > beritaTemp.getDana_terkumpul())
                                        beritaTemp = berita;
//                                    Berita b = dataj.getValue(Berita.class);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d("modalukm", "onDataChange: "+e);
                                }
                            }
                        }
                    }
                }
                if (!beritaTemp.getJudul().equals("")) {
                    top.setText(beritaTemp.getJudul());
                    Log.d("jumlahukm", "onDataChange: "+dataUKM.size());
                } else {
                    top.setText("");
                }
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
                        Berita mu = dataUKM.get(position);
                        Identitas ident = dataIdentitasUKM.get(position);
                        Bundle b = new Bundle();
                        b.putParcelable("ukm", mu);
                        b.putParcelable("identitas",ident);
                        Fragment f = new DetailDonasi();
                        f.setArguments(b);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.mainframe, f);
                        ft.addToBackStack("BerandaFragment");
                        ft.commit();
                        TabLayout tabl = (TabLayout) BerandaFragment.this.getActivity().findViewById(R.id.tabs);
                        NavigationView navigationView = (NavigationView) BerandaFragment.this.getActivity().findViewById(R.id.nav_view);
                        navigationView.setCheckedItem(R.id.donasi);
                        Toolbar toolbar = (Toolbar) BerandaFragment.this.getActivity().findViewById(R.id.toolbar);
                        ImageView cover = (ImageView) toolbar.findViewById(R.id.logobuka);
                        TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
                        cover.setVisibility(GONE);
                        judul.setText("Donasi");
//                tabl.setVisibility(View.VISIBLE);
                    }
                });
                adapter1.SetOnItemClickListener(new BantuanLainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Berita mu = dataUKM.get(position);
                        Identitas ident = dataIdentitasBantuan.get(position);
                        Bundle b = new Bundle();
                        b.putParcelable("ukm", mu);
                        b.putParcelable("identitas",ident);
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
                        cover.setVisibility(GONE);
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
                        cover.setVisibility(GONE);
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
                        cover.setVisibility(GONE);
                        judul.setText("Donasi");
                        tabl.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mDatabase.getReference("admin").child("galang_dana").child("sudah_terverifikasi").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot datai : dataSnapshot.getChildren()) {
//                        for (DataSnapshot dataj : datai.getChildren()) {
//                            if (dataj.exists()) {
//                                try {
//                                    Berita b = dataj.getValue(Berita.class);
//                                    if (b.getKategori().equals("Modal UKM")) {
//                                        dataUKM.add(b);
//                                    } else {
//                                        dataBantuan.add(b);
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    public void requestProfile(final String iduser) {
        //Creating a string request

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.bukalapak.com/v2/users/" + iduser + "/profile.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Beranda", "Message : " + response);
                            JSONObject respon = new JSONObject(response);
                            JSONObject user = respon.getJSONObject("user");
                            JSONObject alamat = user.getJSONObject("address");
                            if (respon.getString("status").equals("OK")) {
//                                profileData = new userProfile(user.getString("name"), user.getString("avatar"), alamat.getString("city") + ", " + alamat.getString("province"), user.getString("email"), user.getString("phone"), "");
                                userProfile profile = new userProfile(user.getString("name"), user.getString("avatar"), alamat.getString("city") + ", " + alamat.getString("province"), "", "", "");
                                profile.setUser_id(user.getString("id"));
                                nama.setText(profile.getNama());
                                Picasso.with(BerandaFragment.this.getContext()).load(profile.getAvatar()).fit().into(avatar);

                            } else {
                                Toast.makeText(BerandaFragment.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("Beranda", "Message : " + e.getMessage());
                            Toast.makeText(BerandaFragment.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(BerandaFragment.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e("Beranda", "Message : " + error.getMessage());
                    }
                });

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

}
