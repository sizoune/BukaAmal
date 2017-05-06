package com.studio.pattimura.bukaamal.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.ModalUKM;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * Created by wildan on 06/05/17.
 */

public class ModalUKMAdapter extends RecyclerView.Adapter<ModalUKMAdapter.ViewHolder> {
    final Context context;
    OnItemClickListener mItemClickListener;
    private ArrayList<ModalUKM> listUKM;

    public ModalUKMAdapter(ArrayList<ModalUKM> listUKM, Context context) {
        this.listUKM = listUKM;
        this.context = context;
    }

    @Override
    public ModalUKMAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_ukm_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ModalUKMAdapter.ViewHolder holder, int position) {
        holder.judul.setText(listUKM.get(position).getJudul());
        holder.danaterkumpul.setText("Rp. " + Integer.toString(listUKM.get(position).getDanaterkumpul()));
        holder.persen.setText(Float.toString(listUKM.get(position).getPersen()) + " %");
        holder.tenggatwaktu.setText(listUKM.get(position).getTanggal());
        holder.desc.setText(listUKM.get(position).getDeskripsi());
        Picasso.with(context).load(listUKM.get(position).getGambar()).fit().into(holder.gambar);
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