package com.studio.pattimura.bukaamal.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalangDanaAdmin extends Fragment {
    private SectionedRecyclerViewAdapter sectionAdapter;
    private ArrayList<String> header = new ArrayList<>();
    private ArrayList<Berita> dataBerita = new ArrayList<>();
    private FirebaseDatabase database;
    private RecyclerView list;
    private GridLayoutManager gridLayoutManager;

    public GalangDanaAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_galang_dana_admin, container, false);
        header.add("Belum di Verifikasi");
        header.add("Sudah di Verifikasi");
        database = FirebaseDatabase.getInstance();
        sectionAdapter = new SectionedRecyclerViewAdapter();
        list = (RecyclerView) v.findViewById(R.id.recyclerViewadmin1);
        gridLayoutManager = new GridLayoutManager(GalangDanaAdmin.this.getContext(), 1, GridLayoutManager.VERTICAL, false);
        getAlldata();
        return v;
    }

    private void getAlldata() {
        database.getReference("admin").child("galang_dana").child("belum_terverifikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    dataBerita.add(berita);
                }
                if (!dataBerita.isEmpty()) {
                    sectionAdapter.addSection(new ContactsSection(header.get(0), dataBerita, GalangDanaAdmin.this.getContext()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("admin").child("galang_dana").child("sudah_terverifikasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataBerita = new ArrayList<Berita>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    dataBerita.add(berita);
                }
                if (!dataBerita.isEmpty()) {
                    sectionAdapter.addSection(new ContactsSection(header.get(1), dataBerita, GalangDanaAdmin.this.getContext()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        Context context;
        StorageReference mStorageRef;

        public ContactsSection(String title, ArrayList<Berita> list, Context context) {
            super(R.layout.header_item, R.layout.list_row_galangdanasaya);

            this.title = title;
            this.list = list;
            this.context = context;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            final Berita item = list.get(position);

            itemHolder.kategori.setText(item.getJudul());
            itemHolder.desc.setText(item.getDeskripsi());
            mStorageRef = FirebaseStorage.getInstance().getReference(item.getFoto());
            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(context).load(uri).fit().into(itemHolder.gambar);
                }
            });

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GalangDanaAdmin.this.getActivity(), DetailGalangdanDonasiAdmin.class)
                            .putExtra("berita", item);
                    startActivity(intent);
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

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
        public TextView kategori, desc;
        public ImageView gambar;
        public CardView cv;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            cv = (CardView) view.findViewById(R.id.cardView1);
            gambar = (ImageView) view.findViewById(R.id.imageGalangDanasaya);
            kategori = (TextView) view.findViewById(R.id.txtKategoriGalangdanasaya);
            desc = (TextView) view.findViewById(R.id.txtDescgalangdanasaya);
        }
    }
}
