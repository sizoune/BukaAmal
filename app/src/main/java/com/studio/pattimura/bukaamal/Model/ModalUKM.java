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
    private long dana_terkumpul,dana;
    private float persen;
    private ArrayList<Galeri> gambar;


//    public ModalUKM(String tanggal, String judul, String deskripsi, long dana_terkumpul, float persen) {
//        gambar = new ArrayList<>();
//        this.tanggal = tanggal;
//        this.judul = judul;
//        this.deskripsi = deskripsi;
//        this.dana_terkumpul = dana_terkumpul;
//        this.persen = persen;
//    }

    public ModalUKM(String tanggal, String judul, String deskripsi, long dana_terkumpul,long dana) {
        gambar = new ArrayList<>();
        this.tanggal = tanggal;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.dana_terkumpul = dana_terkumpul;
        this.dana = dana;
    }

    protected ModalUKM(Parcel in) {
        tanggal = in.readString();
        judul = in.readString();
        deskripsi = in.readString();
        dana_terkumpul = in.readLong();
        dana = in.readLong();
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

    public long getDana_terkumpul() {
        return dana_terkumpul;
    }

    public void setDana_terkumpul(long dana_terkumpul) {
        this.dana_terkumpul = dana_terkumpul;
    }

    public long getDana() {
        return dana;
    }

    public void setDana(long dana) {
        this.dana = dana;
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
        dest.writeLong(dana_terkumpul);
        dest.writeLong(dana);
        dest.writeFloat(persen);
        dest.writeTypedList(gambar);
    }


}
