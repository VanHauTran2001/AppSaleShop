package com.example.saleapp.Retrofit;

public class DataService {
    public static String BASE_URL ="https://tranvanhau3108.000webhostapp.com/SaleShop/";
    public static APIService getAPIService(){
        return RetrofitClient.getInstance(BASE_URL).create(APIService.class);
    }
}
