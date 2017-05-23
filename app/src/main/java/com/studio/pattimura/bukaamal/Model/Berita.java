package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by desmoncode on 22/05/17.
 */

public class Berita implements Parcelable {
    private String judul, deadline, kategori, lokasi, deskripsi, foto;
    private Boolean status, status_pencairan;
    private long dana, dana_terkumpul, id;

    public Berita() {
    }

    public Berita(long id, String judul, String deadline, String kategori, String lokasi, String deskripsi, String foto, Boolean status, Boolean status_pencarian, long dana, long dana_terkumpul) {
        this.id = id;
        this.judul = judul;
        this.deadline = deadline;
        this.kategori = kategori;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.status = status;
        this.status_pencairan = status_pencarian;
        this.dana = dana;
        this.dana_terkumpul = dana_terkumpul;
    }

    protected Berita(Parcel in) {
        judul = in.readString();
        deadline = in.readString();
        kategori = in.readString();
        lokasi = in.readString();
        deskripsi = in.readString();
        foto = in.readString();
        dana = in.readLong();
        dana_terkumpul = in.readLong();
        id = in.readLong();
    }

    public static final Creator<Berita> CREATOR = new Creator<Berita>() {
        @Override
        public Berita createFromParcel(Parcel in) {
            return new Berita(in);
        }

        @Override
        public Berita[] newArray(int size) {
            return new Berita[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus_pencarian() {
        return status_pencairan;
    }

    public void setStatus_pencarian(Boolean status_pencarian) {
        this.status_pencairan = status_pencarian;
    }

    public long getDana() {
        return dana;
    }

    public void setDana(long dana) {
        this.dana = dana;
    }

    public long getDana_terkumpul() {
        return dana_terkumpul;
    }

    public void setDana_terkumpul(long dana_terkumpul) {
        this.dana_terkumpul = dana_terkumpul;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(deadline);
        dest.writeString(kategori);
        dest.writeString(lokasi);
        dest.writeString(deskripsi);
        dest.writeString(foto);
        dest.writeLong(dana);
        dest.writeLong(dana_terkumpul);
        dest.writeLong(id);
    }
}
