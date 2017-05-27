package com.studio.pattimura.bukaamal.Model;

/**
 * Created by desmoncode on 27/05/17.
 */

public class topUser {
    private String id_user;
    private long jumlah_donasi;

    public topUser(String id_user, long jumlah_donasi) {
        this.id_user = id_user;
        this.jumlah_donasi = jumlah_donasi;
    }

    public topUser(){

    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public long getJumlah_donasi() {
        return jumlah_donasi;
    }

    public void setJumlah_donasi(long jumlah_donasi) {
        this.jumlah_donasi = jumlah_donasi;
    }
}
