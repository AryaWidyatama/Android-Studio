package com.suhailahnfsella.fudum;

public class RcvHistoryOrderan {
        private String tvKodeProduk;
        private String tvJumlahProduk;
        private String tvNamaPemesan;
        private String tvAlamatPenerima;
        private String tvTglOrder;

        public RcvHistoryOrderan(String tvKodeProduk, String tvJumlahProduk, String tvNamaPemesan, String tvAlamatPenerima, String tvTglOrder) {
            this.tvKodeProduk = tvKodeProduk;
            this.tvJumlahProduk = tvJumlahProduk;
            this.tvNamaPemesan = tvNamaPemesan;
            this.tvAlamatPenerima = tvAlamatPenerima;
            this.tvTglOrder = tvTglOrder;
        }

        public String getTvKodeProduk() {
            return tvKodeProduk;
        }

        public void setTvKodeProduk(String tvKodeProduk) {
            this.tvKodeProduk = tvKodeProduk;
        }

        public String getTvJumlahProduk() {
            return tvJumlahProduk;
        }

        public void setTvJumlahProduk(String tvJumlahProduk) {
            this.tvJumlahProduk = tvJumlahProduk;
        }

        public String getTvNamaPemesan() {
            return tvNamaPemesan;
        }

        public void setTvNamaPemesan(String tvNamaPemesan) {
            this.tvNamaPemesan = tvNamaPemesan;
        }

        public String getTvAlamatPenerima() {
            return tvAlamatPenerima;
        }

        public void setTvAlamatPenerima(String tvAlamatPenerima) {
            this.tvAlamatPenerima = tvAlamatPenerima;
        }

        public String getTvTglOrder() {
            return tvTglOrder;
        }

        public void setTvTglOrder(String tvTglOrder) {
            this.tvTglOrder = tvTglOrder;
        }
}

