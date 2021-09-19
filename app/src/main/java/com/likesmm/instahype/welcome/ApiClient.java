package com.likesmm.instahype.welcome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    public static final String BASE_URL="https://like-smm.ru/android/";
    public static final String BASE_URL="http://a0556538.xsph.ru/";
    public static Retrofit retrofit=null;



    public static Retrofit getApiClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        if ((retrofit==null)){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
