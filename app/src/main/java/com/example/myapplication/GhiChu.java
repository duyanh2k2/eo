package com.example.myapplication;

import java.io.Serializable;

public class GhiChu implements Serializable {

    private String tieude, ngaythang, noidung;

    public GhiChu() {
    }

    public GhiChu( String tieude, String ngaythang, String noidung) {
        this.tieude = tieude;
        this.ngaythang = ngaythang;
        this.noidung = noidung;
    }


    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
