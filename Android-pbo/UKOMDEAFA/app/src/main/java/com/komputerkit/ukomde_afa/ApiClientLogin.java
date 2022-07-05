package com.komputerkit.ukomde_afa;

import java.util.Arrays;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientLogin {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).protocols(Arrays.asList(Protocol.HTTP_1_1)).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
//                    .protocols( Collections.singletonList(Protocol.HTTP_1_1) )
                 //  .baseUrl("https://www.deafa.xyz/ukom/api-lumen/public/")
                 .baseUrl("http://192.168.1.7:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static LoginRegInterfaace loginRegInterfaace(){
        LoginRegInterfaace login = getRetrofit().create(LoginRegInterfaace.class);
        return login;
    }
}
