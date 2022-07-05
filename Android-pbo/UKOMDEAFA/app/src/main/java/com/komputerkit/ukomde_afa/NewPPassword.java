package com.komputerkit.ukomde_afa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPPassword {
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
