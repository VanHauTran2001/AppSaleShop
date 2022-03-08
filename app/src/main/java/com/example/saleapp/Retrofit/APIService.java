package com.example.saleapp.Retrofit;

import com.example.saleapp.Model.LoaiSpModel;
import com.example.saleapp.Model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSP();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSPMoi();

    @POST("chitietsp.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPhamDT(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
