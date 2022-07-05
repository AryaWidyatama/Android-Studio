package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifikasiAkunModel {
    @SerializedName("NamaLengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("Kelas")
    @Expose
    private String kelas;
    @SerializedName("Jurusan")
    @Expose
    private String jurusan;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("FotoKtp")
    @Expose
    private String fotoKtp;
    @SerializedName("NoTelp")
    @Expose
    private String noTelp;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("status")
    @Expose
    private String status;

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
