package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by wildan on 06/05/17.
 */

public class ModalUKM implements Parcelable {

    public static final Creator<ModalUKM> CREATOR = new Creator<ModalUKM>() {
        @Override
        public ModalUKM createFromParcel(Parcel in) {
            return new ModalUKM(in);
        }

        @Override
        public ModalUKM[] newArray(int size) {
            return new ModalUKM[size];
        }
    };
    private String tanggal, judul, deskripsi;
    private int danaterkumpul;
    private float persen;
    private ArrayList<Galeri> gambar;


    public ModalUKM(String tanggal, String judul, String deskripsi, int danaterkumpul, float persen) {
        gambar = new ArrayList<>();
        this.tanggal = tanggal;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.danaterkumpul = danaterkumpul;
        this.persen = persen;
    }

    protected ModalUKM(Parcel in) {
        tanggal = in.readString();
        judul = in.readString();
        deskripsi = in.readString();
        danaterkumpul = in.readInt();
        persen = in.readFloat();
        gambar = in.createTypedArrayList(Galeri.CREATOR);
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getDanaterkumpul() {
        return danaterkumpul;
    }

    public void setDanaterkumpul(int danaterkumpul) {
        this.danaterkumpul = danaterkumpul;
    }

    public float getPersen() {
        return persen;
    }

    public void setPersen(float persen) {
        this.persen = persen;
    }

    public ArrayList<Galeri> getGambar() {
        return gambar;
    }

    public void setGambar(ArrayList<Galeri> gambar) {
        this.gambar = gambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(tanggal);
        dest.writeString(judul);
        dest.writeString(deskripsi);
        dest.writeInt(danaterkumpul);
        dest.writeFloat(persen);
        dest.writeTypedList(gambar);
    }


}
