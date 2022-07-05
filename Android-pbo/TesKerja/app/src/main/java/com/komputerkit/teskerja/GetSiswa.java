package com.komputerkit.teskerja;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSiswa {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("data")
    @Expose
    private SiswaModel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public SiswaModel getData() {
        return data;
    }

    public void setData(SiswaModel data) {
        this.data = data;
    }
}
