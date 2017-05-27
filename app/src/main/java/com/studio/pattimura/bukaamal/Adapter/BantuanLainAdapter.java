package com.studio.pattimura.bukaamal.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.BantuanLain;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * Created by wildan on 07/05/17.
 */

public class BantuanLainAdapter extends RecyclerView.Adapter<BantuanLainAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private ArrayList<BantuanLain> listUKM;
    private Context context;

    public BantuanLainAdapter(ArrayList<BantuanLain> listUKM, Context context) {
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
    public void onBindViewHolder(BantuanLainAdapter.ViewHolder holder, int position) {
        holder.judul.setText(listUKM.get(position).getJudul());
        holder.danaterkumpul.setText("Rp. " + Long.toString(listUKM.get(position).getDana_terkumpul()));
        holder.persen.setText(String.format("%.2f",((listUKM.get(position).getDana_terkumpul()/listUKM.get(position).getDana())*100)) + " %");
        holder.tenggatwaktu.setText(listUKM.get(position).getTanggal());
        holder.desc.setText(listUKM.get(position).getDeskripsi());
        Picasso.with(context).load(listUKM.get(position).getGambar().get(0).getGambar()).fit().into(holder.gambar);
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
