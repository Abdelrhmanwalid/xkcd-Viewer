package com.abdelrhman.xkcd.data.remote;

import com.abdelrhman.xkcd.BuildConfig;
import com.abdelrhman.xkcd.data.remote.dto.ComicDto;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class XKCDService {

    XKCDServiceInterface service;

    public XKCDService(String baseURl) {
        service = buildService(baseURl);
    }

    public Flowable<ComicDto> getComic(long id) {
        return service.getSpecific(id);
    }

    public Flowable<ComicDto> getLatest() {
        return service.getLatest();
    }

    private XKCDServiceInterface buildService(String baseURl) {
        return getDefaultRetrofit(baseURl).create(XKCDServiceInterface.class);
    }

    private Retrofit getDefaultRetrofit(String baseURl) {
        return new Retrofit.Builder()
                .baseUrl(baseURl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClientBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
    }

    interface XKCDServiceInterface {
        @GET("info.0.json")
        Flowable<ComicDto> getLatest();

        @GET("/{id}/info.0.json")
        Flowable<ComicDto> getSpecific(@Path("id") long id);
    }

}
