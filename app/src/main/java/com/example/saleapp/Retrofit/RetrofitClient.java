package com.example.saleapp.Retrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getInstance(String url){
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(10000, TimeUnit.MILLISECONDS)
//                .writeTimeout(10000,TimeUnit.MILLISECONDS)
//                .connectTimeout(10000,TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .protocols(Arrays.asList(Protocol.HTTP_1_1))
//                .build();
//        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
