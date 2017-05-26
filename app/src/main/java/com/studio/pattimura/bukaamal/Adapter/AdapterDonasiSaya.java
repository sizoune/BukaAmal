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
import com.studio.pattimura.bukaamal.Model.DonasiSaya;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * Created by mwi on 5/24/17.
 */

public class AdapterDonasiSaya extends RecyclerView.Adapter<AdapterDonasiSaya.ViewHolder> {
    final Context context;
    OnItemClickListener mItemClickListener;
    private ArrayList<DonasiSaya> dataDonasisaya;
    StorageReference mStorageRef;

    public AdapterDonasiSaya(Context context, ArrayList<DonasiSaya> dataDonasisaya) {
        this.context = context;
        this.dataDonasisaya = dataDonasisaya;
    }

    @Override
    public AdapterDonasiSaya.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_galangdanasaya, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.kategori.setText(dataDonasisaya.get(position).getDataBerita().getJudul());
        holder.desc.setText("Anda mendonasikan sebesar Rp. " + dataDonasisaya.get(position).getJumlah());
        mStorageRef = FirebaseStorage.getInstance().getReference(dataDonasisaya.get(position).getDataBerita().getFoto());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).fit().into(holder.gambar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataDonasisaya.size();
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
            desc.setTextSize(12);
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
