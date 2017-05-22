package com.studio.pattimura.bukaamal.Model;

import java.io.Serializable;

/**
 * Created by desmoncode on 19/05/17.
 */

public class userProfile implements Serializable{
    private String nama,avatar,badge,alamat,email,telepon,tglLahir,rekening,ktp;

    public userProfile(String nama, String avatar, String badge, String alamat, String email, String telepon, String tglLahir) {
        this.nama = nama;
        this.avatar = avatar;
        this.badge = badge;
        this.alamat = alamat;
        this.email = email;
        this.telepon = telepon;
        this.tglLahir = tglLahir;
    }

    public userProfile(String nama, String alamat, String telepon, String rekening, String ktp) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.rekening = rekening;
        this.ktp = ktp;
    }

    public userProfile(String nama, String avatar, String alamat, String email, String telepon, String tglLahir) {
        this.nama = nama;
        this.avatar = avatar;
        this.alamat = alamat;
        this.email = email;
        this.telepon = telepon;
        this.tglLahir = tglLahir;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public userProfile() {
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }
}
