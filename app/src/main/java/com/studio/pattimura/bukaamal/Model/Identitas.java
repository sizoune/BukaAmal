package com.studio.pattimura.bukaamal.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by desmoncode on 24/05/17.
 */

public class Identitas implements Parcelable{
    private String alamat,avatar,bank,email,id,ktp,nama,rekening,telepon,tglLahir,foto;

    public Identitas(String alamat, String avatar, String bank, String email, String id, String ktp, String nama, String rekening, String telepon, String tglLahir, String foto) {
        this.alamat = alamat;
        this.avatar = avatar;
        this.bank = bank;
        this.email = email;
        this.id = id;
        this.ktp = ktp;
        this.nama = nama;
        this.rekening = rekening;
        this.telepon = telepon;
        this.tglLahir = tglLahir;
        this.foto = foto;
    }

    public Identitas() {
    }

    protected Identitas(Parcel in) {
        alamat = in.readString();
        avatar = in.readString();
        bank = in.readString();
        email = in.readString();
        id = in.readString();
        ktp = in.readString();
        nama = in.readString();
        rekening = in.readString();
        telepon = in.readString();
        tglLahir = in.readString();
        foto = in.readString();
    }

    public static final Creator<Identitas> CREATOR = new Creator<Identitas>() {
        @Override
        public Identitas createFromParcel(Parcel in) {
            return new Identitas(in);
        }

        @Override
        public Identitas[] newArray(int size) {
            return new Identitas[size];
        }
    };

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(alamat);
        parcel.writeString(avatar);
        parcel.writeString(bank);
        parcel.writeString(email);
        parcel.writeString(id);
        parcel.writeString(ktp);
        parcel.writeString(nama);
        parcel.writeString(rekening);
        parcel.writeString(telepon);
        parcel.writeString(tglLahir);
        parcel.writeString(foto);
    }
}
