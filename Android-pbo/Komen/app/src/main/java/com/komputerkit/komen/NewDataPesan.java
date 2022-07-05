package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDataPesan {
    @SerializedName("KelasPengguna")
    @Expose
    private String kelasPengguna;
    @SerializedName("JurusanPengguna")
    @Expose
    private String jurusanPengguna;

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

}
