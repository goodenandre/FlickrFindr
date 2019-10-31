package com.example.flickrfindr.data.retrofit;

import com.example.flickrfindr.data.PhotosService;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServiceFactory {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new FlickerQueryInterceptor())
            .callTimeout(5, TimeUnit.SECONDS)
            .build();

    private static Moshi moshi = new Moshi.Builder()
            .add(new KotlinJsonAdapterFactory())
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.flickr.com/services/rest/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build();

    public static <T> T createService(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
