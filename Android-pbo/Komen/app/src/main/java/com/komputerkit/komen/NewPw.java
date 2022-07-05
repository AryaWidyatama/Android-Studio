package com.komputerkit.komen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewPw {
    @SerializedName("Password")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
