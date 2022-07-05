package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JasaModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("NamaJasa")
    @Expose
    private String namaJasa;
    @SerializedName("Kategori")
    @Expose
    private String kategori;
    @SerializedName("NamaPembuatJasa")
    @Expose
    private String namaPembuatJasa;
    @SerializedName("Kelas")
    @Expose
    private String kelas;
    @SerializedName("Deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("Pengalaman")
    @Expose
    private String pengalaman;
    @SerializedName("GambarJasa")
    @Expose
    private String gambarJasa;
    @SerializedName("NoHp")
    @Expose
    private String noHp;
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

    public String getNamaJasa() {
        return namaJasa;
    }

    public void setNamaJasa(String namaJasa) {
        this.namaJasa = namaJasa;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNamaPembuatJasa() {
        return namaPembuatJasa;
    }

    public void setNamaPembuatJasa(String namaPembuatJasa) {
        this.namaPembuatJasa = namaPembuatJasa;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
    }

    public String getGambarJasa() {
        return gambarJasa;
    }

    public void setGambarJasa(String gambarJasa) {
        this.gambarJasa = gambarJasa;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
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
