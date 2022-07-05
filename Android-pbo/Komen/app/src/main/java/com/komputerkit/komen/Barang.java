package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Barang")
    @Expose
    private String barang;
    @SerializedName("Harga")
    @Expose
    private Integer harga;
    @SerializedName("KodeBRG")
    @Expose
    private String kodeBRG;
    @SerializedName("Deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("FotoBrg")
    @Expose
    private String fotoBrg;
    @SerializedName("Kategori")
    @Expose
    private String kategori;
    @SerializedName("Stok")
    @Expose
    private Integer stok;
    @SerializedName("NoHp")
    @Expose
    private String noHp;
    @SerializedName("JumlahHarga")
    @Expose
    private Integer jumlahHarga;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getKodeBRG() {
        return kodeBRG;
    }

    public void setKodeBRG(String kodeBRG) {
        this.kodeBRG = kodeBRG;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFotoBrg() {
        return fotoBrg;
    }

    public void setFotoBrg(String fotoBrg) {
        this.fotoBrg = fotoBrg;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Integer getJumlahHarga() {
        return jumlahHarga;
    }

    public void setJumlahHarga(Integer jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
