package com.studio.pattimura.bukaamal.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Adapter.AdapterDonasiSaya;
import com.studio.pattimura.bukaamal.Donasi;
import com.studio.pattimura.bukaamal.Model.*;
import com.studio.pattimura.bukaamal.R;

import org.json.JSONObject;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonasiAdmin extends Fragment {
    private SectionedRecyclerViewAdapter sectionAdapter;
    private ArrayList<com.studio.pattimura.bukaamal.Model.DonasiSaya> dataDonasiBelum = new ArrayList<>();
    private ArrayList<com.studio.pattimura.bukaamal.Model.DonasiSaya> dataDonasiSudah = new ArrayList<>();
    private ArrayList<String> header = new ArrayList<>();
    private ArrayList<Berita> dataBerita = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitas = new ArrayList<>();
    //    private ArrayList<userAuth> dataUser = new ArrayList<>();
    private FirebaseDatabase database;
    private RecyclerView list;
    private GridLayoutManager gridLayoutManager;
    private int ix1, iy1, ix2, iy2;
    private ArrayList<userProfile> dataProfile = new ArrayList<>();
    //    private userProfile profileData;
    private ArrayList<String> idUser = new ArrayList<>();


    public DonasiAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donasi_admin, container, false);
        header.add("Belum di Verifikasi");
        header.add("Sudah di Verifikasi");
        database = FirebaseDatabase.getInstance();
        sectionAdapter = new SectionedRecyclerViewAdapter();
        list = (RecyclerView) view.findViewById(R.id.recyclerViewadmin1);
        gridLayoutManager = new GridLayoutManager(DonasiAdmin.this.getContext(), 1, GridLayoutManager.VERTICAL, false);
        ix1 = 0;
        iy1 = 0;
        ix2 = 0;
        iy2 = 0;
//        getUser();
        getAlldata();
//        for (String i : idUser) {
//            Toast.makeText(this.getContext(), i, Toast.LENGTH_SHORT).show();
//            requestProfile(i);
//        }
        return view;
    }

    private void getAlldata() {
        Query query = database.getReference("admin").child("donasi").child("belum_terverifikasi");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    com.studio.pattimura.bukaamal.Model.DonasiSaya donasi = data.getValue(com.studio.pattimura.bukaamal.Model.DonasiSaya.class);
                    dataDonasiBelum.add(donasi);
                    ix1++;
                }
                getDataBeritaBelum();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Error", databaseError.getMessage());
            }
        });

        Query query1 = database.getReference("admin").child("donasi").child("sudah_terverifikasi");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    com.studio.pattimura.bukaamal.Model.DonasiSaya donasi = data.getValue(com.studio.pattimura.bukaamal.Model.DonasiSaya.class);
                    dataDonasiSudah.add(donasi);
                    ix2++;
                }
                getDataBeritaSudah();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database Error", databaseError.getMessage());
            }
        });

        postDataBerita();

    }

