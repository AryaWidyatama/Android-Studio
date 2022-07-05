package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PesananModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idSiswa")
    @Expose
    private Integer idSiswa;
    @SerializedName("Namabarang")
    @Expose
    private String namabarang;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("FotoBarang")
    @Expose
    private String fotoBarang;
    @SerializedName("NamaPengguna")
    @Expose
    private String namaPengguna;
    @SerializedName("KodePesanan")
    @Expose
    private String kodePesanan;
    @SerializedName("KelasPengguna")
    @Expose
    private String kelasPengguna;
    @SerializedName("JurusanPengguna")
    @Expose
    private String jurusanPengguna;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("TanggalTransaksi")
    @Expose
    private String tanggalTransaksi;
    @SerializedName("DeskripsiPesanan")
    @Expose
    private String deskripsiPesanan;
    @SerializedName("jumlahPesanan")
    @Expose
    private Integer jumlahPesanan;
    @SerializedName("KodeBarang")
    @Expose
    private String kodeBarang;
    @SerializedName("Opsi")
    @Expose
    private String opsi;
    @SerializedName("Tujuan")
    @Expose
    private String tujuan;
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

    public Integer getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(Integer idSiswa) {
        this.idSiswa = idSiswa;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getFotoBarang() {
        return fotoBarang;
    }

    public void setFotoBarang(String fotoBarang) {
        this.fotoBarang = fotoBarang;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getKodePesanan() {
        return kodePesanan;
    }

    public void setKodePesanan(String kodePesanan) {
        this.kodePesanan = kodePesanan;
    }

    public String getKelasPengguna() {
        return kelasPengguna;
    }

    public void setKelasPengguna(String kelasPengguna) {
        this.kelasPengguna = kelasPengguna;
    }

    public String getJurusanPengguna() {
        return jurusanPengguna;
    }

    public void setJurusanPengguna(String jurusanPengguna) {
        this.jurusanPengguna = jurusanPengguna;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getDeskripsiPesanan() {
        return deskripsiPesanan;
    }

    public void setDeskripsiPesanan(String deskripsiPesanan) {
        this.deskripsiPesanan = deskripsiPesanan;
    }

    public Integer getJumlahPesanan() {
        return jumlahPesanan;
    }

    public void setJumlahPesanan(Integer jumlahPesanan) {
        this.jumlahPesanan = jumlahPesanan;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getOpsi() {
        return opsi;
    }

    public void setOpsi(String opsi) {
        this.opsi = opsi;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
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
