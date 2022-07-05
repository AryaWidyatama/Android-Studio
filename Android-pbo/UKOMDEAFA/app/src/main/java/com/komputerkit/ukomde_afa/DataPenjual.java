package com.komputerkit.ukomde_afa;

public class DataPenjual {
    private String JudulBarang;
    private String DescBarang;
    private Integer GambarBarang;
    private String KodeBarang;
    private String HargaBarang;



    public DataPenjual(String judulBarang, String descBarang, Integer gambarBarang, String kodeBarang, String hargaBarang) {
        JudulBarang = judulBarang;
        DescBarang = descBarang;
        GambarBarang = gambarBarang;
        KodeBarang = kodeBarang;
        HargaBarang = hargaBarang;
    }


    public String getJudulBarang() {
        return JudulBarang;
    }

    public void setJudulBarang(String judulBarang) {
        JudulBarang = judulBarang;
    }

    public String getDescBarang() {
        return DescBarang;
    }

    public void setDescBarang(String descBarang) {
        DescBarang = descBarang;
    }

    public Integer getGambarBarang() {
        return GambarBarang;
    }

    public String getKodeBarang() {
        return KodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        KodeBarang = kodeBarang;
    }

    public void setGambarBarang(Integer gambarBarang) {
        GambarBarang = gambarBarang;
    }
    public String getHargaBarang() {
        return HargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        HargaBarang = hargaBarang;
    }

}