//    public void getUser() {
//        Query query = database.getReference("userAuth");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    userAuth user = data.getValue(userAuth.class);
//                    dataUser.add(user);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("Database Error", databaseError.getMessage());
//            }
//        });
//    }

    private void getDataBeritaBelum() {
        for (final com.studio.pattimura.bukaamal.Model.DonasiSaya datadonasi : dataDonasiBelum) {
            idUser.add(datadonasi.getId_user());
            requestProfile(datadonasi.getId_user());
            Query query = database.getReference("admin").child("galang_dana").child("sudah_terverifikasi").child(datadonasi.getId_berita_galang());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Berita berita = dataSnapshot.child("berita").getValue(Berita.class);
                        Identitas identitas = dataSnapshot.child("identitas").getValue(Identitas.class);
                        datadonasi.addBerita(berita);
                        dataBerita.add(berita);
                        dataIdentitas.add(identitas);

                        iy1++;
                        //Toast.makeText(DonasiSaya.this.getContext(), datadonasi.getDataBerita().getJudul(), Toast.LENGTH_SHORT).show();
                    }
                    if (iy1 == ix1) {
                        if (!dataDonasiBelum.isEmpty())
                            sectionAdapter.addSection(new DonasiAdmin.ContactsSection(header.get(0), dataBerita, DonasiAdmin.this.getContext(), dataIdentitas, dataDonasiBelum, dataProfile, idUser));
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Database Error1", databaseError.getMessage());
                }
            });
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (iy1 == ix1) {
//                    if (!dataDonasiBelum.isEmpty()) {
//                        sectionAdapter.addSection(new DonasiAdmin.ContactsSection(header.get(0), dataBerita, DonasiAdmin.this.getContext(),dataIdentitas,dataDonasiBelum));
//                    }
//                    list.setAdapter(sectionAdapter);
//                    list.setLayoutManager(gridLayoutManager);
//                }
//            }
//        }, 10000);

    }

    private void getDataBeritaSudah() {
        dataBerita = new ArrayList<Berita>();
        dataIdentitas = new ArrayList<Identitas>();
        for (final com.studio.pattimura.bukaamal.Model.DonasiSaya datadonasi : dataDonasiSudah) {
            idUser.add(datadonasi.getId_user());
            requestProfile(datadonasi.getId_user());
            Query query = database.getReference("admin").child("galang_dana").child("sudah_terverifikasi").child(datadonasi.getId_berita_galang());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Berita berita = dataSnapshot.child("berita").getValue(Berita.class);
                        Identitas identitas = dataSnapshot.child("identitas").getValue(Identitas.class);
                        datadonasi.addBerita(berita);
                        dataBerita.add(berita);
                        dataIdentitas.add(identitas);

                        iy2++;
                        //Toast.makeText(DonasiSaya.this.getContext(), datadonasi.getDataBerita().getJudul(), Toast.LENGTH_SHORT).show();
                    }
                    if (iy2 == ix2) {
                        if (!dataDonasiSudah.isEmpty())
                            sectionAdapter.addSection(new DonasiAdmin.ContactsSection(header.get(1), dataBerita, DonasiAdmin.this.getContext(), dataIdentitas, dataDonasiSudah, dataProfile, idUser));
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Database Error1", databaseError.getMessage());
                }
            });
        }


    }

    public void postDataBerita() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(sectionAdapter);
                list.setLayoutManager(gridLayoutManager);
            }
        }, 10000);
    }

    class ContactsSection extends StatelessSection {

        String title;
        ArrayList<Berita> list;
        ArrayList<Identitas> listIdentitas;
        ArrayList<com.studio.pattimura.bukaamal.Model.DonasiSaya> listDonasi;
        ArrayList<userProfile> listUser;
        ArrayList<String> listid;
        Context context;

        public ContactsSection(String title, ArrayList<Berita> list, Context context, ArrayList<Identitas> listIdentitas, ArrayList<com.studio.pattimura.bukaamal.Model.DonasiSaya> listDonasi, ArrayList<userProfile> listUser, ArrayList<String> listid) {
            super(R.layout.header_item, R.layout.list_donasi_admin);
            this.listIdentitas = listIdentitas;
            this.listDonasi = listDonasi;
            this.title = title;
            this.list = list;
            this.listUser = listUser;
            this.listid = listid;
            this.context = context;
        }

        @Override
        public int getContentItemsTotal() {
            return listDonasi.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new DonasiAdmin.ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final DonasiAdmin.ItemViewHolder itemHolder = (DonasiAdmin.ItemViewHolder) holder;

            final Berita item = list.get(position);
            final Identitas itemIdentitas = listIdentitas.get(position);
            final com.studio.pattimura.bukaamal.Model.DonasiSaya itemDonasi = listDonasi.get(position);
            userProfile prof = new userProfile();
            for (userProfile profile : dataProfile) {
                if (itemDonasi.getId_user().equals(profile.getUser_id())) {
                    prof = profile;
                    break;
                }
            }
            final userProfile profileData = prof;

            itemHolder.nama.setText(profileData.getNama());
            itemHolder.judul.setText(item.getJudul());
            itemHolder.jumlah.setText("Rp " + itemDonasi.getJumlah());

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DonasiAdmin.this.getActivity(), DetailGalangdanDonasiAdmin.class)
                            .putExtra("berita", item);
                    intent.putExtra("identitas", itemIdentitas);
                    intent.putExtra("donasi", itemDonasi);
                    intent.putExtra("user", (Parcelable) profileData);
                    startActivity(intent);
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new DonasiAdmin.HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            DonasiAdmin.HeaderViewHolder headerHolder = (DonasiAdmin.HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.txtHeader);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        public TextView nama, judul, jumlah;


        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            nama = (TextView) view.findViewById(R.id.txtNamaDonatur);
            judul = (TextView) view.findViewById(R.id.txtJudulDonasi);
            jumlah = (TextView) view.findViewById(R.id.txtJumlahDonasi);
        }
    }

    public void requestProfile(final String iduser) {
        //Creating a string request

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.bukalapak.com/v2/users/" + iduser + "/profile.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("DonasiAdmin", "Message : " + response);
                            JSONObject respon = new JSONObject(response);
                            JSONObject user = respon.getJSONObject("user");
                            JSONObject alamat = user.getJSONObject("address");
                            if (respon.getString("status").equals("OK")) {
//                                profileData = new userProfile(user.getString("name"), user.getString("avatar"), alamat.getString("city") + ", " + alamat.getString("province"), user.getString("email"), user.getString("phone"), "");
                                userProfile profile = new userProfile(user.getString("name"), user.getString("avatar"), alamat.getString("city") + ", " + alamat.getString("province"), "", "", "");
                                profile.setUser_id(user.getString("id"));
                                dataProfile.add(profile);

                            } else {
//                                Toast.makeText(DonasiAdmin.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("DonasiAdmin", "Message : " + e.getMessage());
//                            Toast.makeText(DonasiAdmin.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
//                        Toast.makeText(DonasiAdmin.this.getContext(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e("DonasiAdmin", "Message : " + error.getMessage());
                    }
                });

        //Adding the string request to the queue
        try{
            RequestQueue requestQueue = Volley.newRequestQueue(this.getContext().getApplicationContext());
            requestQueue.add(stringRequest);
        }catch (Exception e){
            Log.d("donasiAdmin", "requestProfile: "+e);
        }

    }

}
