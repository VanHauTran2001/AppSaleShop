package com.example.saleapp.Model;

public class SanPhamMoi {
    int id;
    String tensp;
    String gia;
    String hinhanh;
    String mota;
    int loai;

    public SanPhamMoi(int id, String tensp, String gia, String hinhanh, String mota, int loai) {
        this.id = id;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
