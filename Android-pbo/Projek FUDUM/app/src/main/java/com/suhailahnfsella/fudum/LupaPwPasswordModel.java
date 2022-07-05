package com.suhailahnfsella.fudum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LupaPwPasswordModel {
    @SerializedName("password")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
