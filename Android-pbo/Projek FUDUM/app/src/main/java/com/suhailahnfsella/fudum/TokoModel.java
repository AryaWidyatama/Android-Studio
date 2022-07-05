package com.suhailahnfsella.fudum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokoModel {
    @SerializedName("idtoko")
    @Expose
    private Integer idtoko;
    @SerializedName("namatoko")
    @Expose
    private String namatoko;
    @SerializedName("fototoko")
    @Expose
    private String fototoko;
    @SerializedName("alamattoko")
    @Expose
    private String alamattoko;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kabupaten")
    @Expose
    private String kabupaten;
    @SerializedName("tahuntoko")
    @Expose
    private String tahuntoko;
    @SerializedName("sosmed")
    @Expose
    private String sosmed;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getIdtoko() {
        return idtoko;
    }

    public void setIdtoko(Integer idtoko) {
        this.idtoko = idtoko;
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

    public String getTahuntoko() {
        return tahuntoko;
    }

    public void setTahuntoko(String tahuntoko) {
        this.tahuntoko = tahuntoko;
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
}
