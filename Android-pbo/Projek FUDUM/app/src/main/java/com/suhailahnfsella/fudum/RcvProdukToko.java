package com.suhailahnfsella.fudum;

public class RcvProdukToko {
        private String tvJudulProduk;
        private String tvKategoriProduk;
        private String tvHargaProduk;
        private String tvKecProduk;
        private String tvKabProduk;
        private Integer imgProduk1;

        public RcvProdukToko(String tvJudulProduk, String tvKategoriProduk, String tvHargaProduk, String tvKecProduk, String tvKabProduk, Integer imgProduk1) {
            this.tvJudulProduk = tvJudulProduk;
            this.tvKategoriProduk = tvKategoriProduk;
            this.tvHargaProduk = tvHargaProduk;
            this.tvKecProduk = tvKecProduk;
            this.tvKabProduk = tvKabProduk;
            this.imgProduk1 = imgProduk1;
        }

        public Integer getImgProduk1() {
            return imgProduk1;
        }

        public void setImgProduk1(Integer imgProduk1) {
            this.imgProduk1 = imgProduk1;
        }

        public String getTvJudulProduk() {
            return tvJudulProduk;
        }

        public void setTvJudulProduk(String tvJudulProduk) {
            this.tvJudulProduk = tvJudulProduk;
        }

        public String getTvKategoriProduk() {
            return tvKategoriProduk;
        }

        public void setTvKategoriProduk(String tvKategoriProduk) {
            this.tvKategoriProduk = tvKategoriProduk;
        }

        public String getTvHargaProduk() {
            return tvHargaProduk;
        }

        public void setTvHargaProduk(String tvHargaProduk) {
            this.tvHargaProduk = tvHargaProduk;
        }

        public String getTvKecProduk() {
            return tvKecProduk;
        }

        public void setTvKecProduk(String tvKecProduk) {
            this.tvKecProduk = tvKecProduk;
        }

        public String getTvKabProduk() {
            return tvKabProduk;
        }

        public void setTvKabProduk(String tvKabProduk) {
            this.tvKabProduk = tvKabProduk;
        }
}

