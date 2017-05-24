package com.studio.pattimura.bukaamal.Model;

import java.util.ArrayList;

/**
 * Created by mwi on 5/24/17.
 */

public class DonasiSaya {
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
}
