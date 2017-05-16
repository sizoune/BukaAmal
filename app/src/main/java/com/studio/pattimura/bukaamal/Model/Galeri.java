package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wildan on 13/05/17.
 */

public class Galeri implements Parcelable {
    public static final Creator<Galeri> CREATOR = new Creator<Galeri>() {
        @Override
        public Galeri createFromParcel(Parcel in) {
            return new Galeri(in);
        }

        @Override
        public Galeri[] newArray(int size) {
            return new Galeri[size];
        }
    };
    private String nama, gambar;

    public Galeri(String nama, String gambar) {
        this.nama = nama;
        this.gambar = gambar;
    }

    protected Galeri(Parcel in) {
        nama = in.readString();
        gambar = in.readString();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(gambar);
    }
}
