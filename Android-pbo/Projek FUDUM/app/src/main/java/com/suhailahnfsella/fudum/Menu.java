package com.suhailahnfsella.fudum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("idmenu")
    @Expose
    private Integer idmenu;
    @SerializedName("idkategori")
    @Expose
    private Integer idkategori;
    @SerializedName("idtoko")
    @Expose
    private Integer idtoko;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("produk")
    @Expose
    private String produk;
    @SerializedName("kodeproduk")
    @Expose
    private String kodeproduk;
    @SerializedName("stok")
    @Expose
    private Integer stok;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("namatoko")
    @Expose
    private String namatoko;
    @SerializedName("fototoko")
    @Expose
    private String fototoko;
    @SerializedName("tahunusaha")
    @Expose
    private Integer tahunusaha;
    @SerializedName("alamattoko")
    @Expose
    private String alamattoko;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kabupaten")
    @Expose
    private String kabupaten;
    @SerializedName("sosmed")
    @Expose
    private String sosmed;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public Integer getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(Integer idkategori) {
        this.idkategori = idkategori;
    }

    public Integer getIdtoko() {
        return idtoko;
    }

    public void setIdtoko(Integer idtoko) {
        this.idtoko = idtoko;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getKodeproduk() {
        return kodeproduk;
    }

    public void setKodeproduk(String kodeproduk) {
        this.kodeproduk = kodeproduk;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
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

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getNamatoko() {
        return namatoko;
    }

    public void setNamatoko(String namatoko) {
        this.namatoko = namatoko;
    }

    public String getFototoko() {
        return fototoko;
    }

    public void setFototoko(String fototoko) {
        this.fototoko = fototoko;
    }

    public Integer getTahunusaha() {
        return tahunusaha;
    }

    public void setTahunusaha(Integer tahunusaha) {
        this.tahunusaha = tahunusaha;
    }

    public String getAlamattoko() {
        return alamattoko;
    }

    public void setAlamattoko(String alamattoko) {
        this.alamattoko = alamattoko;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getSosmed() {
        return sosmed;
    }

    public void setSosmed(String sosmed) {
        this.sosmed = sosmed;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
