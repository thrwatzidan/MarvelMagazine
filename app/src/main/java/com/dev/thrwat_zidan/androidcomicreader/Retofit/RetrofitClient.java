package com.dev.thrwat_zidan.androidcomicreader.Retofit;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;
//private static String baseUrl="http://192.168.1.4";
private static String baseUrl="https://manganodejsapiheroku.herokuapp.com/ ";
    public static Retrofit getInstance() {
        if (instance==null)

            instance=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }
}
