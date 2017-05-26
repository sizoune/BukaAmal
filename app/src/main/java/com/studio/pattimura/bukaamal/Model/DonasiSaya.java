package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by mwi on 5/24/17.
 */

public class DonasiSaya implements Parcelable{
    private long id;
    private String id_berita_galang, id_user,jumlah, metode_bayar;
    private boolean status;
    private Berita dataBerita;

    public DonasiSaya() {
    }

    public DonasiSaya(long id, String id_berita_galang, String id_user, String jumlah, String metode_bayar, boolean status) {
        this.id = id;
        this.id_berita_galang = id_berita_galang;
        this.id_user = id_user;
        this.jumlah = jumlah;
        this.metode_bayar = metode_bayar;
        this.status = status;
    }

    protected DonasiSaya(Parcel in) {
        id = in.readLong();
        id_berita_galang = in.readString();
        id_user = in.readString();
        jumlah = in.readString();
        metode_bayar = in.readString();
        status = in.readByte() != 0;
        dataBerita = in.readParcelable(Berita.class.getClassLoader());
    }

    public static final Creator<DonasiSaya> CREATOR = new Creator<DonasiSaya>() {
        @Override
        public DonasiSaya createFromParcel(Parcel in) {
            return new DonasiSaya(in);
        }

        @Override
        public DonasiSaya[] newArray(int size) {
            return new DonasiSaya[size];
        }
    };

    public long getId(){
        return this.id;
    }

    public long getIdDonasi() {
        return id;
    }

    public void setIdDonasi(long id) {
        this.id = id;
    }

    public String getId_berita_galang() {
        return id_berita_galang;
    }

    public void setId_berita_galang(String id_berita_galang) {
        this.id_berita_galang = id_berita_galang;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getMetode_bayar() {
        return metode_bayar;
    }

    public void setMetode_bayar(String metode_bayar) {
        this.metode_bayar = metode_bayar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addBerita(Berita berita) {
        dataBerita = berita;
    }

    public Berita getDataBerita() {
        return dataBerita;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(id_berita_galang);
        parcel.writeString(id_user);
        parcel.writeString(jumlah);
        parcel.writeString(metode_bayar);
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeParcelable(dataBerita, i);
    }
}
