package com.studio.pattimura.bukaamal.Model;

/**
 * Created by desmoncode on 23/05/17.
 */

public class Donation {
    private long id;
    private String id_berita_galang, jumlah, id_user;
    private Boolean status;

    public Donation(long id, String id_berita_galang, String jumlah, String id_user, Boolean status) {
        this.id = id;
        this.id_berita_galang = id_berita_galang;
        this.jumlah = jumlah;
        this.id_user = id_user;
        this.status = status;
    }

    public Donation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getId_berita_galang() {
        return id_berita_galang;
    }

    public void setId_berita_galang(String id_berita_galang) {
        this.id_berita_galang = id_berita_galang;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
