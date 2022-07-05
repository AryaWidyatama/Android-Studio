package com.suhailahnfsella.fudum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHistory {
    @SerializedName("nopesanan")
    @Expose
    private Integer nopesanan;
    @SerializedName("iduser")
    @Expose
    private Integer iduser;
    @SerializedName("idtoko")
    @Expose
    private Integer idtoko;
    @SerializedName("namapenerima")
    @Expose
    private String namapenerima;
    @SerializedName("alamatpenerima")
    @Expose
    private String alamatpenerima;
    @SerializedName("kodeproduk")
    @Expose
    private String kodeproduk;
    @SerializedName("jumlahpesanan")
    @Expose
    private Integer jumlahpesanan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("via")
    @Expose
    private String via;
    @SerializedName("tgl")
    @Expose
    private String tgl;
    @SerializedName("bukti")
    @Expose
    private String bukti;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getNopesanan() {
        return nopesanan;
    }

    public void setNopesanan(Integer nopesanan) {
        this.nopesanan = nopesanan;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public Integer getIdtoko() {
        return idtoko;
    }

    public void setIdtoko(Integer idtoko) {
        this.idtoko = idtoko;
    }

    public String getNamapenerima() {
        return namapenerima;
    }

    public void setNamapenerima(String namapenerima) {
        this.namapenerima = namapenerima;
    }

    public String getAlamatpenerima() {
        return alamatpenerima;
    }

    public void setAlamatpenerima(String alamatpenerima) {
        this.alamatpenerima = alamatpenerima;
    }

    public String getKodeproduk() {
        return kodeproduk;
    }

    public void setKodeproduk(String kodeproduk) {
        this.kodeproduk = kodeproduk;
    }

    public Integer getJumlahpesanan() {
        return jumlahpesanan;
    }

    public void setJumlahpesanan(Integer jumlahpesanan) {
        this.jumlahpesanan = jumlahpesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) { this.tgl = tgl; }

    public String getBukti() { return bukti; }

    public void setBukti(String bukti) {
        this.bukti = bukti;
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
