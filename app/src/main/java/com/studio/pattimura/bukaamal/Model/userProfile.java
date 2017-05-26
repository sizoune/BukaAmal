package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by desmoncode on 19/05/17.
 */

public class userProfile implements Serializable,Parcelable{
    private String nama,avatar,badge,alamat,email,telepon,tglLahir,rekening,ktp,user_id;

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

    protected userProfile(Parcel in) {
        nama = in.readString();
        avatar = in.readString();
        badge = in.readString();
        alamat = in.readString();
        email = in.readString();
        telepon = in.readString();
        tglLahir = in.readString();
        rekening = in.readString();
        ktp = in.readString();
    }

    public static final Creator<userProfile> CREATOR = new Creator<userProfile>() {
        @Override
        public userProfile createFromParcel(Parcel in) {
            return new userProfile(in);
        }

        @Override
        public userProfile[] newArray(int size) {
            return new userProfile[size];
        }
    };

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public userProfile() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeString(avatar);
        parcel.writeString(badge);
        parcel.writeString(alamat);
        parcel.writeString(email);
        parcel.writeString(telepon);
        parcel.writeString(tglLahir);
        parcel.writeString(rekening);
        parcel.writeString(ktp);
    }
}
