package com.studio.pattimura.bukaamal.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wildan on 07/05/17.
 */

public class BantuanLainAdapter extends RecyclerView.Adapter<BantuanLainAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private ArrayList<Berita> listUKM;
    private Context context;
    StorageReference mStorageRef;

    public BantuanLainAdapter(ArrayList<Berita> listUKM, Context context) {
        this.listUKM = listUKM;
        this.context = context;
    }

    @Override
    public BantuanLainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_ukm_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.judul.setText(listUKM.get(position).getJudul());
        holder.desc.setText(listUKM.get(position).getDeskripsi());
        double d = (double) listUKM.get(position).getDana_terkumpul() / listUKM.get(position).getDana();
        if (d * 100 < 100)
            holder.persen.setText(String.format("%.2f", ((d) * 100)) + "%");
        else
            holder.persen.setText("100%");
        holder.danaterkumpul.setText("Rp. " + Long.toString(listUKM.get(position).getDana_terkumpul()));

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            Date deadline = myFormat.parse(listUKM.get(position).getDeadline());
            long selisih = deadline.getTime() - date.getTime();
            holder.tenggatwaktu.setText(Long.toString(TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS)) + " Hari Lagi");
        } catch (ParseException e) {
            Log.e("parse error", e.getMessage());
        }
        mStorageRef = FirebaseStorage.getInstance().getReference(listUKM.get(position).getFoto());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).fit().into(holder.gambar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUKM.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView judul, desc, tenggatwaktu, persen, danaterkumpul;
        public ImageView gambar;
        public CardView cv;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cardView);
            gambar = (ImageView) view.findViewById(R.id.covermodalUkmrow);
            judul = (TextView) view.findViewById(R.id.txtJudulUKM);
            desc = (TextView) view.findViewById(R.id.txtSebagianDescUKM);
            tenggatwaktu = (TextView) view.findViewById(R.id.txtTenggatWaktuUKM);
            persen = (TextView) view.findViewById(R.id.txtPersenTerkumpul);
            danaterkumpul = (TextView) view.findViewById(R.id.txtDanaTerkumpulUKM);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
