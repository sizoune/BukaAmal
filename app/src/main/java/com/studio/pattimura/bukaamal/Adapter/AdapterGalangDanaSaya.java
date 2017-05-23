package com.studio.pattimura.bukaamal.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * Created by mwi on 5/23/17.
 */

public class AdapterGalangDanaSaya extends RecyclerView.Adapter<AdapterGalangDanaSaya.ViewHolder> {
    final Context context;
    OnItemClickListener mItemClickListener;
    private ArrayList<Berita> dataBerita;
    StorageReference mStorageRef;

    public AdapterGalangDanaSaya(Context context, ArrayList<Berita> dataBerita) {
        this.context = context;
        this.dataBerita = dataBerita;
    }

    @Override
    public AdapterGalangDanaSaya.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_galangdanasaya, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.kategori.setText(dataBerita.get(position).getKategori());
        holder.desc.setText(dataBerita.get(position).getDeskripsi());
        mStorageRef = FirebaseStorage.getInstance().getReference(dataBerita.get(position).getFoto());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).fit().into(holder.gambar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBerita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView kategori, desc;
        public ImageView gambar;
        public CardView cv;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cardView1);
            gambar = (ImageView) view.findViewById(R.id.imageGalangDanasaya);
            kategori = (TextView) view.findViewById(R.id.txtKategoriGalangdanasaya);
            desc = (TextView) view.findViewById(R.id.txtDescgalangdanasaya);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}