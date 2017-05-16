package com.studio.pattimura.bukaamal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Galeri;
import com.studio.pattimura.bukaamal.R;

import java.util.ArrayList;

/**
 * Created by wildan on 13/05/17.
 */

public class AdapterGaleri extends RecyclerView.Adapter<AdapterGaleri.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Context context;
    private ArrayList<Galeri> daftargambar;

    public AdapterGaleri(Context context, ArrayList<Galeri> daftargambar) {
        this.context = context;
        this.daftargambar = daftargambar;
    }

    @Override
    public AdapterGaleri.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.galeri_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterGaleri.ViewHolder holder, int position) {
        Picasso.with(context).load(daftargambar.get(position).getGambar()).fit().into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return daftargambar.size();
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
        public ImageView gambar;

        public ViewHolder(View view) {
            super(view);

            gambar = (ImageView) view.findViewById(R.id.galeri);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
