package com.studio.pattimura.bukaamal.Model;

/**
 * Created by wildan on 06/05/17.
 */

public class ModalUKM {
    private String gambar, tanggal, judul, deskripsi;
    private int danaterkumpul;
    private float persen;

    public ModalUKM(String gambar, String tanggal, String judul, String deskripsi, int danaterkumpul, float persen) {
        this.gambar = gambar;
        this.tanggal = tanggal;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.danaterkumpul = danaterkumpul;
        this.persen = persen;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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
}
