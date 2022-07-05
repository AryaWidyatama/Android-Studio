package com.komputerkit.ukomde_afa;

import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dataMenu  {
    @SerializedName("idmenu")
    @Expose
    private Integer idmenu;
    @SerializedName("idkategori")
    @Expose
    private String idkategori;
    @SerializedName("menu")
    @Expose
    private String menu;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("nama_toko")
    @Expose
    private String namaToko;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("kategori")
    @Expose
    private String kategori;

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

//    @SerializedName("idpelanggan")
//    @Expose
//    private Integer idpelanggan;
//    @SerializedName("pelanggan")
//    @Expose
//    private String pelanggan;
//    @SerializedName("alamat")
//    @Expose
//    private String alamat;
//    @SerializedName("telp")
//    @Expose
//    private String telp;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//
//    public Integer getIdpelanggan() {
//        return idpelanggan;
//    }
//
//    public void setIdpelanggan(Integer idpelanggan) {
//        this.idpelanggan = idpelanggan;
//    }
//
//    public String getPelanggan() {
//        return pelanggan;
//    }
//
//    public void setPelanggan(String pelanggan) {
//        this.pelanggan = pelanggan;
//    }
//
//    public String getAlamat() {
//        return alamat;
//    }
//
//    public void setAlamat(String alamat) {
//        this.alamat = alamat;
//    }
//
//    public String getTelp() {
//        return telp;
//    }
//
//    public void setTelp(String telp) {
//        this.telp = telp;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//




}
